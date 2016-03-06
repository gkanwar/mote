package io.github.gkanwar.mote;

import org.scilab.forge.jlatexmath.*;
import io.github.gkanwar.mote.expr.*;

/**
 * Wrapper class to track atom rendering to Boxes.
 */
public class TaggedAtom extends Atom {
  private Atom base;
  private Expr tag;
  private RenderMap rm;

  /**
   * Wrapped values.
   */
  public int type;
  public int type_limits;
  public int alignment;
  
  public TaggedAtom(Atom base, Expr tag, RenderMap rm) {
    this.base = base;
    this.tag = tag;
    this.rm = rm;

    type = base.type;
    type_limits = base.type_limits;
    alignment = base.alignment;
  }

  @Override
  public Box createBox(TeXEnvironment env) {
    Box out = base.createBox(env);
    TrackingBox tb = new TrackingBox(out);
    rm.put(tag, tb);
    return tb;
  }

  @Override
  public int getLeftType() {
    return base.getLeftType();
  }

  @Override
  public int getRightType() {
    return base.getRightType();
  }
}
