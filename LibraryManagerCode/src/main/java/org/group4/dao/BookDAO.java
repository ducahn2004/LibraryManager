package org.group4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.group4.module.books.Author;
import org.group4.module.books.Book;

import org.group4.module.books.BookItem;
import org.group4.module.enums.BookFormat;
import org.group4.module.enums.BookStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BookDAO is responsible for CRUD operations on {@link Book} data in the database.
 * <p>This class implements {@link GenericDAO} to provide a standard set of methods
 * for data persistence and retrieval.</p>
 */
public class BookDAO extends BaseDAO implements GenericDAO<Book, String> {

  /** Logger instance for BookDAO. */
  private static final Logger logger = LoggerFactory.getLogger(BookDAO.class);

  /** SQL query to add a new book to the database. */
  private static final String ADD_BOOK_SQL =
      "INSERT INTO books (isbn, title, subject, publisher, language, number_of_pages) "
          + "VALUES (?, ?, ?, ?, ?, ?)";

  /** SQL query to update an existing book in the database. */
  private static final String UPDATE_BOOK_SQL =
      "UPDATE books SET title = ?, subject = ?, publisher = ?, language = ?, number_of_pages = ? "
          + "WHERE isbn = ?";

  /** SQL query to delete a book from the database by ISBN. */
  private static final String DELETE_BOOK_SQL = "DELETE FROM books WHERE isbn = ?";

  /** SQL query to find a book by ISBN. */
  private static final String GET_BOOK_BY_ID_SQL = "SELECT * FROM books WHERE isbn = ?";

  /** SQL query to find all books in the database. */
  private static final String GET_ALL_BOOKS_SQL = "SELECT * FROM books";

  /** SQL query to find all authors of a book by ISBN. */
  private static final String GET_AUTHORS_BY_BOOK_SQL =
      "SELECT authors.author_id, authors.name FROM authors "
          + "JOIN book_authors ON authors.author_id = book_authors.author_id "
          + "WHERE book_authors.book_isbn = ?";

  @Override
  public boolean add(Book book) {
    // Adds a new book to the database with data from the Book object.
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_BOOK_SQL)) {
      setBookData(preparedStatement, book, false); // Set data for insertion
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error adding book: {}", book, e);
      return false;
    }
  }

  @Override
  public boolean update(Book book) {
    // Updates the existing book's information in the database.
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOK_SQL)) {
      setBookData(preparedStatement, book, true); // Set data with update mode
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error updating book: {}", book, e);
      return false;
    }
  }

  @Override
  public boolean delete(String isbn) {
    // Deletes a book by its ISBN.
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOK_SQL)) {
      preparedStatement.setString(1, isbn);
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error deleting book with ID: {}", isbn, e);
      return false;
    }
  }

  @Override
  public Optional<Book> getById(String isbn) {
    // Retrieves a book by its ISBN.
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_BOOK_BY_ID_SQL)) {
      preparedStatement.setString(1, isbn);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return Optional.of(mapRowToBook(resultSet, connection));
        }
      }
    } catch (SQLException e) {
      logger.error("Error finding book by ID: {}", isbn, e);
    }
    return Optional.empty();
  }

  @Override
  public List<Book> getAll() {
    // Retrieves all books from the database.
    List<Book> books = new ArrayList<>();
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BOOKS_SQL);
        ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        books.add(mapRowToBook(resultSet, connection));
      }
    } catch (SQLException e) {
      logger.error("Error finding all books", e);
    }
    return books;
  }

  /**
   * Sets the data for a book into a PreparedStatement.
   * <p>If `isUpdate` is true, the `ISBN` is set only at the last position, required for the WHERE clause.</p>
   *
   * @param preparedStatement the PreparedStatement to set data into
   * @param book the Book object containing data to be set
   * @param isUpdate whether the operation is an update; if true, places ISBN last for the WHERE clause
   * @throws SQLException if a database access error occurs
   */
  private void setBookData(PreparedStatement preparedStatement, Book book, boolean isUpdate)
      throws SQLException {
    int index = 1;
    if (!isUpdate) {
      // Set ISBN at the beginning only for `add`
      preparedStatement.setString(index++, book.getISBN());
    }

    // Set the other attributes
    preparedStatement.setString(index++, book.getTitle());
    preparedStatement.setString(index++, book.getSubject());
    preparedStatement.setString(index++, book.getPublisher());
    preparedStatement.setString(index++, book.getLanguage());
    preparedStatement.setInt(index++, book.getNumberOfPages());

    if (isUpdate) {
      // Set ISBN at the last position for `update`
      preparedStatement.setString(index, book.getISBN());
    }
  }

  /**
   * Maps the current row in the {@link ResultSet} to a {@link Book} object.
   * <p>Extracts attributes from the result set and initializes a {@link Book} object.</p>
   *
   * @param resultSet the {@link ResultSet} containing book data
   * @param connection the database connection to retrieve author data for the book
   * @return a new {@link Book} object populated with data from the {@link ResultSet}
   * @throws SQLException if a database access error occurs
   */
  private Book mapRowToBook(ResultSet resultSet, Connection connection) throws SQLException {
    // Map book attributes from ResultSet to a new Book object
    String isbn = resultSet.getString("isbn");
    String title = resultSet.getString("title");
    String subject = resultSet.getString("subject");
    String publisher = resultSet.getString("publisher");
    String language = resultSet.getString("language");
    int numberOfPages = resultSet.getInt("number_of_pages");

    // Retrieve associated authors for the book
    Set<Author> authors = getAuthorsByBook(isbn, connection);

    return new Book(isbn, title, subject, publisher, language, numberOfPages, authors);
  }

  /**
   * Retrieves a set of {@link Author} objects associated with a specific book.<p>
   * Uses the `book_authors` join table to fetch authors for the given ISBN.</p>
   *
   * @param isbn the ISBN of the book whose authors are retrieved
   * @param connection the database connection to execute the query
   * @return a {@link Set} of {@link Author} objects associated with the book
   */
  private Set<Author> getAuthorsByBook(String isbn, Connection connection) {
    Set<Author> authors = new HashSet<>();

    try (PreparedStatement preparedStatement = connection.prepareStatement(GET_AUTHORS_BY_BOOK_SQL)) {
      preparedStatement.setString(1, isbn);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          String authorId = resultSet.getString("author_id");
          String authorName = resultSet.getString("name");
          authors.add(new Author(authorId, authorName));
        }
      }
    } catch (SQLException e) {
      logger.error("Error retrieving authors for book ISBN: {}", isbn, e);
    }
    return authors;
  }

  /**
   * Retrieves all BookItems for a given book using its ISBN.
   *
   * @param isbn the ISBN of the book.
   * @return a list of BookItems associated with the given ISBN.
   * @throws SQLException if a database access error occurs.
   */
  public List<BookItem> getAllBookItems(String isbn) throws SQLException {
    BookItemDAO bookItemDAO = new BookItemDAO();
    return bookItemDAO.getAllByIsbn(isbn);
  }

}
