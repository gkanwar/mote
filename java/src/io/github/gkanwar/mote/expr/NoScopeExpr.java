package io.github.gkanwar.mote.expr;

import java.util.*;
import io.github.gkanwar.mote.*;

public abstract class NoScopeExpr extends Expr {
  @Override
  public Set<Var> getScopeVars() {
    return new HashSet<Var>();
  }
}
