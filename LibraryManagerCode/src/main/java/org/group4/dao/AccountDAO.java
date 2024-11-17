package org.group4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.group4.module.users.Account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data Access Object for CRUD operations on {@link Account} entities in the database.
 * Implements {@link GenericDAO} to standardize database operations for Accounts.
 */
public class AccountDAO extends BaseDAO implements GenericDAO<Account, String> {

  private static final Logger logger = LoggerFactory.getLogger(AccountDAO.class);

  private static final String GET_ACCOUNT_BY_ID_SQL = "SELECT * FROM account WHERE id = ?";
  private static final String UPDATE_ACCOUNT_SQL = "UPDATE account SET password = ? WHERE id = ?";

  /**
   * Retrieves an account by its ID from the database.
   *
   * @param id the account ID
   * @return an {@code Optional} containing the account if found, or empty otherwise
   */
  @Override
  public Optional<Account> getById(String id) {
    try (Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(GET_ACCOUNT_BY_ID_SQL)) {
      stmt.setString(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return Optional.of(new Account(
              rs.getString("id"),
              rs.getString("password")));
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
  @Override
  public boolean update(Account account) {
    try (Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(UPDATE_ACCOUNT_SQL)) {
      stmt.setString(1, account.getPassword());
      stmt.setString(2, account.getId());
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error updating account: {}", account.getId(), e);
    }
    return false;
  }
}
