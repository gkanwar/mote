package io.github.gkanwar.mote;

public interface Selectable {
  /**
   * Toggle whether this object is selected or not, possibly affecting
   * the selection status of other related objects.
   */
  public void toggleSelected();

  /**
   * Accessor of selected bit.
   */
  public boolean isSelected();

  /**
   * Clear selected bit, possibly affecting the selection status of other
   * related objects.
   */
  public void clearSelected();
}
