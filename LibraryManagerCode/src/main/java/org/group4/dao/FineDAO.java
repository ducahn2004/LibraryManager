package org.group4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.group4.module.transactions.Fine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data Access Object for CRUD operations on {@link Fine} entities in the database.
 */
public class FineDAO extends BaseDAO implements GenericDAO<Fine, Integer> {

  /** The logger for FineDAO. */
  private static final Logger logger = LoggerFactory.getLogger(FineDAO.class);

  /** SQL query to add a new fine to the database. */
  private static final String ADD_FINE_SQL = "INSERT INTO fines (id, amount) VALUES (?, ?)";

  /** SQL query to delete a fine from the database by ID. */
  private static final String DELETE_FINE_SQL = "DELETE FROM fines WHERE id = ?";

  /** SQL query to find a fine by ID. */
  private static final String FIND_FINE_BY_ID_SQL = "SELECT * FROM fines WHERE id = ?";

  @Override
  public boolean add(Fine fine) {
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(ADD_FINE_SQL)) {
      preparedStatement.setDouble(1, fine.getAmount());
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error adding fine: {}", fine, e);
      return false;
    }
  }

  @Override
  public boolean delete(Integer id) {
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FINE_SQL)) {
      preparedStatement.setInt(1, id);
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error deleting fine with ID: {}", id, e);
      return false;
    }
  }

  @Override
  public Optional<Fine> getById(Integer id) {
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(FIND_FINE_BY_ID_SQL)) {
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        Fine fine = new Fine();
        fine.setAmount(resultSet.getDouble("amount"));
        return Optional.of(fine);
      }
    } catch (SQLException e) {
      logger.error("Error finding fine by ID: {}", id, e);
    }
    return Optional.empty();
  }
}