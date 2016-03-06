package org.scilab.forge.jlatexmath;

import java.util.*;
import java.awt.Graphics2D;

// import org.scilab.forge.jlatexmath.*;

/**
 * Wrapper class to track Box draw calls.
 */
public class TrackingBox extends Box {
  private Box base;
  
  public float lastX, lastY, lastW, lastH;
  public TrackingBox(Box base) {
    this.base = base;
    this.foreground = base.foreground;
    this.background = base.background;
    this.width = base.width;
    this.height = base.height;
    this.depth = base.depth;
    this.shift = base.shift;
    this.type = base.type;
    this.children = base.children;
    this.parent = base.parent;
    this.elderParent = base.elderParent;
    this.markForDEBUG = base.markForDEBUG;
  }
  
  public void add(Box b) {
    assert false;
    base.add(b);
  }
  public void add(int pos, Box b) {
    assert false;
    base.add(pos, b);
  }
  public void setParent(Box parent) {
    assert false;
    base.setParent(parent);
  }
  public Box getParent() {
    return base.getParent();
  }
  public void setElderParent(Box elderParent) {
    assert false;
    base.setElderParent(elderParent);
  }
  public Box getElderParent() {
    return base.getElderParent();
  }
  public float getWidth() {
    return base.getWidth();
  }
  public void negWidth() {
    assert false;
    base.negWidth();
  }
  public float getHeight() {
    return base.getHeight();
  }
  public float getDepth() {
    return base.getDepth();
  }
  public float getShift() {
    return base.getShift();
  }
  public void setWidth(float w) {
    assert false;
    base.setWidth(w);
  }
  public void setDepth(float d) {
    assert false;
    base.setDepth(d);
  }
  public void setHeight(float h) {
    assert false;
    base.setHeight(h);
  }
  public void setShift(float s) {
    assert false;
    base.setShift(s);
  }
  public void draw(Graphics2D g2, float x, float y) {
    // System.out.println("Tracking draw: " + x + "," + y);
    lastX = x;
    lastY = y;
    lastW = width;
    lastH = height;
    base.draw(g2, x, y);
  }
  public int getLastFontId() {
    return base.getLastFontId();
  }
}
