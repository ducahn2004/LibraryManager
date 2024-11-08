package org.group4.base.users;

import java.time.LocalDate;

public class Person {
  private String name;
  private LocalDate dateOfBirth;
  private String email;
  private String phoneNumber;

  // Constructor
  public Person(String name, LocalDate dateOfBirth, String email, String phoneNumber) {
    this.name = name;
    this.dateOfBirth = dateOfBirth;
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

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }


}


