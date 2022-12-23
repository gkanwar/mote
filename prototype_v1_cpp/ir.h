#ifndef IR_H
#define IR_H

#include <cassert>
#include <iostream>
#include <string>
#include <set>
#include <vector>
#include <memory>

#include "ir_printer.h"
#include "var.h"

#define PRINT_ACCEPT virtual void accept(IRPrinter *p) const {p->visit(this);}

using namespace std;

// Forward declarations
class Expr;
class VarExpr;
class IRPrinter;

// Abstract class encapsulating the idea of a field?
class Field {

};

// General expression object, having a value
// Object should take a value in some field
struct ExprNode {
  ExprNode() {}
  ExprNode(Field field) : field(field) {}

  // Returns variable names bound at this scope
  virtual set<Var> getScopeVars() const {
    cerr << "Attempt to getScopeVars of expr: " << toString() << endl;
    assert(false); // not yet implemented
  }

  // Return node type as string, for debugging
  virtual string toString() const {return "Expr";}

  // Accept a printer, making use of virtual methods to choose the
  // correct visit method within the printer.
  // Should be overridden through the PRINT_ACCEPT macro.
  virtual void accept(IRPrinter *printer) const {
    cerr << "Attempt to accept exprnode: " << toString() << endl;
    assert(false);
  }

  Field field;
};

struct Expr {
  Expr() {}
  Expr(ExprNode *ptr) : ptr(ptr) {}
  Expr(const Var &var);
  Expr(const Expr &other) : ptr(other.ptr) {}
  shared_ptr<ExprNode> ptr;

  friend ostream& operator<<(ostream& os, const Expr& e);

  bool defined() { return (bool)ptr; }

  virtual void accept(IRPrinter *printer) const {
    return ptr->accept(printer);
  }
  virtual set<Var> getScopeVars() const { return ptr->getScopeVars(); }
  virtual string toString() const { return ptr->toString(); }

  template <typename E> friend bool isa(Expr);
  template <typename E> friend E* to(Expr);
};

template <typename E>
inline bool isa(Expr e) {
  return e.defined() && dynamic_cast<const E*>(e.ptr.get()) != nullptr;
}

template <typename E>
inline E* to(Expr e) {
  assert(isa<E>(e)); // Check expr type
  return static_cast<E*>(e.ptr.get());
}

struct NoScopeExpr : public ExprNode {
  virtual set<Var> getScopeVars() const {return set<Var>();}
  virtual string toString() const {return "NoScopeExpr";}
};

struct VarExpr : public NoScopeExpr {
  Var v;
  virtual string toString() const {return "VarExpr";}
  PRINT_ACCEPT

  static Expr make(const Var v) {
    VarExpr *ve = new VarExpr();
    ve->v = v;
    return ve;
  }
};

struct AddExpr : public NoScopeExpr {
  bool neg;
  Expr l, r;
  virtual string toString() const {return "AddExpr";}
  PRINT_ACCEPT

  static Expr make(Expr l, Expr r, bool neg=false) {
    AddExpr *ae = new AddExpr();
    ae->l = l;
    ae->r = r;
    ae->neg = neg;
    return ae;
  }
};

struct MulExpr : public NoScopeExpr {
  bool inv;
  Expr l, r;
  virtual string toString() const {return "MulExpr";}
  PRINT_ACCEPT

  static Expr make(Expr l, Expr r, bool inv=false) {
    MulExpr *me = new MulExpr();
    me->l = l;
    me->r = r;
    me->inv = inv;
    return me;
  }
};

struct EmptyExpr : public NoScopeExpr {
  virtual string toString() const {return "EmptyExpr";}
  PRINT_ACCEPT

  static Expr make() {
    return new EmptyExpr();
  }
};

struct ScopeExpr : public ExprNode {
  set<Var> scopedVars;
  Expr inner;
  void addVar(Var v) {
    scopedVars.insert(v);
  }
  virtual set<Var> getScopeVars() const {
    return scopedVars;
  }
  virtual string toString() const {return "ScopeExpr";}
  PRINT_ACCEPT

  static Expr make(set<Var> vars, Expr inner) {
    ScopeExpr *se = new ScopeExpr();
    se->scopedVars = vars;
    se->inner = inner;
    return se;
  }
};

#endif
