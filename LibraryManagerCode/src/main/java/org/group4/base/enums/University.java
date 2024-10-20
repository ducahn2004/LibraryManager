package org.group4.base.enums;

public enum University {
  UET("University of Technology"),
  UMP("University of Medicine"),
  ULIS("University of Language"),
  HUST("University of science"),
  HUCE("University of Civil"),
  UL("University of law"),
  UEB("University of business");

  private final String displayName;

  /**
   * This function is used to construct.
   *
   * @param displayName is the value.
   */
  University(String displayName) {
    this.displayName = displayName;
  }

  /**
   * This function is used to get the value.
   *
   * @return the value
   */
  public String getDisplayName(){
    return displayName;
  }

  /**
   * This function is used to override toString method.
   *
   * @return displayName value
   */
  @Override
  public String toString(){
    return displayName;
  }
}
