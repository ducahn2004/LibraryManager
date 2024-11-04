package org.group4.base.entities;

import org.group4.database.AccountDatabase;

public class Account {

  private final String id;
  private String password;

  // Constructor
  public Account() {
    this.id = "";
    this.password = "";
  }
  public Account(String id, String password) {
    this.id = id;
    this.password = password;
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

  public static boolean login(String id, String password) {
    return AccountDatabase.getInstance().getItems().stream()
        .anyMatch(acc -> acc.getId().equals(id) && acc.getPassword().equals(password));
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

