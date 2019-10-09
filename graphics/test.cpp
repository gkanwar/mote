/** 
 * Test rendering functionality.
 */

#include <iostream>

#include "box.h"

using namespace std;

int main(int argc, char** argv) {
  cerr << "Testing solve bounds:" << endl;
  Box box1(new RectBox(3.0, 4.0)), box2(new RectBox(1.0, 5.0));
  Box cont(new ContainerBox({box1, box2}));
  double lx, ly;
  solve_box_bounds(cont, 1.0, 1.0, lx, ly);
}
