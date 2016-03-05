package io.github.gkanwar.mote;

import java.util.*;

import org.scilab.forge.jlatexmath.*;

/**
 * Wrapper class to track atom rendering to Boxes.
 */
public class TaggedAtom extends Atom {
  private Atom base;
  private String tag;
  // private Map<String, TrackingBox> tagMap;
  private Map<String, Box> tagMap;

  /**
   * Wrapped values.
   */
  public int type;
  public int type_limits;
  public int alignment;
  
  public TaggedAtom(Atom base, String tag, Map<String, Box> tagMap) {
    this.base = base;
    this.tag = tag;
    this.tagMap = tagMap;

    type = base.type;
    type_limits = base.type_limits;
    alignment = base.alignment;
  }

  public Box createBox(TeXEnvironment env) {
    Box out = base.createBox(env);
    // TrackingBox tb = new TrackingBox(out);
    // tagMap.put(tag, tb);
    tagMap.put(tag, out);
    // return tb;
    return out;
  }

  @Override
  public int getLeftType() {
    return base.getLeftType();
  }

  @Override
  public int getRightType() {
    return base.getRightType();
  }
}
