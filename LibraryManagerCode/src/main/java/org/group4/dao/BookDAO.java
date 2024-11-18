package org.group4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.group4.module.books.Author;
import org.group4.module.books.Book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data Access Object (DAO) for managing {@link Book} entities in the database.
 * Handles CRUD operations and manages relationships between books and authors.
 */
public class BookDAO extends BaseDAO implements GenericDAO<Book, String> {

  /** The logger for BookDAO. */
  private static final Logger logger = LoggerFactory.getLogger(BookDAO.class);

  /** SQL statements for CRUD operations on the books table. */
  private static final String ADD_BOOK_SQL =
      "INSERT INTO books (isbn, title, subject, publisher, language, number_of_pages) "
          + "VALUES (?, ?, ?, ?, ?, ?)";

  /** SQL statements for CRUD operations on the books table. */
  private static final String ADD_BOOK_AUTHOR_SQL =
      "INSERT INTO book_authors (book_isbn, author_id) VALUES (?, ?)";

  /** SQL statements for CRUD operations on the books table. */
  private static final String GET_BOOK_BY_ID_SQL = "SELECT * FROM books WHERE isbn = ?";

  /** SQL statements for CRUD operations on the books table. */
  private static final String GET_ALL_BOOKS_SQL = "SELECT * FROM books";

  /** SQL statements for CRUD operations on the books table. */
  private static final String DELETE_BOOK_SQL = "DELETE FROM books WHERE isbn = ?";

  /** SQL statements for CRUD operations on the books table. */
  private static final String DELETE_BOOK_AUTHORS_SQL =
      "DELETE FROM book_authors WHERE book_isbn = ?";

  /** The AuthorDAO dependency for managing authors. */
  private final AuthorDAO authorDAO;

  /**
   * Constructs a new BookDAO with a new AuthorDAO instance.
   */
  public BookDAO() {
    this.authorDAO = FactoryDAO.getAuthorDAO();
  }

  @Override
  public boolean add(Book book) {
    try (Connection connection = getConnection()) {
      // Start transaction
      connection.setAutoCommit(false);

      // Insert book into books table
      try (PreparedStatement bookStmt = connection.prepareStatement(ADD_BOOK_SQL)) {
        bookStmt.setString(1, book.getISBN());
        bookStmt.setString(2, book.getTitle());
        bookStmt.setString(3, book.getSubject());
        bookStmt.setString(4, book.getPublisher());
        bookStmt.setString(5, book.getLanguage());
        bookStmt.setInt(6, book.getNumberOfPages());
        bookStmt.executeUpdate();
      }

      // Add authors and establish relationships in book_authors table
      for (Author author : book.getAuthors()) {
        String authorId = findOrCreateAuthor(author);
        try (PreparedStatement bookAuthorStmt = connection.prepareStatement(ADD_BOOK_AUTHOR_SQL)) {
          bookAuthorStmt.setString(1, book.getISBN());
          bookAuthorStmt.setString(2, authorId);
          bookAuthorStmt.executeUpdate();
        }
      }

      connection.commit();
      return true;
    } catch (SQLException e) {
      logger.error("Error adding book: {}", book, e);
      return false;
    }
  }

  @Override
  public boolean delete(String isbn) {
    try (Connection connection = getConnection()) {
      connection.setAutoCommit(false);

      // Delete relationships in book_authors table
      try (PreparedStatement deleteAuthorsStmt = connection.prepareStatement(DELETE_BOOK_AUTHORS_SQL)) {
        deleteAuthorsStmt.setString(1, isbn);
        deleteAuthorsStmt.executeUpdate();
      }

      // Delete book from books table
      try (PreparedStatement deleteBookStmt = connection.prepareStatement(DELETE_BOOK_SQL)) {
        deleteBookStmt.setString(1, isbn);
        deleteBookStmt.executeUpdate();
      }

      connection.commit();
      return true;
    } catch (SQLException e) {
      logger.error("Error deleting book with ISBN {}: {}", isbn, e);
      return false;
    }
  }

  @Override
  public Optional<Book> getById(String isbn) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_BOOK_BY_ID_SQL)) {
      preparedStatement.setString(1, isbn);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return Optional.of(mapRowToBook(resultSet));
        }
      }
    } catch (SQLException e) {
      logger.error("Error retrieving book with ISBN {}: {}", isbn, e);
    }
    return Optional.empty();
  }

  @Override
  public List<Book> getAll() {
    List<Book> books = new ArrayList<>();
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BOOKS_SQL);
        ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        books.add(mapRowToBook(resultSet));
      }
    } catch (SQLException e) {
      logger.error("Error retrieving all books", e);
    }
    return books;
  }

  /**
   * Maps a row in the ResultSet to a Book object, including its authors.
   *
   * @param resultSet the ResultSet containing book data
   * @return a Book object with data from the ResultSet
   * @throws SQLException if a database error occurs
   */
  private Book mapRowToBook(ResultSet resultSet) throws SQLException {
    String isbn = resultSet.getString("isbn");
    String title = resultSet.getString("title");
    String subject = resultSet.getString("subject");
    String publisher = resultSet.getString("publisher");
    String language = resultSet.getString("language");
    int numberOfPages = resultSet.getInt("number_of_pages");
    Set<Author> authors = authorDAO.getAuthorsByBook(isbn);
    return new Book(isbn, title, subject, publisher, language, numberOfPages, authors);
  }

  /**
   * Finds an existing author by name or creates a new one.
   *
   * @param author the author to find or create
   * @return the author's ID
   */
  private String findOrCreateAuthor(Author author) {
    // Check if the author already exists
    Optional<Author> existingAuthor = authorDAO.getAll().stream()
        .filter(a -> a.getName().equals(author.getName()))
        .findFirst();

    if (existingAuthor.isPresent()) {
      return existingAuthor.get().getAuthorId();
    }

    // Create new author if not found
    authorDAO.add(author);
    return author.getAuthorId();
  }

}
