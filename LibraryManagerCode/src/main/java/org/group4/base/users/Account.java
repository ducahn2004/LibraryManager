package org.group4.base.users;

import org.group4.base.entities.Person;
import org.group4.base.enums.AccountStatus;

public class Account {

  private String id;
  private String password;
  private AccountStatus status;
  private Person person;

  public Account(String id, String password, Person person,  AccountStatus status) {
    this.id = id;
    this.password = password;
    this.person = person;
    this.status = status;
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

  public AccountStatus getStatus() {
    return status;
  }

  public void setStatus(AccountStatus status) {
    this.status = status;
  }


  public boolean isActive() {
    return this.status == AccountStatus.ACTIVE;
  }

  public void closedAccount() {
    this.status = AccountStatus.CLOSED;
  }

  public boolean login (String id, String password) {
    if (this.id.equals(id) && this.password.equals(password)) {
      return true;
    }
    return false;
  }

  public static Account register (String id, String password, Person person) {
    Account newAccount = new Account(id, password, person, AccountStatus.ACTIVE);
    return newAccount;
  }

  public boolean changePassword(String oldPassword, String newPassword) {
    if (this.password.equals(oldPassword)) {
      this.password = newPassword;
      return true;
    }
    return false;
  }

  public void resetPassword(String newPassword) {
    this.password = newPassword;
  }


}
