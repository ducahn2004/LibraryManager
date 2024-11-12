package org.group4.base.users;

import java.time.LocalDate;

/**
 * The {@code Person} class represents an individual with basic personal information,
 * including name, date of birth, email, and phone number.
 */
public class Person {

  private String name;
  private LocalDate dateOfBirth;
  private String email;
  private String phoneNumber;

  /**
   * Constructs a {@code Person} object with the specified name, date of birth, email, and phone number.
   *
   * @param name the name of the person
   * @param dateOfBirth the date of birth of the person
   * @param email the email address of the person
   * @param phoneNumber the phone number of the person
   */
  public Person(String name, LocalDate dateOfBirth, String email, String phoneNumber) {
    this.name = name;
    this.dateOfBirth = dateOfBirth;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  // Getter methods
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

  // Setter methods
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
