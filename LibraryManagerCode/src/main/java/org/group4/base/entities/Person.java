package org.group4.base.entities;

public class Person {
  private String name;
  private String email;
  private String phoneNumber;

  // Constructor
  public Person() {
    this.name = "";
    this.email = "";
    this.phoneNumber = "";
  }
  public Person(String name, String email, String phoneNumber) {
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  // Getter
  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  // Setter
  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

}


