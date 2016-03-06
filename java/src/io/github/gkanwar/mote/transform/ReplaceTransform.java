package io.github.gkanwar.mote.transform;

import io.github.gkanwar.mote.DeepCopy;
import io.github.gkanwar.mote.expr.*;

public class ReplaceTransform extends Transform {
  private Expr changed;

  public ReplaceTransform(Expr changed) {
    this.changed = changed;
  }
  
  @Override
  public Expr transform(Expr base) {
    return (Expr) DeepCopy.copy(this.changed);
  }
}
