package io.github.gkanwar.mote;

import java.util.*;

public abstract class Expr {
  public abstract Set<Var> getScopeVars();
}
