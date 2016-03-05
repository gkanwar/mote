package io.github.gkanwar.mote;

public class MulExpr extends NoScopeExpr {
  private boolean inv;
  private Expr l, r;

  public MulExpr(Expr l, Expr r) {
    this(l, r, false);
  }
  public MulExpr(Expr l, Expr r, boolean inv) {
    this.l = l;
    this.r = r;
    this.inv = inv;
  }

  @Override
  public String toString() {
    String exprType = inv ? "Div" : "Mul";
    return exprType + "Expr("+l+","+r+")";
  }
}
