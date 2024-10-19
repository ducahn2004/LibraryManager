package org.group4.base.users;

import org.group4.base.database.AccountDatabase;
import org.group4.base.entities.Person;
import org.group4.base.enums.AccountStatus;

public class Account {

  private final String id;
  private String password;
  private final Person person;
  private AccountStatus status;

  // Constructor
  public Account(String id, String password, Person person) {
    this.id = id;
    this.password = password;
    this.person = person;
    this.status = AccountStatus.NONE;
  }

  public String getId() {
    return id;
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

  public void setPerson(Person person) {
    this.person.setName(person.getName());
    this.person.setEmail(person.getEmail());
  }

  public Person getPerson() {
    return person;
  }

  public boolean isActive() {
    return this.status == AccountStatus.ACTIVE;
  }

  public void closedAccount() {
    setStatus(AccountStatus.CLOSED);
    AccountDatabase.getAccounts().removeIf(acc -> acc.getId().equals(this.id));
  }

  public boolean login(String id, String password) {
    return this.id.equals(id) && getPassword().equals(password) && isActive();
  }

  public static boolean register(String id, String password, String rePassword, Person person) {
    if (AccountDatabase.getAccounts().stream().anyMatch(acc -> acc.getId().equals(id)) || !password.equals(
        rePassword)) {
      return false;
    }
    Account newAccount = new Account(id, password, person);
    newAccount.setStatus(AccountStatus.ACTIVE);
    AccountDatabase.getAccounts().add(newAccount);
    return true;
  }

  public boolean changePassword(String oldPassword, String newPassword, String reNewPassword) {
    if (getPassword().equals(oldPassword) && newPassword.equals(reNewPassword)) {
      setPassword(newPassword);
      return true;
    }
    return false;
  }

  public boolean logout() {
    return true;
  }

  public boolean resetPassword(String newPassword, String reNewPassword) {
    if (!newPassword.equals(reNewPassword)) {
      return false;
    }
    setPassword(newPassword);
    return true;
  }
}

