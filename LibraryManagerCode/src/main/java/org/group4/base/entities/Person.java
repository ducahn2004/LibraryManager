package org.group4.base.entities;

import java.time.LocalDate;

public class Person {
  private String name;
  private LocalDate dateOfBirth;
  private String address;
  private String email;
  private String phoneNumber;

  // Constructor
  public Person() {
  }

  public Person(String name, LocalDate dateOfBirth, String address, String email, String phoneNumber) {
    this.name = name;
    this.dateOfBirth = dateOfBirth;
    this.address = address;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  // Getter
  public String getName() {
    return name;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public String getAddress() {
    return address;
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


