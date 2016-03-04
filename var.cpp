#include "var.h"
#include "ir.h"

bool operator==(const Var& v1, const Var& v2) {
  return v1.ptr->name == v2.ptr->name;
}
bool operator!=(const Var& v1, const Var& v2) {
  return !(v1 == v2);
}
bool operator<(const Var& v1, const Var& v2) {
  return v1.ptr->name < v2.ptr->name;
}

ostream& operator<<(ostream& os, const Var& v) {
  os << v.ptr->name;
}

