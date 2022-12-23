#ifndef BOX_H
#define BOX_H

/**
 * Fundamental rendering object: Box providing px and py
 * as functions of lx and ly. Global rendering involves
 * setting a px and py externally and allowing equilibration.
 */

#include <cmath>
#include <memory>
#include <vector>

using namespace std;

class BoxNode {
 public:
  // Compute the pressures and pressure gradients
  virtual void get_pressures(
      double lx, double ly,
      double& px, double& py,
      double& dpx_dlx, double& dpx_dly,
      double& dpy_dlx, double& dpy_dly) const = 0;
};

/// Standard boilerplate object management things
struct Box {
  Box() {}
  Box(BoxNode *ptr) : ptr(ptr) {}
  Box(const Box& other) : ptr(other.ptr) {}
  shared_ptr<BoxNode> ptr;

  bool defined() { return (bool)ptr; }

  template<typename T> friend bool isa(Box);
  template<typename T> friend T* to(Box);

  // Provide clean interface to main method
  void get_pressures(double lx, double ly, double& px, double& py,
                     double& dpx_dlx, double& dpx_dly,
                     double& dpy_dlx, double& dpy_dly) const {
    ptr->get_pressures(lx, ly, px, py,
                       dpx_dlx, dpx_dly, dpy_dlx, dpy_dly);
  }
};

template<typename T>
inline bool isa(Box b) {
  return b.defined() && dynamic_cast<const T*>(b.ptr.get()) != nullptr;
}

template<typename T>
inline T* to(Box b) {
  assert(isa<T>(b));
  return static_cast<T*>(b.ptr.get());
}

void solve_box_bounds(const Box& box, double ext_px, double ext_py,
                      double& lx, double& ly, double tol=1e-6) {
  // Use gradient descent to solve for box bounds
  const double eps_len = 0.01; // Smallest allowed length
  const double eps_t = 0.1; // Epsilon timestep for gradient descent
  const double eps_grad = 0.01; // Convergence factor for gradient inversion
  lx = 1.0;
  ly = 1.0;
  for (int iter = 0; iter < 100; ++iter) {
    cout << "lens: " << lx << " " << ly << endl;
    double px, py, dpx_dlx, dpx_dly, dpy_dlx, dpy_dly;
    box.get_pressures(lx, ly, px, py,
                      dpx_dlx, dpx_dly,
                      dpy_dlx, dpy_dly);
    cout << "Grads: " << dpx_dlx << " " << dpx_dly << " " << dpy_dlx << " " << dpy_dly << endl;
    double diff_px = ext_px - px;
    double diff_py = ext_py - py;
    if (diff_px*diff_px + diff_py*diff_py < tol) break;
    cout << px << " " << py << "(" << diff_px << " " << diff_py << ")" << endl;
    double gradx = diff_px * dpx_dlx + diff_py * dpy_dlx;
    double grady = diff_px * dpx_dly + diff_py * dpy_dly;
    lx += eps_t * gradx / sqrt(gradx*gradx + grady*grady + eps_grad);
    ly += eps_t * grady / sqrt(gradx*gradx + grady*grady + eps_grad);
    if (lx <= eps_len) lx = eps_len;
    if (ly <= eps_len) ly = eps_len;
  }
}


class RectBox : public BoxNode {
  // Desired dimensions
  double dx, dy;
 public:
  RectBox(double dx, double dy) : dx(dx), dy(dy) {}
  virtual void get_pressures(double lx, double ly,
                             double& px, double& py,
                             double& dpx_dlx, double& dpx_dly,
                             double& dpy_dlx, double& dpy_dly) const {
    // Gas pressure due to volume P_gas = dx*dy / lx*ly;
    double pgas = dx*dy / (lx*ly);
    double gradx_pgas = -dx*dy / (lx*lx*ly);
    double grady_pgas = -dx*dy / (lx*ly*ly);
    // Skew force transfers pressure in the direction of
    // desired aspect ratio change.
    const double dratio = dx/dy;
    double ratio = lx/ly;
    // Construct symmetric force transfer
    double skew_factor = dratio/ratio - ratio/dratio;
    double dsf_dlx = -dratio/(ratio*lx) - ratio/(dratio*lx);
    double dsf_dly = dratio/(ratio*ly) + ratio/(dratio*ly);
    /// DEBUG: no skew
    // double skew_factor = 0.0;
    // double dsf_dlx = 0.0;
    // double dsf_dly = 0.0;
    px = pgas + skew_factor/lx;
    py = pgas - skew_factor/ly;
    dpx_dlx = gradx_pgas - skew_factor/(lx*lx) + dsf_dlx/lx;
    dpx_dly = grady_pgas + dsf_dly/lx;
    dpy_dlx = gradx_pgas - dsf_dlx/ly;
    dpy_dly = grady_pgas + skew_factor/(ly*ly) - dsf_dly/ly;
  }
};

class ContainerBox : public BoxNode {
  // Contained boxes
  vector<Box> boxes;
 public:
  ContainerBox(vector<Box> boxes) : boxes(boxes) {}
  virtual void get_pressures(double lx, double ly,
                             double& px, double& py,
                             double& dpx_dlx, double& dpx_dly,
                             double& dpy_dlx, double& dpy_dly) const {
    // Binary search pressures until sum(lx) <= lx and sum(ly) <= ly
    // and one of the bounds is saturated.
    double min_px = 0.01;
    double min_py = 0.01;
    double max_px = 100.0;
    double max_py = 100.0;
    const int max_iters = 100;
    for (int iter = 0; iter < max_iters; ++iter) {
      px = (min_px + max_px) / 2.0;
      py = (min_py + max_py) / 2.0;
      double tot_lx = 0.0, tot_ly = 0.0;
      for (auto &b : boxes) {
        double local_lx, local_ly;
        solve_box_bounds(b, px, py, local_lx, local_ly);
        tot_lx += local_lx;
        tot_ly += local_ly;
      }
      if (tot_lx > lx || tot_ly > ly) {
        max_px = px;
        max_py = py;
      }
      else {
        min_px = px;
        min_py = py;
      }
    }
    dpx_dlx = 0.0;
    dpx_dly = 0.0;
    dpy_dlx = 0.0;
    dpy_dly = 0.0;
    for (auto &b : boxes) {
      double local_lx, local_ly;
      solve_box_bounds(b, px, py, local_lx, local_ly);
      double local_dpx_dlx, local_dpx_dly,
          local_dpy_dlx, local_dpy_dly;
      double local_px, local_py;
      b.get_pressures(lx, ly, local_px, local_py,
                      local_dpx_dlx, local_dpx_dly,
                      local_dpy_dlx, local_dpy_dly);
      dpx_dlx += local_dpx_dlx;
      dpx_dly += local_dpx_dly;
      dpy_dlx += local_dpy_dlx;
      dpy_dly += local_dpy_dly;
    }
  }
};

#endif
