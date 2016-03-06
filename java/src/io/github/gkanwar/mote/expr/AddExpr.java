package io.github.gkanwar.mote.expr;

import io.github.gkanwar.mote.*;
import org.scilab.forge.jlatexmath.*;

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

  @Override
  public void buildFormula(Formula f, RenderMap rm) {
    String exprType = neg ? "minus" : "plus";
    l.buildFormula(f, rm);
    f.add(new TaggedAtom(SymbolAtom.get(exprType), (Expr)this, rm));
    r.buildFormula(f, rm);
  }
}
