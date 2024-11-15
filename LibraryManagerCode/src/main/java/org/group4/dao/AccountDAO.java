package org.group4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.group4.module.users.Account;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code AccountDAO} class is responsible for handling database operations
 * related to {@code Account} objects, such as verifying credentials and updating accounts.
 */
public class AccountDAO extends BaseDAO {

  /** Logger for the AccountDAO class. */
  private static final Logger logger = LoggerFactory.getLogger(AccountDAO.class);

  /** SQL query to retrieve an account by its ID from the database. */
  private static final String GET_ACCOUNT_BY_ID_SQL = "SELECT * FROM account WHERE id = ?";

  /** SQL query to update an account's password in the database. */
  private static final String UPDATE_ACCOUNT_SQL = "UPDATE account SET password = ? WHERE id = ?";

  /** SQL query to retrieve an account's password from the database. */
  private static final String GET_ACCOUNT_PASSWORD_SQL = "SELECT password FROM account WHERE id = ?";

  /**
   * Retrieves an account by its ID from the database.
   *
   * @param id the account ID
   * @return an {@code Optional} containing the account if found,
   *         or an empty {@code Optional} otherwise
   */
  public Optional<Account> getById(String id) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ACCOUNT_BY_ID_SQL)) {
      preparedStatement.setString(1, id);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return Optional.of(new Account(resultSet.getString("id"),
              resultSet.getString("password")));
        }
      }
    } catch (SQLException e) {
      logger.error("Error retrieving account by ID: {}", id, e);
    }
    return Optional.empty();
  }


  /**
   * Updates the account's password in the database.
   *
   * @param account the account object with updated information
   * @return {@code true} if the update was successful, {@code false} otherwise
   */
  public boolean update(Account account) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT_SQL)) {
      preparedStatement.setString(1, account.getPassword());
      preparedStatement.setString(2, account.getId());
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error updating account: {}", account.getId(), e);
      return false;
    }
  }

  /**
   * Retrieves the stored hashed password for the given account ID.
   *
   * @param id the account ID
   * @return an {@code Optional} containing the hashed password if it exists,
   *         or an empty {@code Optional} if not found
   */
  public Optional<String> getStoredHashedPassword(String id) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ACCOUNT_PASSWORD_SQL)) {
      preparedStatement.setString(1, id);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return Optional.of(resultSet.getString("password"));
        }
      }
    } catch (SQLException e) {
      logger.error("Error retrieving hashed password for account ID: {}", id, e);
    }
    return Optional.empty();
  }

  /**
   * Verifies if the provided credentials match the stored hashed password for the account ID.
   *
   * @param id       the account ID
   * @param password the plain text password to verify
   * @return {@code true} if the provided password matches the stored hashed password,
   *         {@code false} otherwise
   */
  public boolean verifyCredentials(String id, String password) {
    // Fetch the stored hashed password for the account from the database
    Optional<String> storedHashedPassword = getStoredHashedPassword(id);

    // Check if the password matches
    return storedHashedPassword.isPresent() && BCrypt.checkpw(password, storedHashedPassword.get());
  }
}
