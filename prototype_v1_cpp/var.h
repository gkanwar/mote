#ifndef VAR_H
#define VAR_H

#include <iostream>
#include <memory>

using namespace std;

struct VarNode {
  string name;
  // Expr scope;
};

class Var {
 public:
  Var() {}
  Var(string name) {
    VarNode *vn = new VarNode();
    ptr.reset(vn);
    ptr->name = name;
  }
  Var(VarNode *ptr) : ptr(ptr) {}
  Var(const Var& other) : ptr(other.ptr) {}

  bool defined() { return (bool)ptr; }

  friend bool operator==(const Var& v1, const Var& v2);
  friend bool operator!=(const Var& v1, const Var& v2);
  friend bool operator<(const Var& v1, const Var& v2);
  friend ostream& operator<<(ostream& os, const Var& v);
  
 private:
  shared_ptr<VarNode> ptr;
};

#endif
