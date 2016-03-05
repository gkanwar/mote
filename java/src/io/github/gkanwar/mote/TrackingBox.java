package io.github.gkanwar.mote;

import java.util.*;
import java.awt.Graphics2D;

import org.scilab.forge.jlatexmath.*;

/**
 * Wrapper class to track Box draw calls.
 */
public class TrackingBox extends Box {
  private Box base;

  
  public float lastX, lastY;
  public TrackingBox(Box base) {
    this.base = base;
  }
  
  public void add(Box b) {
    base.add(b);
  }
  public void add(int pos, Box b) {
    base.add(pos, b);
  }
  public void setParent(Box parent) {
    base.setParent(parent);
  }
  public Box getParent() {
    return base.getParent();
  }
  public void setElderParent(Box elderParent) {
    base.setElderParent(elderParent);
  }
  public Box getElderParent() {
    return base.getElderParent();
  }
  public float getWidth() {
    return base.getWidth();
  }
  public void negWidth() {
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
    base.setWidth(w);
  }
  public void setDepth(float d) {
    base.setDepth(d);
  }
  public void setHeight(float h) {
    base.setHeight(h);
  }
  public void setShift(float s) {
    base.setShift(s);
  }
  public void draw(Graphics2D g2, float x, float y) {
    System.out.println("Tracking draw: " + x + "," + y);
    lastX = x;
    lastY = y;
    base.draw(g2, x, y);
  }
  public int getLastFontId() {
    return base.getLastFontId();
  }
}
