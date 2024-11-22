package org.group4.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.group4.model.users.Librarian;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data Access Object for CRUD operations on {@link Librarian} entities in the database.
 * Implements {@link GenericDAO} to standardize database operations for Librarians.
 */
public class LibrarianDAO extends BaseDAO implements GenericDAO<Librarian, String> {

  /** Logger for LibrarianDAO class. */
  private static final Logger logger = LoggerFactory.getLogger(LibrarianDAO.class);

  /** Column names in the librarian table. */
  private static final String COLUMN_ID = "id";
  private static final String COLUMN_NAME = "name";
  private static final String COLUMN_DATE_OF_BIRTH = "date_of_birth";
  private static final String COLUMN_EMAIL = "email";
  private static final String COLUMN_PHONE = "phone";

  /** SQL statements for CRUD operations on the librarian table. */
  private static final String ADD_LIBRARIAN_SQL =
      "INSERT INTO librarian ("
          + COLUMN_ID + ", "
          + COLUMN_NAME + ", "
          + COLUMN_DATE_OF_BIRTH + ", "
          + COLUMN_EMAIL + ", "
          + COLUMN_PHONE + ") "
          + "VALUES (?, ?, ?, ?, ?)";

  private static final String UPDATE_LIBRARIAN_SQL =
      "UPDATE librarian SET "
          + COLUMN_NAME + " = ?, "
          + COLUMN_DATE_OF_BIRTH + " = ?, "
          + COLUMN_EMAIL + " = ?, "
          + COLUMN_PHONE + " = ? "
          + "WHERE " + COLUMN_ID + " = ?";

  private static final String DELETE_LIBRARIAN_SQL =
      "DELETE FROM librarian WHERE " + COLUMN_ID + " = ?";

  private static final String GET_LIBRARIAN_BY_ID_SQL =
      "SELECT * FROM librarian WHERE " + COLUMN_ID + " = ?";

  @Override
  public boolean add(Librarian librarian) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_LIBRARIAN_SQL)) {
      preparedStatement.setString(1, librarian.getLibrarianId());
      preparedStatement.setString(2, librarian.getName());
      preparedStatement.setDate(3, Date.valueOf(librarian.getDateOfBirth()));
      preparedStatement.setString(4, librarian.getEmail());
      preparedStatement.setString(5, librarian.getPhoneNumber());
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error adding librarian: {}", librarian, e);
      return false;
    }
  }

  @Override
  public boolean update(Librarian librarian) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_LIBRARIAN_SQL)) {
      preparedStatement.setString(1, librarian.getName());
      preparedStatement.setDate(2, Date.valueOf(librarian.getDateOfBirth()));
      preparedStatement.setString(3, librarian.getEmail());
      preparedStatement.setString(4, librarian.getPhoneNumber());
      preparedStatement.setString(5, librarian.getLibrarianId());
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error updating librarian: {}", librarian, e);
      return false;
    }
  }

  @Override
  public boolean delete(String id) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_LIBRARIAN_SQL)) {
      preparedStatement.setString(1, id);
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error deleting librarian with ID: {}", id, e);
      return false;
    }
  }

  @Override
  public Optional<Librarian> getById(String id) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_LIBRARIAN_BY_ID_SQL)) {
      preparedStatement.setString(1, id);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return Optional.of(mapRowToLibrarian(resultSet));
        }
      }
    } catch (SQLException e) {
      logger.error("Error retrieving librarian with ID: {}", id, e);
    }
    return Optional.empty();
  }

  /**
   * Maps a row in the database to a {@link Librarian} object.
   *
   * @param resultSet the result set from a SQL query.
   * @return a Librarian object.
   */
  public Librarian mapRowToLibrarian(ResultSet resultSet){
    try {
      return new Librarian(
          resultSet.getString(COLUMN_ID),
          resultSet.getString(COLUMN_NAME),
          resultSet.getDate(COLUMN_DATE_OF_BIRTH).toLocalDate(),
          resultSet.getString(COLUMN_EMAIL),
          resultSet.getString(COLUMN_PHONE)
      );
    } catch (SQLException e) {
      logger.error("Error mapping row to Librarian object", e);
      return null;
    }
  }
}
