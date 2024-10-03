package org.group4.base.users;

import org.group4.base.entities.Person;
import org.group4.base.enums.AccountStatus;

public class Account {

  private String id;
  private String password;
  AccountStatus status;
  private Person person;


  /**
   * Creates a new Account with the specified id, password, status, and associated person details.
   *
   * @param id       the unique identifier for the account
   * @param password the password for the account
   * @param status   the current status of the account, represented by an {@link AccountStatus}
   *                 value
   * @param person   the personal details associated with the account, represented by a
   *                 {@link Person} object
   */
  public Account(String id, String password, AccountStatus status, Person person) {
    this.id = id;
    this.password = password;
    this.status = status;
    this.person = person;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  AccountStatus getStatus() {
    return status;
  }

  public void setStatus(AccountStatus status) {
    this.status = status;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  /**
   * Resets the account password to the specified new password.
   *
   * @param newPassword the new password to set; must not be null or empty
   * @return true if the password was successfully reset; false otherwise
   */
  public boolean resetPassword(String newPassword) {
    if (newPassword != null || newPassword.isEmpty()) {
      this.password = newPassword;
      return true;
    }
    return false;
  }

}
