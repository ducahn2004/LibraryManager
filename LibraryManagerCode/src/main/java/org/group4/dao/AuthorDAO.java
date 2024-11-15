package org.group4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.group4.module.books.Author;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data Access Object (DAO) class for CRUD operations on the {@link Author} entity in the database.
 * This class provides methods to add, update, delete, and retrieve authors using JDBC connection.
 * Each method is executed within a try-with-resources statement to ensure proper resource handling.
 */
public class AuthorDAO extends BaseDAO implements GenericDAO<Author, String> {

  /** The logger for AuthorDAO. */
  private static final Logger logger = LoggerFactory.getLogger(AuthorDAO.class);

  /** SQL query to add a new author to the database. */
  private static final String ADD_AUTHOR_SQL = "INSERT INTO authors (author_id, name) VALUES (?, ?)";

  /** SQL query to update an existing author in the database. */
  private static final String UPDATE_AUTHOR_SQL = "UPDATE authors SET name = ? WHERE author_id = ?";

  /** SQL query to delete an author from the database by ID. */
  private static final String DELETE_AUTHOR_SQL = "DELETE FROM authors WHERE author_id = ?";

  /** SQL query to find an author by ID. */
  private static final String GET_AUTHOR_BY_ID_SQL = "SELECT * FROM authors WHERE author_id = ?";

  /** SQL query to find all authors in the database. */
  private static final String GET_ALL_AUTHORS_SQL = "SELECT * FROM authors";

  /** SQL query to find the maximum author_id in the database. */
  private static final String GET_MAX_AUTHOR_ID_SQL = "SELECT MAX(author_id) AS max_id FROM authors";

  @Override
  public boolean add(Author author) {
    try (Connection connection = getConnection()) {
      // Generate new Author ID
      String newAuthorId = generateAuthorId(connection);
      author.setAuthorId(newAuthorId);

      // Prepare and execute the SQL INSERT statement
      try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_AUTHOR_SQL)) {
        preparedStatement.setString(1, author.getAuthorId());
        preparedStatement.setString(2, author.getName());
        return preparedStatement.executeUpdate() > 0;
      }
    } catch (SQLException e) {
      logger.error("Error adding author: {}", author.getAuthorId(), e);
    }
    return false;
  }

  @Override
  public boolean update(Author author) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AUTHOR_SQL)) {
      preparedStatement.setString(1, author.getName());
      preparedStatement.setString(2, author.getAuthorId());
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error updating author with ID {}: {}", author.getAuthorId(), e.getMessage());
    }
    return false;
  }

  @Override
  public boolean delete(String authorId) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_AUTHOR_SQL)) {
      preparedStatement.setString(1, authorId);
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error deleting author with ID {}: {}", authorId, e.getMessage());
    }
    return false;
  }

  @Override
  public Optional<Author> getById(String authorId) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_AUTHOR_BY_ID_SQL)) {
      preparedStatement.setString(1, authorId);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return Optional.of(mapRowToAuthor(resultSet));
        }
      }
    } catch (SQLException e) {
      logger.error("Error finding author by ID {}: {}", authorId, e.getMessage());
    }
    return Optional.empty();
  }

  @Override
  public Set<Author> getAll() {
    Set<Author> authors = new HashSet<>();
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_AUTHORS_SQL);
        ResultSet resultSet = preparedStatement.executeQuery()) {

      while (resultSet.next()) {
        authors.add(mapRowToAuthor(resultSet));
      }
    } catch (SQLException e) {
      logger.error("Error retrieving all authors: {}", e.getMessage());
    }
    return authors;
  }

  /**
   * Maps a row from the {@link ResultSet} to an {@link Author} object.
   *
   * @param resultSet the {@link ResultSet} from which to map the author data.
   * @return an {@link Author} object populated with data from the current row in {@link ResultSet}.
   * @throws SQLException if a database access error occurs.
   */
  private Author mapRowToAuthor(ResultSet resultSet) throws SQLException {
    String authorId = formatAuthorId(resultSet.getInt("author_id"));
    String name = resultSet.getString("name");
    return new Author(authorId, name);
  }

  /**
   * Formats an author ID with the "AUTHOR-" prefix and zero padding for consistency.
   *
   * @param id the raw integer ID from the database.
   * @return the formatted author ID string with prefix.
   */
  private String formatAuthorId(int id) {
    return "AUTHOR-" + String.format("%03d", id);
  }

  /**
   * Generates a new Author ID by finding the current maximum ID and incrementing it.
   * @param connection The database connection.
   * @return A new unique Author ID in the format AUTHOR-XXX.
   * @throws SQLException If a database access error occurs.
   */
  private String generateAuthorId(Connection connection) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(GET_MAX_AUTHOR_ID_SQL);
        ResultSet resultSet = statement.executeQuery()) {
      if (resultSet.next()) {
        String maxId = resultSet.getString("max_id");
        if (maxId != null) {
          // Extract the numeric part, increment it, and format the new ID
          int nextId = Integer.parseInt(maxId.replace("AUTHOR-", "")) + 1;
          return formatAuthorId(nextId); // Reuse the formatAuthorId method
        }
      }
    }
    return formatAuthorId(1); // Default ID if no records exist
  }

}
