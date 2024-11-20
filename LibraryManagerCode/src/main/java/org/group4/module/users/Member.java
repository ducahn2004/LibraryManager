package org.group4.module.users;

import java.time.LocalDate;

/**
 * The {@code Member} class represents a library member who can lend books.
 * It includes information like member ID, book lendings, and related operations.
 * This class extends the {@link Person} class to inherit basic personal information.
 */
public class Member extends Person {

  /** The unique identifier for the member */
  private String memberId;

  /** The total number of books lent by the member */
  private int totalBooksCheckedOut;

  /**
   * Constructs a {@code Member} object with the given details.
   *
   * @param name the name of the member
   * @param dateOfBirth the date of birth of the member
   * @param email the email address of the member
   * @param phoneNumber the phone number of the member
   */
  public Member(String name, LocalDate dateOfBirth, String email, String phoneNumber) {
    super(name, dateOfBirth, email, phoneNumber);
    this.memberId = "";
    totalBooksCheckedOut = 0;
  }

  /**
   * Constructs a {@code Member} object with the given details.
   *
   * @param memberId the member ID
   * @param name the name of the member
   * @param dateOfBirth the date of birth of the member
   * @param email the email address of the member
   * @param phoneNumber the phone number of the member
   * @param totalBooksCheckedOut the total number of books checked out by the member
   */
  public Member(String memberId, String name, LocalDate dateOfBirth, String email,
      String phoneNumber, int totalBooksCheckedOut) {
    super(name, dateOfBirth, email, phoneNumber);
    this.memberId = memberId;
    this.totalBooksCheckedOut = totalBooksCheckedOut;
  }

  /**
   * Returns the member ID.
   *
   * @return the member ID
   */
  public String getMemberId() {
    return memberId;
  }

  /**
   * Returns the list of book lendings for this member.
   *
   * @param memberId the member ID
   */
  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

  /**
   * Returns the total number of books checked out by the member.
   *
   * @return the total number of books checked out
   */
  public int getTotalBooksCheckedOut() {
    return totalBooksCheckedOut;
  }

  /**
   * Sets the total number of books checked out by the member.
   *
   * @param totalBooksCheckedOut the total number of books checked out
   */

  public void setTotalBooksCheckedOut(int totalBooksCheckedOut) {
    this.totalBooksCheckedOut = totalBooksCheckedOut;
  }

  @Override
  public String toString() {
    return "  memberId = '" + memberId + "',\n" +
        "  name = '" + getName() + "',\n" +
        "  dateOfBirth = " + getDateOfBirth() + ",\n" +
        "  email = '" + getEmail() + "',\n" +
        "  phoneNumber = '" + getPhoneNumber() + "'\n";
  }
}
