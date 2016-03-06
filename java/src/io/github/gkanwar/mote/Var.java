package io.github.gkanwar.mote;

public class Var {
  private String name;
  
  public Var(String name) {
    this.name = name;
  }

  public char getSymbol() {
    return name.charAt(0);
  }

  @Override
  public String toString() {
    return name;
  }
}
