package org.group4.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.group4.module.users.Librarian;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data Access Object for CRUD operations on {@link Librarian} entities in the database.
 * Implements {@link GenericDAO} to standardize database operations for Librarians.
 */
public class LibrarianDAO extends BaseDAO implements GenericDAO<Librarian, String> {

  /** Logger for LibrarianDAO class. */
  private static final Logger logger = LoggerFactory.getLogger(LibrarianDAO.class);

  /** SQL query to add a new librarian to the database. */
  private static final String ADD_LIBRARIAN_SQL =
      "INSERT INTO librarian (id, name, date_of_birth, email, phone) VALUES (?, ?, ?, ?, ?)";

  /** SQL query to update an existing librarian in the database. */
  private static final String UPDATE_LIBRARIAN_SQL =
      "UPDATE librarian SET name = ?, date_of_birth = ?, email = ?, phone = ? WHERE id = ?";

  /** SQL query to delete a librarian from the database by ID. */
  private static final String DELETE_LIBRARIAN_SQL = "DELETE FROM librarian WHERE id = ?";

  /** SQL query to find a librarian by ID. */
  private static final String GET_LIBRARIAN_BY_ID_SQL = "SELECT * FROM librarian WHERE id = ?";

  /** SQL query to find all librarians in the database. */
  private static final String GET_ALL_LIBRARIANS_SQL = "SELECT * FROM librarian";

  @Override
  public boolean add(Librarian librarian) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_LIBRARIAN_SQL)) {
      setLibrarianData(preparedStatement, librarian, false);
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
      setLibrarianData(preparedStatement, librarian, true);
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
  public Optional<Librarian> getById(String id) throws SQLException {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_LIBRARIAN_BY_ID_SQL)) {
      preparedStatement.setString(1, id);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return Optional.of(mapRowToLibrarian(resultSet));
        }
      }
    }
    return Optional.empty();
  }

  @Override
  public Collection<Librarian> getAll() {
    List<Librarian> librarians = new ArrayList<>();
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_LIBRARIANS_SQL);
        ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        librarians.add(mapRowToLibrarian(resultSet));
      }
    } catch (SQLException e) {
      logger.error("Error retrieving all librarians", e);
    }
    return librarians;
  }

  /**
   * Maps a row in the database to a {@link Librarian} object.
   *
   * @param resultSet the result set from a SQL query.
   * @return a Librarian object.
   * @throws SQLException if an SQL error occurs during mapping.
   */
  public Librarian mapRowToLibrarian(ResultSet resultSet) throws SQLException {
    return new Librarian(
        resultSet.getString("id"),
        resultSet.getString("name"),
        resultSet.getDate("date_of_birth").toLocalDate(),
        resultSet.getString("email"),
        resultSet.getString("phone")
    );
  }

  /**
   * Sets the data for a librarian into a PreparedStatement.
   * <p>If `isUpdate` is true, the `id` is set only at the last position, required for the WHERE clause.</p>
   *
   * @param preparedStatement the PreparedStatement to set data into
   * @param librarian the Librarian object containing data to be set
   * @param isUpdate whether the operation is an update; if true, places ID last for the WHERE clause
   * @throws SQLException if a database access error occurs
   */
  private void setLibrarianData(PreparedStatement preparedStatement, Librarian librarian,
      boolean isUpdate) throws SQLException {

    int index = 1;

    if (!isUpdate) {
      preparedStatement.setString(index++, librarian.getLibrarianId());
    }

    preparedStatement.setString(index++, librarian.getName());
    preparedStatement.setDate(index++, Date.valueOf(librarian.getDateOfBirth()));
    preparedStatement.setString(index++, librarian.getEmail());
    preparedStatement.setString(index++, librarian.getPhoneNumber());

    if (isUpdate) {
      preparedStatement.setString(index, librarian.getLibrarianId());
    }
  }
}
