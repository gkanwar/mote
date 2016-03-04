#ifndef IR_PRINTER_H
#define IR_PRINTER_H

#include <cassert>
#include <iostream>

using namespace std;

// Forward declarations to avoid ir.h include
class Expr;
class VarExpr;
class AddExpr;
class MulExpr;
class ScopeExpr;
class EmptyExpr;

class IRPrinter {
 public:
  IRPrinter(ostream& os) : os(os) {}

  // Top-level
  virtual void print(const Expr &e);

  // Visitor delegation from Expr subclasses
  virtual void visit(const VarExpr *e);
  virtual void visit(const AddExpr *e);
  virtual void visit(const MulExpr *e);
  virtual void visit(const EmptyExpr *e);
  virtual void visit(const ScopeExpr *e);

 private:
  ostream& os;
};

#endif
