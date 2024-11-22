package org.group4.dao.book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.group4.dao.base.FactoryDAO;
import org.group4.dao.base.GenericDAO;
import org.group4.dao.base.BaseDAO;
import org.group4.model.book.Author;
import org.group4.model.book.Book;

import org.group4.model.book.BookItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data Access Object (DAO) for managing {@link Book} entities in the database.
 * Handles CRUD operations and supports additional functionality for relationships with authors.
 */
public class BookDAO extends BaseDAO implements GenericDAO<Book, String> {

  /** The logger for BookDAO. */
  private static final Logger logger = LoggerFactory.getLogger(BookDAO.class);

  /** Column names in the books table. */
  private static final String COLUMN_ISBN = "isbn";
  private static final String COLUMN_TITLE = "title";
  private static final String COLUMN_SUBJECT = "subject";
  private static final String COLUMN_PUBLISHER = "publisher";
  private static final String COLUMN_LANGUAGE = "language";
  private static final String COLUMN_NUMBER_OF_PAGES = "number_of_pages";
  private static final String COLUMN_AUTHOR_ID = "author_id";

  /** SQL statements for CRUD operations on the books table. */
  private static final String ADD_BOOK_SQL =
      "INSERT INTO books ("
          + COLUMN_ISBN + ", "
          + COLUMN_TITLE + ", "
          + COLUMN_SUBJECT + ", "
          + COLUMN_PUBLISHER + ", "
          + COLUMN_LANGUAGE + ", "
          + COLUMN_NUMBER_OF_PAGES + ") "
          + "VALUES (?, ?, ?, ?, ?, ?)";

  private static final String ADD_BOOK_AUTHOR_SQL =
      "INSERT INTO book_authors ("+ COLUMN_ISBN +", "+ COLUMN_AUTHOR_ID +") VALUES (?, ?)";

  private static final String UPDATE_BOOK_SQL =
      "UPDATE books SET "
          + COLUMN_TITLE + " = ?, "
          + COLUMN_SUBJECT + " = ?, "
          + COLUMN_PUBLISHER + " = ?, "
          + COLUMN_LANGUAGE + " = ?, "
          + COLUMN_NUMBER_OF_PAGES + " = ? "
          + "WHERE "+ COLUMN_ISBN + " = ?";

  private static final String GET_BOOK_BY_ID_SQL =
      "SELECT * FROM books WHERE "+ COLUMN_ISBN + " = ?";

  private static final String GET_ALL_BOOKS_SQL = "SELECT * FROM books";

  private static final String DELETE_BOOK_SQL = "DELETE FROM books WHERE " + COLUMN_ISBN + " = ?";

  private static final String DELETE_BOOK_AUTHORS_SQL =
      "DELETE FROM book_authors WHERE " + COLUMN_ISBN + " = ?";

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
      if (getById(book.getISBN()).isPresent()) { // Book already exists
        return false;
      }

      connection.setAutoCommit(false); // Start transaction

      try (PreparedStatement bookStmt = connection.prepareStatement(ADD_BOOK_SQL)) {
        bookStmt.setString(1, book.getISBN());
        bookStmt.setString(2, book.getTitle());
        bookStmt.setString(3, book.getSubject());
        bookStmt.setString(4, book.getPublisher());
        bookStmt.setString(5, book.getLanguage());
        bookStmt.setInt(6, book.getNumberOfPages());
        bookStmt.executeUpdate();
      }

      updateBookAuthors(connection, book); // Add authors to book_authors table

