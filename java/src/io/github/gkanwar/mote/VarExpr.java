package io.github.gkanwar.mote;

public class VarExpr extends NoScopeExpr {
  private Var v;

  public VarExpr(Var v) {
    this.v = v;
  }

  @Override
  public String toString() {
    return v.toString();
  }
}
