package io.github.gkanwar.mote;

import java.util.*;
import io.github.gkanwar.mote.expr.*;
import org.scilab.forge.jlatexmath.*;

public class RenderMap {
  private Map<Expr, TrackingBox> map = new HashMap<Expr, TrackingBox>();

  public RenderMap() {}

  public void put(Expr e, TrackingBox b) {
    map.put(e, b);
  }

  public TrackingBox get(Expr e) {
    return map.get(e);
  }
}
