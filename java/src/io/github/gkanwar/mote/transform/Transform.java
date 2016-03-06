package io.github.gkanwar.mote.transform;

import io.github.gkanwar.mote.expr.*;

public abstract class Transform {
  /**
   * Transform an expression into another equivalent expression.
   * Returned Expr should be independent of `base` and any constructor
   * parameters. In general, this means a deep copy should be performed
   * before local transformations.
   */
  public abstract Expr transform(Expr base);
}
