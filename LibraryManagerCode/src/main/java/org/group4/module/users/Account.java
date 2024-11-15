package org.group4.module.users;

import org.group4.dao.AccountDAO;
import org.group4.dao.FactoryDAO;
import org.mindrot.jbcrypt.BCrypt;

/**
 * The {@code Account} class represents a user account with a unique ID and a password.
 * This class provides methods for login, password change, and logout functionality.
 */
public class Account {

  /** The unique identifier for the account */
  private final String id;

  /** The hashed password for the account */
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
    this.password = hashPassword(password);
  }

  /**
   * Returns the unique identifier of the account.
   *
   * @return the account ID
   */
  public String getId() {
    return id;
  }

  /**
   * Returns the hashed password of the account.
   *
   * @return the hashed password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets a new hashed password for the account.
   *
   * @param password the plain text password to hash and store
   */
  private void setPassword(String password) {
    this.password = hashPassword(password);
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
   * Logs in the user by verifying the provided credentials.
   *
   * @param id       the account ID
   * @param password the plain text password
   * @return {@code true} if the login is successful, {@code false} otherwise
   */
  public boolean login(String id, String password) {
    AccountDAO accountDAO = FactoryDAO.getAccountDAO();
    return accountDAO.verifyCredentials(id, password);
  }

  /**
   * Changes the account's password if the old password is correct.
   *
   * @param oldPassword the current password for verification
   * @param newPassword the new plain text password
   * @return {@code true} if the password change is successful, {@code false} otherwise
   */
  public boolean changePassword(String oldPassword, String newPassword) {
    AccountDAO accountDAO = FactoryDAO.getAccountDAO();
    if (accountDAO.verifyCredentials(this.id, oldPassword)) {
      setPassword(newPassword);
      return accountDAO.update(this);  // Update password in database
    }
    return false;
  }

  /**
   * Logs the user out of the system.
   * Placeholder for session management logic, which can be added later.
   *
   * @return {@code true} if the logout is successful
   */
  public boolean logout() {
    // Placeholder for session management logic
    return true;
  }
}
