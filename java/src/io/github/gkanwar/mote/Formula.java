package io.github.gkanwar.mote;

import org.scilab.forge.jlatexmath.*;

/**
 * Wrapper class around library TeXFormula (for now).
 */
public class Formula {
  public TeXFormula tf;

  public Formula(TeXFormula tf) {
    this.tf = tf;
  }

  /**
   * Must add TaggedAtoms only.
   */
  public void add(TaggedAtom tagged) {
    tf.add(tagged);
  }
}
