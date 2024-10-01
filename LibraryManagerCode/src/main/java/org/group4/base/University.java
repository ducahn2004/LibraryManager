package org.group4.base;

public enum University {
  UNIVERSITY_OF_TECHNOLOGY("University"),
  UNIVERSITY_OF_MEDICINE("University of Medicine"),
  UNIVERSITY_OF_LANGUAGE("University of Language"),
  UNIVERSITY_OF_SCIENCE("University of science"),
  UNIVERSITY_OF_CIVIL("University of Civil"),
  UNIVERSITY_OF_LAW("University of law"),
  UNIVERSITY_OF_BUSINESS("University of business");

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
