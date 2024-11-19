package org.group4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.group4.module.transactions.BookLending;
import org.group4.module.transactions.Fine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data Access Object for CRUD operations on {@link Fine} entities in the database.
 */
public class FineDAO extends BaseDAO implements GenericDAO<Fine, BookLending> {

  /** The logger for FineDAO. */
  private static final Logger logger = LoggerFactory.getLogger(FineDAO.class);

  /** SQL query to add a new fine to the database. */
  private static final String ADD_FINE_SQL = "INSERT INTO fines (barcode, member_id, amount) "
      + "VALUES (?, ?, ?)";

  /** SQL query to delete a fine from the database by ID. */
  private static final String DELETE_FINE_SQL = "DELETE FROM fines WHERE barcode = ? AND member_id = ?";

  /** SQL query to find a fine by ID. */
  private static final String FIND_FINE_BY_ID_SQL = "SELECT * FROM fines WHERE barcode = ? AND member_id = ?";

  @Override
  public boolean add(Fine fine) {
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(ADD_FINE_SQL)) {
      preparedStatement.setString(1, fine.getBookLending().getBookItem().getBarcode());
      preparedStatement.setString(2, fine.getBookLending().getMember().getMemberId());
      preparedStatement.setDouble(3, fine.getAmount());
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error adding fine: {}", fine, e);
      return false;
    }
  }

  @Override
  public boolean delete(BookLending bookLending) {
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FINE_SQL)) {
      preparedStatement.setString(1, bookLending.getBookItem().getBarcode());
      preparedStatement.setString(2, bookLending.getMember().getMemberId());
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error deleting fine: {}", bookLending, e);
      return false;
    }
  }

  @Override
  public Optional<Fine> getById(BookLending bookLending) {
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(FIND_FINE_BY_ID_SQL)) {
      preparedStatement.setString(1, bookLending.getBookItem().getBarcode());
      preparedStatement.setString(2, bookLending.getMember().getMemberId());
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        double amount = resultSet.getDouble("amount");
        return Optional.of(new Fine(bookLending, amount));
      }
    } catch (SQLException e) {
      logger.error("Error finding fine by ID: {}", bookLending, e);
    }
    return Optional.empty();
  }
}