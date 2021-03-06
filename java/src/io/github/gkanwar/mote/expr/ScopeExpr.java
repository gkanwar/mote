package io.github.gkanwar.mote.expr;

import java.util.*;
import io.github.gkanwar.mote.*;
import org.scilab.forge.jlatexmath.*;

public class ScopeExpr extends Expr {
  private Set<Var> scopedVars;
  private Expr inner;

  public ScopeExpr(Set<Var> vars, Expr inner) {
    this.scopedVars = vars;
    this.inner = inner;
  }

  public void addVar(Var v) {
    scopedVars.add(v);
  }

  public void setInner(Expr inner) {
    this.inner = inner;
  }

  @Override
  public String toString() {
    String vars = "";
    boolean first = true;
    for (Var v : scopedVars) {
      if (!first) {
        vars += ",";
      }
      else {
        first = false;
      }
      vars += v;
    }
    return "ScopeExpr["+vars+"]("+inner+")";
  }

  @Override
  public Set<Var> getScopeVars() {
    return scopedVars;
  }

  @Override
  public void buildFormula(Formula f, RenderMap rm) {
    inner.buildFormula(f, rm);
  }
}
