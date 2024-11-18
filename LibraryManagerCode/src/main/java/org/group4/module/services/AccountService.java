package org.group4.module.services;

import org.group4.module.users.Account;
import org.group4.dao.AccountDAO;
import org.group4.dao.FactoryDAO;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Service class for handling account-related business logic.
 */
public class AccountService {

  /** The data access object for account entities. */
  private final AccountDAO accountDAO;

  /**
   * Constructs an {@code AccountService} object with the default account DAO.
   */
  public AccountService() {
    this.accountDAO = FactoryDAO.getAccountDAO();
  }

  /**
   * Logs in the user by verifying the provided credentials.
   *
   * @param id       the account ID
   * @param password the plain text password
   * @return {@code true} if login is successful, {@code false} otherwise
   */
  public boolean login(String id, String password) {
    return accountDAO
        .getById(id)
        .map(account -> BCrypt.checkpw(password, account.getPassword()))
        .orElse(false);
  }

  /**
   * Changes the account's password if the old password is correct.
   *
   * @param id          the account ID
   * @param oldPassword the current password for verification
   * @param newPassword the new plain text password
   * @return {@code true} if the password change is successful, {@code false} otherwise
   */
  public boolean changePassword(String id, String oldPassword, String newPassword) {
    return accountDAO
        .getById(id)
        .filter(account -> BCrypt.checkpw(oldPassword, account.getPassword()))
        .map(account -> {
          account.setPassword(Account.hashPassword(newPassword));
          return accountDAO.update(account);
        })
        .orElse(false);
  }
}
