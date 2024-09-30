package org.group4.base;

public enum Address {
  HOME("Home"),
  WORK("Work"),
  OTHER("Other");

  private final String description;

  Address(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
