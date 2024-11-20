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
 * Data Access Object (DAO) for managing {@link Author} entities in the database.
 * Handles CRUD operations and supports additional functionality for relationships with books.
 */
public class AuthorDAO extends BaseDAO implements GenericDAO<Author, String> {

  /** The logger for AuthorDAO. */
  private static final Logger logger = LoggerFactory.getLogger(AuthorDAO.class);

  /** Column names in the authors table. */
  private static final String COLUMN_AUTHOR_ID = "author_id";
  private static final String COLUMN_NAME = "name";
  private static final String COLUMN_BOOK_ISBN = "book_isbn";
  private static final String COLUMN_MAX_ID = "max_id";

  /** SQL statements for CRUD operations on the authors table. */
  private static final String ADD_AUTHOR_SQL =
      "INSERT INTO authors ("+ COLUMN_AUTHOR_ID +", "+ COLUMN_NAME +") VALUES (?, ?)";

  private static final String UPDATE_AUTHOR_SQL =
      "UPDATE authors SET "+ COLUMN_NAME +" = ? WHERE "+ COLUMN_AUTHOR_ID +" = ?";

  private static final String DELETE_AUTHOR_SQL =
      "DELETE FROM authors WHERE "+ COLUMN_AUTHOR_ID +" = ?";

  private static final String GET_AUTHOR_BY_ID_SQL =
      "SELECT * FROM authors WHERE "+ COLUMN_AUTHOR_ID +" = ?";

  private static final String GET_ALL_AUTHORS_SQL =
      "SELECT * FROM authors";

  private static final String GET_AUTHORS_BY_BOOK_SQL =
      "SELECT a."+ COLUMN_AUTHOR_ID +", a."+ COLUMN_NAME +" FROM authors a "
      + "JOIN book_authors ba ON a."+ COLUMN_AUTHOR_ID +" = ba."+ COLUMN_AUTHOR_ID +" "
      + "WHERE ba."+ COLUMN_BOOK_ISBN +" = ?";

  private static final String GET_MAX_AUTHOR_ID_SQL =
      "SELECT MAX("+ COLUMN_AUTHOR_ID +") AS "+ COLUMN_MAX_ID +" FROM authors";

  @Override
  public boolean add(Author author) {
    try (Connection connection = getConnection()) {
      // Generate a new Author ID
      String newAuthorId = generateAuthorId(connection);
      author.setAuthorId(newAuthorId);

      // Insert new author into database
      try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_AUTHOR_SQL)) {
        preparedStatement.setString(1, author.getAuthorId());
        preparedStatement.setString(2, author.getName());
        return preparedStatement.executeUpdate() > 0;
      }
    } catch (SQLException e) {
      logger.error("Error adding author: {}", author, e);
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
      logger.error("Error updating author: {}", author, e);
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
      logger.error("Error deleting author with ID {}: {}", authorId, e);
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
      logger.error("Error retrieving author by ID {}: {}", authorId, e);
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
      logger.error("Error retrieving all authors", e);
    }
    return authors;
  }

  /**
   * Retrieves authors associated with a specific book.
   *
   * @param isbn the ISBN of the book
   * @return a set of authors associated with the book
   */
  public Set<Author> getAuthorsByBook(String isbn) {
    Set<Author> authors = new HashSet<>();
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_AUTHORS_BY_BOOK_SQL)) {
      preparedStatement.setString(1, isbn);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          authors.add(mapRowToAuthor(resultSet));
        }
      }
    } catch (SQLException e) {
      logger.error("Error retrieving authors for book with ISBN {}: {}", isbn, e);
    }
    return authors;
  }

  /**
   * Maps a ResultSet row to an Author object.
   *
   * @param resultSet the ResultSet to map
   * @return an Author object
   * @throws SQLException if a database access error occurs
   */
  private Author mapRowToAuthor(ResultSet resultSet) throws SQLException {
    String authorId = resultSet.getString(COLUMN_AUTHOR_ID);
    String name = resultSet.getString(COLUMN_NAME);
    return new Author(authorId, name);
  }

  /**
   * Generates a new unique Author ID.
   *
   * @param connection the database connection
   * @return a new Author ID
   * @throws SQLException if a database access error occurs
   */
  private String generateAuthorId(Connection connection) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(GET_MAX_AUTHOR_ID_SQL);
        ResultSet resultSet = statement.executeQuery()) {
      if (resultSet.next()) {
        String maxId = resultSet.getString("max_id"); // Get the maximum Author ID
        if (maxId != null) {
          // Increment the maximum Author ID by 1
          int nextId = Integer.parseInt(maxId.replace("AUTHOR-", "")) + 1;
          return String.format("AUTHOR-%03d", nextId);
        }
      }
    }
    return "AUTHOR-001"; // Default Author ID
  }
}
