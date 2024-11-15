package org.group4.module.users;

import org.mindrot.jbcrypt.BCrypt;

/**
 * The {@code Account} class represents a user account with a unique ID and a password.
 */
public class Account {

  /** The unique identifier for the account */
  private final String id;

  /** The hashed password for the account */
  private String password;

  /**
   * Constructs an {@code Account} object with the given ID and password.
   *
   * @param id       the unique identifier for the account
   * @param password the hashed password
   */
  public Account(String id, String password) {
    this.id = id;
    this.password = password;
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
   * @param password the hashed password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Hashes the provided password using BCrypt.
   *
   * @param password the plain text password to hash
   * @return the hashed password
   */
  public static String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }
}
