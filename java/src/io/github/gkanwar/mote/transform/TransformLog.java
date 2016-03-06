package io.github.gkanwar.mote.transform;

import io.github.gkanwar.mote.expr.*;

/**
 * Class to log a transform event that has been applied to the tree.
 */
public class TransformLog {
  private Expr orig, changed;
  private Transform t;
  
  public TransformLog(Expr orig, Expr changed, Transform t) {
    this.orig = orig;
    this.changed = changed;
    this.t = t;
  }
}
