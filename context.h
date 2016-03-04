#ifndef CONTEXT_H
#define CONTEXT_H

#include "ir.h"
#include "ir_printer.h"

struct Context {
  Context() {
    globalScope = ScopeExpr::make({}, EmptyExpr::make());
  }
  Context(Expr root) {
    globalScope = ScopeExpr::make({}, root);
  }
  Var makeGlobal(string name) {
    // return Var(name, globalScope);
    return Var(name);
  }
  void setRoot(Expr root) {
    to<ScopeExpr>(globalScope)->inner = root;
  }
  Expr globalScope;

  friend ostream& operator<<(ostream& os, const Context& c) {
    IRPrinter printer(os);
    printer.print(c.globalScope);
    return os;
  }
};

#endif
