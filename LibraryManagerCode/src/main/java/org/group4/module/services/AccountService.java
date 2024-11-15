package org.group4.module.services;

import org.group4.module.users.Account;
import org.group4.dao.AccountDAO;
import org.group4.dao.FactoryDAO;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Service class for handling account-related business logic.
 */
public class AccountService {

  private final AccountDAO accountDAO;

  public AccountService() {
    this.accountDAO = FactoryDAO.getAccountDAO();
  }

  /**
   * Logs in the user by verifying the provided credentials.
   *
   * @param id       the account ID
   * @param password the plain text password
   * @return the corresponding {@code Account} if login is successful, or {@code null} otherwise
   */
  public Account login(String id, String password) {
    Account account = accountDAO.getById(id).orElse(null);
    if (account != null && BCrypt.checkpw(password, account.getPassword())) {
      return account;
    }
    return null;
  }

  /**
   * Changes the account's password if the old password is correct.
   *
   * @param account      the account to update
   * @param oldPassword  the current password for verification
   * @param newPassword  the new plain text password
   * @return {@code true} if the password change is successful, {@code false} otherwise
   */
  public boolean changePassword(Account account, String oldPassword, String newPassword) {
    if (BCrypt.checkpw(oldPassword, account.getPassword())) {
      account.setPassword(Account.hashPassword(newPassword));
      return accountDAO.update(account);
    }
    return false;
  }
}
