package io.github.gkanwar.mote;

public class AddExpr extends NoScopeExpr {
  private boolean neg;
  private Expr l, r;

  public AddExpr(Expr l, Expr r) {
    this(l, r, false);
  }
  public AddExpr(Expr l, Expr r, boolean neg) {
    this.l = l;
    this.r = r;
    this.neg = neg;
  }
  
  @Override
  public String toString() {
    String exprType = neg ? "Sub" : "Add";
    return exprType + "Expr("+l+","+r+")";
  }
}
