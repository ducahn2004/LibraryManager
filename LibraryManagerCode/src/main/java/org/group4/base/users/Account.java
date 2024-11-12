package org.group4.base.users;

import org.group4.database.AccountDatabase;
import org.mindrot.jbcrypt.BCrypt;

/**
 * The {@code Account} class represents a user account with an ID and a password.
 * This class provides methods for login, password change, and logout functionality.
 */
public class Account {

  private final String id;
  private String password;

  /**
   * Constructs an {@code Account} object with the given ID and password.
   * The password is hashed using BCrypt before being stored.
   *
   * @param id       the unique identifier for the account
   * @param password the plain text password to be hashed and stored
   */
  public Account(String id, String password) {
    this.id = id;
    this.password = hashPassword(password); // Hash the password when creating the account
  }

  /**
   * Returns the ID of the account.
   *
   * @return the unique identifier of the account
   */
  public String getId() {
    return id;
  }

  /**
   * Returns the hashed password of the account.
   *
   * @return the hashed password of the account
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets a new password for the account, which is hashed before being stored.
   *
   * @param password the new plain text password to be hashed and set
   */
  private void setPassword(String password) {
    this.password = hashPassword(password); // Hash the new password before storing
  }

  /**
   * Hashes the provided password using BCrypt.
   *
   * @param password the plain text password to hash
   * @return the hashed password
   */
  private String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  /**
   * Verifies if the provided password matches the stored hashed password.
   *
   * @param password the plain text password to verify
   * @return true if the provided password matches the stored password, false otherwise
   */
  private boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.password);
  }

  /**
   * Attempts to log in by verifying the provided ID and password.
   *
   * @param id       the account ID
   * @param password the plain text password to verify
   * @return true if login is successful (ID and password match), false otherwise
   */
  public static boolean login(String id, String password) {
    return AccountDatabase.getInstance().getItems().stream()
        .filter(acc -> acc.getId().equals(id))
        .map(Account::getPassword)
        .findFirst()
        .map(storedPassword -> BCrypt.checkpw(password, storedPassword)) // Verify hashed password
        .orElse(false); // Return false if no matching account or incorrect password
  }

  /**
   * Changes the account's password if the old password is correct and the new passwords match.
   *
   * @param oldPassword  the current password
   * @param newPassword  the new password
   * @param reNewPassword the new password entered again for confirmation
   * @return true if the password change is successful, false otherwise
   */
  public boolean changePassword(String oldPassword, String newPassword, String reNewPassword) {
    if (!checkPassword(oldPassword)) {
      return false; // Old password is incorrect
    }
    if (!newPassword.equals(reNewPassword)) {
      return false; // New passwords do not match
    }
    setPassword(newPassword); // Set the new password after validation
    return true;
  }

  /**
   * Logs the user out of the system.
   * This is a placeholder for session management logic, which can be added later.
   *
   * @return true if the logout is successful
   */
  public boolean logout() {
    // Placeholder for session management logic
    return true;
  }

}
