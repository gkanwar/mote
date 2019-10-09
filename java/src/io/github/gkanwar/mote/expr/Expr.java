package io.github.gkanwar.mote.expr;

import java.util.*;
import io.github.gkanwar.mote.*;

public abstract class Expr implements Selectable {
  /**
   * Get the set of vars scoped by this expression.
   */
  public abstract Set<Var> getScopeVars();
  
  /**
   * Build a Formula object by sequentially calling f.add()
   * with appropriate TaggedAtom objects.
   */
  public abstract void buildFormula(Formula f, RenderMap rm);


  /// Tree ///
  /**
   * Subclasses should set the parent bit to `this` when maintaining a list of
   * their own children.
   */
  protected Expr parent = null;
  /**
   * Subclasses should override this method to toggle selection of
   * their children.
   */
  public abstract void pushSelectedDown();

  
  /// Selectable interface ///
  private boolean selected = false;
  
  /**
   * In an expression tree, deselecting a sub-node of a selected node pushes the
   * selected bit down the other branches of the tree because the root is no
   * longer fully selected.
   */
  public void toggleSelected() {
    boolean possiblySelected = true;
    boolean toggled = false;
    while (possibleSelected) {
      // See if we are selected ourselves.
      if (selected) {
        selected = false;
        toggled = true;
        break;
      }
      // Walk up the tree and see if we are selected through our parent.
      possiblySelected = false;
      while (parent != null) {
        if (parent.isSelected()) {
          // We are selected through the parent. 
          parent.pushSelectedDown();
          possiblySelected = true;
          break;
        }
      }
    }

    // If we walked the tree and didn't find ourselves selected, we should
    // now toggle our own selection.
    if (!toggled) {
      selected = !selected;
    }
  }
}
