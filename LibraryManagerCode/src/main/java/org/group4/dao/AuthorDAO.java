package org.group4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

  @Override
  public boolean add(Author author) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement =
            connection.prepareStatement(ADD_AUTHOR_SQL, Statement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setString(1, author.getAuthorId()); // Set authorId parameter
      preparedStatement.setString(2, author.getName()); // Set author name
      int rowsAffected = preparedStatement.executeUpdate();

      if (rowsAffected > 0) {
        try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
          if (resultSet.next()) {
            int generatedId = resultSet.getInt(1);
            author.setAuthorId(formatAuthorId(generatedId)); // Format and set authorId
          }
        }
        return true;
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
  public boolean delete(Author author) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_AUTHOR_SQL)) {
      preparedStatement.setString(1, author.getAuthorId());
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error deleting author with ID {}: {}", author.getAuthorId(), e.getMessage());
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
}
