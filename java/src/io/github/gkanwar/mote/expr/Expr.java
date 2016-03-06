package io.github.gkanwar.mote.expr;

import java.util.*;
import io.github.gkanwar.mote.*;

public abstract class Expr {
  /**
   * Get the set of vars scoped by this expression.
   */
  public abstract Set<Var> getScopeVars();
  
  /**
   * Build a Formula object by sequentially calling f.add()
   * with appropriate TaggedAtom objects.
   */
  public abstract void buildFormula(Formula f, RenderMap rm);
}
