package org.group4.base.users;

import java.time.LocalDate;

/**
 * The {@code Member} class represents a library member who can lend books.
 * It includes information like member ID, book lendings, and related operations.
 * This class extends the {@link Person} class to inherit basic personal information.
 */
public class Member extends Person {

  private String memberId;

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
}
