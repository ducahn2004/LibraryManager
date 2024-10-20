package org.group4.base.entities;

public class Person {
  private String name;
  private String email;
  private Address address;

  // Constructor
  public Person(String name, String email, Address address) {
    this.name = name;
    this.email = email;
    this.address = address;
  }

  // Getter
  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public Address getAddress() {
    return address;
  }

  // Setter
  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

}


