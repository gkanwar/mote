#include "ir.h"
#include "ir_printer.h"

void IRPrinter::print(const Expr& e) {
  e.accept(this);
}

void IRPrinter::visit(const VarExpr* e) {
  os << e->v;
}

void IRPrinter::visit(const AddExpr* e) {
  os << "(";
  print(e->l);
  if (e->neg) {
    os << "-";
  }
  else {
    os << "+";
  }
  print(e->r);
  os << ")";
}

void IRPrinter::visit(const MulExpr* e) {
  os << "(";
  print(e->l);
  if (e->inv) {
    os << "/";
  }
  else {
    os << "*";
  }
  print(e->r);
  os << ")";
}

void IRPrinter::visit(const EmptyExpr* e) {
  os << "()";
}

void IRPrinter::visit(const ScopeExpr* e) {
  bool first = true;
  os << "[";
  for (auto& v : e->getScopeVars()) {
    if (!first) {
      os << ",";
    }
    else {
      first = false;
    }
    os << v;
  }
  os << "]";
  print(e->inner);
}
