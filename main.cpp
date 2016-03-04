#include <iostream>
#include "ir.h"
#include "context.h"

using namespace std;

int main(int argc, char** argv) {
  Context c;
  Var v1 = c.makeGlobal("a");
  Var v2 = c.makeGlobal("b");
  Var v3 = c.makeGlobal("c");
  c.setRoot(MulExpr::make(v1, AddExpr::make(v2, v3)));

  cout << c << endl;
}
