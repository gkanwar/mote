package io.github.gkanwar.mote;

import java.util.*;

public abstract class NoScopeExpr extends Expr {
  @Override
  public Set<Var> getScopeVars() {
    return new HashSet<Var>();
  }
}
