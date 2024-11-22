package org.group4.model.users;

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
   * Constructs a {@code Person} object with default values.
   */
  public Person() {}

  /**
   * Constructs a {@code Person} object with the specified name, date of birth, email,
   * and phone number.
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

  /**
   * Returns the name of the person.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the date of birth of the person.
   *
   * @return the date of birth
   */
  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * Returns the email address of the person.
   *
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Returns the phone number of the person.
   *
   * @return the phone number
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Sets the name of the person.
   *
   * @param name the new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the date of birth of the person.
   *
   * @param dateOfBirth the new date of birth
   */
  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  /**
   * Sets the email address of the person.
   *
   * @param email the new email address
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Sets the phone number of the person.
   *
   * @param phoneNumber the new phone number
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
