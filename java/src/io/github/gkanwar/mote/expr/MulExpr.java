package io.github.gkanwar.mote.expr;

import io.github.gkanwar.mote.*;
import org.scilab.forge.jlatexmath.*;

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

  @Override
  public void buildFormula(Formula f, RenderMap rm) {
    assert !inv;
    l.buildFormula(f, rm);
    f.add(new TaggedAtom(SymbolAtom.get("times"), (Expr)this, rm));
    r.buildFormula(f, rm);
  }
}
