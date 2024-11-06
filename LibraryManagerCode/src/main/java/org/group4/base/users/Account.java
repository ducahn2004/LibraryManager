package org.group4.base.users;

import org.group4.database.AccountDatabase;

public class Account {

  private final String id;
  private String password;

  // Constructor
  public Account(String id, String password) {
    this.id = id;
    this.password = password;
  }

  // Getter
  public String getId() {
    return id;
  }

  public String getPassword() {
    return password;
  }

  // Setter
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Method to login
   * @param id The id of the account
   * @param password The password of the account
   * @return true if the login is successful, false otherwise
   */
  public static boolean login(String id, String password) {
    return AccountDatabase.getInstance().getItems().stream()
        .anyMatch(acc -> acc.getId().equals(id) && acc.getPassword().equals(password));
  }

  /**
   * Method to change password
   *
   * @param oldPassword The old password
   * @param newPassword The new password
   * @param reNewPassword The new password again
   * @return true if the password is changed successfully, false otherwise
   */
  public boolean changePassword(String oldPassword, String newPassword, String reNewPassword) {
    if (getPassword().equals(oldPassword) && newPassword.equals(reNewPassword)) {
      setPassword(newPassword);
      return true;
    }
    return false;
  }

  /**
   * Method to logout
   *
   * @return true if the logout is successful, false otherwise
   */
  public boolean logout() {
    return true;
  }

}

