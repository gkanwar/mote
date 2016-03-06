package io.github.gkanwar.mote.expr;

import org.scilab.forge.jlatexmath.*;
import io.github.gkanwar.mote.*;

public class VarExpr extends NoScopeExpr {
  private Var v;

  public VarExpr(Var v) {
    this.v = v;
  }

  @Override
  public String toString() {
    return v.toString();
  }

  @Override
  public void buildFormula(Formula f, RenderMap rm) {
    f.add(new TaggedAtom(new CharAtom(v.getSymbol(), null), this, rm));
  }
}
