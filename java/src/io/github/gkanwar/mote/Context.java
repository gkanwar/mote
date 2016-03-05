package io.github.gkanwar.mote;

import java.util.*;

public class Context {
  private ScopeExpr globalScope;
  
  public Context() {
    globalScope = new ScopeExpr(new HashSet<Var>(), null);
  }
  public Context(Expr root) {
    globalScope = new ScopeExpr(new HashSet<Var>(), root);
  }

  Var makeGlobal(String name) {
    Var v = new Var(name);
    globalScope.addVar(v);
    return v;
  }

  void setRoot(Expr root) {
    globalScope.setInner(root);
  }

  @Override
  public String toString() {
    return globalScope.toString();
  }
}
