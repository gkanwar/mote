#include "ir.h"
#include "ir_printer.h"

Expr::Expr(const Var &var) : Expr(VarExpr::make(var)) {}

ostream& operator<<(ostream& os, const Expr& e) {
  IRPrinter printer(os);
  printer.print(e);
  return os;
}
