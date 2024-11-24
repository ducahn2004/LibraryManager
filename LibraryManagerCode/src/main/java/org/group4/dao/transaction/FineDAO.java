package org.group4.dao.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.group4.dao.base.BaseDAO;
import org.group4.dao.base.GenericDAO;
import org.group4.model.transaction.BookLending;
import org.group4.model.transaction.Fine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data Access Object for CRUD operations on {@link Fine} entities in the database.
 */
public class FineDAO extends BaseDAO implements GenericDAO<Fine, BookLending> {

  /** The logger for FineDAO. */
  private static final Logger logger = LoggerFactory.getLogger(FineDAO.class);

  /** Column names in the fines table. */
  private static final String COLUMN_BARCODE = "barcode";
  private static final String COLUMN_MEMBER_ID = "member_id";
  private static final String COLUMN_AMOUNT = "amount";

  /** SQL statements for CRUD operations on the fines table. */
  private static final String ADD_FINE_SQL =
      "INSERT INTO fines ("
          + COLUMN_BARCODE + ", "
          + COLUMN_MEMBER_ID + ", "
          + COLUMN_AMOUNT + ") "
          + "VALUES (?, ?, ?)";

  private static final String DELETE_FINE_SQL =
      "DELETE FROM fines WHERE " + COLUMN_BARCODE + " = ? AND " + COLUMN_MEMBER_ID + " = ?";

  private static final String FIND_FINE_BY_ID_SQL =
      "SELECT * FROM fines WHERE " + COLUMN_BARCODE + " = ? AND " + COLUMN_MEMBER_ID + " = ?";

  @Override
  public boolean add(Fine fine) throws SQLException {
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
  public boolean delete(BookLending bookLending) throws SQLException {
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
  public Optional<Fine> getById(BookLending bookLending) throws SQLException {
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(FIND_FINE_BY_ID_SQL)) {
      preparedStatement.setString(1, bookLending.getBookItem().getBarcode());
      preparedStatement.setString(2, bookLending.getMember().getMemberId());
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        double amount = resultSet.getDouble(COLUMN_AMOUNT);
        return Optional.of(new Fine(bookLending, amount));
      }
    } catch (SQLException e) {
      logger.error("Error finding fine by ID: {}", bookLending, e);
    }
    return Optional.empty();
  }
}