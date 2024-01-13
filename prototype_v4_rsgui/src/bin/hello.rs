use wgpu::CreateSurfaceError;
use winit::{
  dpi::PhysicalSize,
  error::{EventLoopError, OsError},
  event_loop::{EventLoop, ControlFlow},
  window::{Window, WindowBuilder},
  event::{Event, WindowEvent},
};
use futures_lite::future;

#[derive(Debug)]
enum Error {
  GeneralError,
  EventLoopError(EventLoopError),
  OsError(OsError),
  CreateSurfaceError(CreateSurfaceError),
}

struct DisplayState {
  surface: wgpu::Surface,
  device: wgpu::Device,
  queue: wgpu::Queue,
  config: wgpu::SurfaceConfiguration,
  size: PhysicalSize<u32>,
  // apparently it is important that window is after surface, to avoid lifetime
  // issues with window handle inside surface on drop
  window: Window,
}

impl DisplayState {
  fn new(window: Window) -> Result<Self, Error> {
    let instance = wgpu::Instance::default();
    let surface = unsafe {
      instance.create_surface(&window)
    }.map_err(|e| Error::CreateSurfaceError(e))?;
    let adapter = future::block_on(
      instance.request_adapter(&wgpu::RequestAdapterOptions {
        power_preference: wgpu::PowerPreference::default(),
        force_fallback_adapter: false,
        compatible_surface: Some(&surface),
      })
    ).ok_or(Error::GeneralError)?;
    let (device, queue) = future::block_on(
      adapter.request_device(&wgpu::DeviceDescriptor {
        features: wgpu::Features::empty(),
        limits: wgpu::Limits::default(),
        label: None,
      }, None)
    ) .map_err(|_| Error::GeneralError)?;

    let size = window.inner_size();
    let surf_capabilities = surface.get_capabilities(&adapter);
    let surf_format = surf_capabilities.formats.iter().copied()
      .filter(|f| f.is_srgb()).next()
      .ok_or(Error::GeneralError)?;
    let config = wgpu::SurfaceConfiguration {
      usage: wgpu::TextureUsages::RENDER_ATTACHMENT,
      format: surf_format,
      width: size.width,
      height: size.height,
      present_mode: wgpu::PresentMode::Fifo,
      alpha_mode: wgpu::CompositeAlphaMode::Auto,
      view_formats: vec![],
    };
    surface.configure(&device, &config);
    Ok(Self {
      surface, device, queue, config, size, window
    })
  }

  fn resize(&mut self, new_size: PhysicalSize<u32>) {
    self.size = new_size;
    self.config.width = new_size.width;
    self.config.height = new_size.height;
    self.surface.configure(&self.device, &self.config);
  }

  fn render(&mut self) -> Result<(), Error> {
    let frame = self.surface.get_current_texture()
      .map_err(|_| Error::GeneralError)?;
    let view = frame.texture.create_view(
      &wgpu::TextureViewDescriptor::default());
    let mut encoder = self.device.create_command_encoder(
      &wgpu::CommandEncoderDescriptor { label: None });
    {
      let pass = encoder.begin_render_pass(
        &wgpu::RenderPassDescriptor {
          label: None,
          color_attachments: &[Some(
            wgpu::RenderPassColorAttachment {
              view: &view,
              resolve_target: None,
              ops: wgpu::Operations {
                load: wgpu::LoadOp::Clear(wgpu::Color {
                  r: 0.1, g: 0.2, b: 0.3, a: 1.0
                }),
                store: wgpu::StoreOp::Store,
              },
            })],
          depth_stencil_attachment: None,
          occlusion_query_set: None,
          timestamp_writes: None,
        });
    }
    self.queue.submit([encoder.finish()]);
    frame.present();

    Ok(())
  }
}

fn main() -> Result<(), Error> {
  env_logger::init();
  
  // winit event loop, window
  let event_loop = EventLoop::new()
    .map_err(|e| Error::EventLoopError(e))?;
  let window = WindowBuilder::new().build(&event_loop)
    .map_err(|e| Error::OsError(e))?;
  // package up window and wgpu display state
  let mut state = DisplayState::new(window)?;

  // start event loop
  event_loop.set_control_flow(ControlFlow::Poll);
  event_loop.run(move |event, target| {
    match event {
      Event::WindowEvent { event: wevent, window_id }
      if window_id == state.window.id() => {
        match wevent {
          WindowEvent::CloseRequested => {
            target.exit();
          },
          WindowEvent::RedrawRequested => {
            // TODO: update state?
            let ret = state.render();
            match ret {
              Ok(_) => (),
              Err(e) => {
                eprintln!("{:?}", e);
                target.exit();
              }
            }
          },
          WindowEvent::Resized(new_size) => {
            state.resize(new_size);
          },
          WindowEvent::KeyboardInput { event: ievent, .. } => {
            // TODO
          },
          _ => ()
        }
      },
      _ => ()
    }
  }).map_err(|e| Error::EventLoopError(e))?;

  Ok(())
}
