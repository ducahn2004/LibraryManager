package org.group4.service.user;

import org.group4.model.user.Account;
import org.group4.dao.user.AccountDAO;
import org.group4.dao.base.FactoryDAO;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class for handling account-related business logic.
 */
public class AccountService {

  private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

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
   * Changes the password for the specified account.
   *
   * @param id          the account ID
   * @param oldPassword the old password
   * @param newPassword the new password
   * @return {@code true} if the password is successfully changed, {@code false} otherwise
   */
  public boolean changePassword(String id, String oldPassword, String newPassword) {
    return accountDAO
        .getById(id)
        .filter(account -> {
          boolean isOldPasswordCorrect = BCrypt.checkpw(oldPassword, account.getPassword());
          if (!isOldPasswordCorrect) {
            logger.warn("Old password is incorrect for account ID: {}", id);
          }
          return isOldPasswordCorrect;
        })
        .map(account -> {
          String hashedNewPassword = Account.hashPassword(newPassword);
          Account updatedAccount = new Account(id, hashedNewPassword);
          boolean isUpdated = accountDAO.update(updatedAccount);
          if (isUpdated) {
            logger.info("Password updated successfully for account ID: {}", id);
          } else {
            logger.error("Failed to update password for account ID: {}", id);
          }
          return isUpdated;
        })
        .orElse(false);
  }
}
