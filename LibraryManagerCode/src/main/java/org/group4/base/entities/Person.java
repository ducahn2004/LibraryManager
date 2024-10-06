package org.group4.base.entities;

/**
 * Dai dien cho nguoi dung cua he thong.
 */
public class Person {
  private String name; // Ten cua nguoi dung.
  private String email; // Dia chi email cua nguoi dung.

  /**
   * Tao nguoi dung moi.
   * @param name Ten cua nguoi dung.
   * @param email  Dia chi email cua nguoi dung.
   */
  public Person(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}


