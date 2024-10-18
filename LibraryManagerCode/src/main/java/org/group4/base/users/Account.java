package org.group4.base.users;

import org.group4.base.database.AccountDatabase;
import org.group4.base.entities.Person;
import org.group4.base.enums.AccountStatus;

/**
 * Tai khoan cua nguoi dung.
 * Chua cac thong tin cua tai khoan nhu id, password, trang thai, nguoi dung.
 * Co cac phuong thuc de thay doi mat khau, dang nhap, dang ky tai khoan.
 * Co cac phuong thuc de kiem tra trang thai cua tai khoan.
 * Co cac phuong thuc de thay doi trang thai cua tai khoan.
 * Co cac phuong thuc de reset mat khau.
 */
public class Account {

  private String id; // Ma so cua tai khoan.
  private String password; // Mat khau cua tai khoan.
  private final Person person; // Nguoi dung cua tai khoan.
  private AccountStatus status; // Trang thai cua tai khoan.

  /**
   * Tao mot tai khoan moi.
   *
   * @param id       Ma so cua tai khoan.
   * @param password Mat khau cua tai khoan.
   * @param person   Nguoi dung cua tai khoan.
   */
  public Account(String id, String password, Person person) {
    this.id = id;
    this.password = password;
    this.person = person;
    this.status = AccountStatus.ACTIVE;
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

  public Person getPerson() {
    return person;
  }

  public boolean isActive() {
    return this.status == AccountStatus.ACTIVE;
  }

  public void closedAccount() {
    this.status = AccountStatus.CLOSED;
    AccountDatabase.getAccounts().removeIf(acc -> acc.getId().equals(this.id));
  }

  public boolean login(String id, String password) {
    return this.id.equals(id) && this.password.equals(password) && this.status == AccountStatus.ACTIVE;
  }

  public static boolean register(String id, String password, String rePassword, Person person) {
    if (AccountDatabase.getAccounts().stream().anyMatch(acc -> acc.getId().equals(id)) || !password.equals(
        rePassword)) {
      return false;
    }
    Account newAccount = new Account(id, password, person);
    AccountDatabase.getAccounts().add(newAccount);
    return true;
  }

  public boolean changePassword(String oldPassword, String newPassword, String reNewPassword) {
    if (this.password.equals(oldPassword) && newPassword.equals(reNewPassword)) {
      this.password = newPassword;
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
    this.password = newPassword;
    return true;
  }
}

