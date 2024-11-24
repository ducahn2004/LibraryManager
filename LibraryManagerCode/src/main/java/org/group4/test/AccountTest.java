package org.group4.test;

import org.group4.model.user.Account;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.Assert.*;

public class AccountTest {

  private Account account;
  private String plainTextPassword;

  @Before
  public void setUp() {
    // Khởi tạo một account với ID và mật khẩu
    plainTextPassword = "securePassword123";
    account = new Account("user123", Account.hashPassword(plainTextPassword));
  }

  @Test
  public void testConstructor() {
    // Kiểm tra constructor của Account
    assertNotNull(account);
    assertEquals("user123", account.getId());
    assertNotNull(account.getPassword());
    assertNotEquals(plainTextPassword,
        account.getPassword()); // Mật khẩu đã được hash, không giống mật khẩu gốc
  }

  @Test
  public void testGetId() {
    // Kiểm tra phương thức getId()
    assertEquals("user123", account.getId());
  }

  @Test
  public void testGetPassword() {
    // Kiểm tra phương thức getPassword()
    assertNotNull(account.getPassword());
    assertNotEquals(plainTextPassword, account.getPassword()); // Mật khẩu đã được mã hóa
  }

  @Test
  public void testSetPassword() {
    // Kiểm tra phương thức setPassword()
    String newPlainTextPassword = "newSecurePassword";
    account.setPassword(newPlainTextPassword);

    assertNotEquals(newPlainTextPassword, account.getPassword()); // Mật khẩu mới phải được mã hóa
    assertTrue(BCrypt.checkpw(newPlainTextPassword,
        account.getPassword())); // Kiểm tra mật khẩu gốc với mật khẩu đã mã hóa
  }

  @Test
  public void testHashPassword() {
    // Kiểm tra phương thức hashPassword()
    String hashedPassword = Account.hashPassword(plainTextPassword);
    assertNotNull(hashedPassword);
    assertNotEquals(plainTextPassword,
        hashedPassword); // Mật khẩu đã mã hóa không giống mật khẩu gốc
    assertTrue(BCrypt.checkpw(plainTextPassword,
        hashedPassword)); // Kiểm tra mật khẩu gốc với mật khẩu đã mã hóa
  }
}

