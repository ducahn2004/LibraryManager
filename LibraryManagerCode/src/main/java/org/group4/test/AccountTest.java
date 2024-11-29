package org.group4.test;

import org.group4.model.user.Account;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.Assert.*;

/**
 * Unit tests for the {@link Account} class.
 */
public class AccountTest {

  private Account account;
  private String plainTextPassword;

  @Before
  public void setUp() {
    // Initialize an account with ID and password
    plainTextPassword = "securePassword123";
    account = new Account("user123", Account.hashPassword(plainTextPassword));
  }

  @Test
  public void testConstructor() {
    // Test the constructor of Account
    assertNotNull(account);
    assertEquals("user123", account.getId());
    assertNotNull(account.getPassword());
    assertNotEquals(plainTextPassword,
        account.getPassword()); // The password is hashed, so it is not the same as the original password
  }

  @Test
  public void testGetId() {
    // Test the getId() method
    assertEquals("user123", account.getId());
  }

  @Test
  public void testGetPassword() {
    // Test the getPassword() method
    assertNotNull(account.getPassword());
    assertNotEquals(plainTextPassword, account.getPassword()); // The password is hashed
  }

  @Test
  public void testSetPassword() {
    // Test the setPassword() method
    String newPlainTextPassword = "newSecurePassword";
    account.setPassword(newPlainTextPassword);

    assertNotEquals(newPlainTextPassword,
        account.getPassword()); // The new password should be hashed
    assertTrue(BCrypt.checkpw(newPlainTextPassword,
        account.getPassword())); // Verify the original password against the hashed password
  }

  @Test
  public void testHashPassword() {
    // Test the hashPassword() method
    String hashedPassword = Account.hashPassword(plainTextPassword);
    assertNotNull(hashedPassword);
    assertNotEquals(plainTextPassword,
        hashedPassword); // The hashed password is different from the original password
    assertTrue(BCrypt.checkpw(plainTextPassword,
        hashedPassword)); // Verify the original password against the hashed password
  }
}
