package org.group4.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.group4.dao.base.BaseDAO;
import org.group4.model.user.Account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data Access Object (DAO) class for CRUD operations on the {@link Account} entity in the database.
 * This class provides methods to retrieve and update account information using JDBC connection.
 * Each method is executed within a try-with-resources statement to ensure proper resource handling.
 */
public class AccountDAO extends BaseDAO {

  /** The logger for AccountDAO. */
  private static final Logger logger = LoggerFactory.getLogger(AccountDAO.class);

  /** Column names in the account table. */
  private static final String COLUMN_ID = "id";
  private static final String COLUMN_PASSWORD = "password";

  /** SQL statements for CRUD operations on the account table. */
  private static final String GET_ACCOUNT_BY_ID_SQL =
      "SELECT * FROM account WHERE " + COLUMN_ID + " = ?";

  private static final String UPDATE_ACCOUNT_SQL =
      "UPDATE account SET " + COLUMN_PASSWORD + " = ? WHERE " + COLUMN_ID + " = ?";

  /**
   * Retrieves an account by its ID from the database.
   *
   * @param id the account ID
   * @return an {@code Optional} containing the account if found, or empty otherwise
   */
  public Optional<Account> getById(String id) throws SQLException {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ACCOUNT_BY_ID_SQL)) {
      preparedStatement.setString(1, id);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          String retrievedId = resultSet.getString(COLUMN_ID);
          String password = resultSet.getString(COLUMN_PASSWORD);
          return Optional.of(new Account(retrievedId, password));
        }
      }
    } catch (SQLException e) {
      logger.error("Error retrieving account by ID: {}", id, e);
    }
    return Optional.empty();
  }

  /**
   * Updates an account's password in the database.
   *
   * @param account the account to update
   * @return {@code true} if the update was successful, {@code false} otherwise
   */
  public boolean update(Account account) throws SQLException {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT_SQL)) {
      preparedStatement.setString(1, account.getPassword());
      preparedStatement.setString(2, account.getId());
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error updating account: {}", account.getId(), e);
    }
    return false;
  }
}