      connection.commit(); // Commit transaction
      return true;
    } catch (SQLException e) {
      logger.error("Error adding book: {}", book, e);
      return false;
    }
  }

  @Override
  public boolean update(Book book) {
    try (Connection connection = getConnection()) {
      PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOK_SQL);
      preparedStatement.setString(1, book.getTitle());
      preparedStatement.setString(2, book.getSubject());
      preparedStatement.setString(3, book.getPublisher());
      preparedStatement.setString(4, book.getLanguage());
      preparedStatement.setInt(5, book.getNumberOfPages());
      preparedStatement.setString(6, book.getISBN());
      preparedStatement.executeUpdate();

      updateBookAuthors(connection, book); // Update authors in book_authors table
      return true;
    } catch (SQLException e) {
      logger.error("Error updating book: {}", book, e);
      return false;
    }
  }

  @Override
  public boolean delete(String isbn) {
    try (Connection connection = getConnection()) {
      connection.setAutoCommit(false);

      // Delete relationships in book_authors table
      try (PreparedStatement deleteAuthorsStmt =
          connection.prepareStatement(DELETE_BOOK_AUTHORS_SQL)) {
        deleteAuthorsStmt.setString(1, isbn);
        deleteAuthorsStmt.executeUpdate();
      }

      // Delete book from books table
      try (PreparedStatement deleteBookStmt = connection.prepareStatement(DELETE_BOOK_SQL)) {
        deleteBookStmt.setString(1, isbn);
        deleteBookStmt.executeUpdate();
      }

      connection.commit(); // Commit transaction
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
          return Optional.ofNullable(mapRowToBook(resultSet)); // Map row to Book object
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
        books.add(mapRowToBook(resultSet)); // Add Book object to list
      }
    } catch (SQLException e) {
      logger.error("Error retrieving all books", e);
    }
    return books;
  }

  /**
   * Maps a row from the books table to a Book object.
   *
   * @param resultSet the result set from a SQL query
   * @return a Book object
   */
  private Book mapRowToBook(ResultSet resultSet) {
    try {
      String isbn = resultSet.getString(COLUMN_ISBN);
      String title = resultSet.getString(COLUMN_TITLE);
      String subject = resultSet.getString(COLUMN_SUBJECT);
      String publisher = resultSet.getString(COLUMN_PUBLISHER);
      String language = resultSet.getString(COLUMN_LANGUAGE);
      int numberOfPages = resultSet.getInt(COLUMN_NUMBER_OF_PAGES);
      Set<Author> authors = authorDAO.getAuthorsByBook(isbn);
      return new Book(isbn, title, subject, publisher, language, numberOfPages, authors);
    } catch (SQLException e) {
      logger.error("Error mapping row to Book object", e);
      return null;
    }
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

    // Return existing author ID if found
    if (existingAuthor.isPresent()) {
      return existingAuthor.get().getAuthorId();
    }

    // Create new author if not found
    authorDAO.add(author);
    return author.getAuthorId();
  }

  /**
   * Retrieves all book items associated with a book.
   *
   * @param isbn the ISBN of the book
   * @return a list of book items associated with the book
   */
  public List<BookItem> getAllBookItems(String isbn) {
    try {
      BookItemDAO bookItemDAO = new BookItemDAO();
      return bookItemDAO.getAllByIsbn(isbn);
    } catch (Exception e) {
      logger.error("Error retrieving book items for book with ISBN {}: {}", isbn, e);
      return new ArrayList<>();
    }

  }

  /**
   * Updates the authors associated with a book in the database.
   *
   * @param connection the connection to the database
   * @param book the book to update
   */
  private void updateBookAuthors(Connection connection, Book book) {
    try (PreparedStatement deleteAuthorsStmt = connection.prepareStatement(DELETE_BOOK_AUTHORS_SQL)) {
      deleteAuthorsStmt.setString(1, book.getISBN());
      deleteAuthorsStmt.executeUpdate();
    } catch (SQLException e) {
      logger.error("Error deleting authors for book: {}", book, e);
    }

    // Add authors to book_authors table
    for (Author author : book.getAuthors()) {
      String authorId = findOrCreateAuthor(author);
      try (PreparedStatement bookAuthorStmt = connection.prepareStatement(ADD_BOOK_AUTHOR_SQL)) {
        bookAuthorStmt.setString(1, book.getISBN());
        bookAuthorStmt.setString(2, authorId);
        bookAuthorStmt.executeUpdate();
      } catch (SQLException e) {
        logger.error("Error adding author to book: {}", author, e);
      }
    }
  }
}

