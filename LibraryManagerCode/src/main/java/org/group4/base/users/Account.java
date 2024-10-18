package org.group4.base.users;

import java.util.List;
import org.group4.base.database.AccountDatabase;
import org.group4.base.entities.Person;
import org.group4.base.enums.AccountStatus;
import org.group4.base.enums.AccountType;

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
  private AccountStatus status; // Trang thai cua tai khoan.
  private AccountType accountType; // Loai tai khoan.
  private Person person; // Nguoi dung cua tai khoan.

  /**
   * Tao mot tai khoan moi.
   *
   * @param id       Ma so cua tai khoan.
   * @param password Mat khau cua tai khoan.
   * @param person   Nguoi dung cua tai khoan.
   */
  public Account(String id, String password, Person person, AccountType accountType) {
    this.id = id;
    this.password = password;
    this.person = person;
    this.accountType = accountType;
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
  }

  public boolean login(String id, String password) {
    return this.id.equals(id) && this.password.equals(password);

//  public static Account register(String id, String password, Person person, AccountType accountType) {
//    if (accountType == AccountType.MEMBER) {
//      return new Member(id, password, person);
//    } else if (accountType == AccountType.LIBRARIAN) {
//      return new Librarian(id, password, person);
//    } else {
//      throw new IllegalArgumentException("Loại tài khoản không hợp lệ");
//    }
//  }

//  public boolean changePassword(String oldPassword, String newPassword) {
//    if (this.password.equals(oldPassword)) {
//      this.password = newPassword;
//      return true;
//    }
//    return false;
//  }
//
//  public void resetPassword(String newPassword) {
//    this.password = newPassword;
//  }
//
//  public boolean logout() {
//    // TODO: implement
//    return true;
//  }

  }
}