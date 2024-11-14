package org.group4.dao;

import java.util.Optional;
import org.group4.base.books.Author;
import org.group4.base.books.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * BookDAO is responsible for CRUD operations on {@link Book} data in the database.
 * This class implements {@link GenericDAO} to provide a standard set of methods
 * for data persistence and retrieval.
 */
public class BookDAO extends BaseDAO implements GenericDAO<Book, String> {

  private static final Logger logger = LoggerFactory.getLogger(BookDAO.class);

  // SQL query constants
  private static final String ADD_BOOK_SQL =
      "INSERT INTO books (isbn, title, subject, publisher, language, number_of_pages) "
          + "VALUES (?, ?, ?, ?, ?, ?)";
  private static final String UPDATE_BOOK_SQL =
      "UPDATE books SET title = ?, subject = ?, publisher = ?, language = ?, number_of_pages = ? "
          + "WHERE isbn = ?";
  private static final String DELETE_BOOK_SQL = "DELETE FROM books WHERE isbn = ?";
  private static final String GET_BOOK_BY_ID_SQL = "SELECT * FROM books WHERE isbn = ?";
  private static final String GET_ALL_BOOKS_SQL = "SELECT * FROM books";
  private static final String GET_AUTHORS_BY_BOOK_SQL =
      "SELECT author_id FROM book_authors WHERE book_isbn = ?";

  @Override
  public boolean add(Book book) {
    try (Connection connection = getConnection();
         PreparedStatement stmt = connection.prepareStatement(ADD_BOOK_SQL)) {
      setBookData(stmt, book);
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error adding book: {}", book, e);
      return false;
    }
  }

  @Override
  public boolean update(Book book) {
    try (Connection connection = getConnection();
         PreparedStatement stmt = connection.prepareStatement(UPDATE_BOOK_SQL)) {
      setBookData(stmt, book);
      stmt.setString(6, book.getISBN());
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error updating book: {}", book, e);
      return false;
    }
  }

  @Override
  public boolean delete(Book book) {
    try (Connection connection = getConnection();
         PreparedStatement stmt = connection.prepareStatement(DELETE_BOOK_SQL)) {
      stmt.setString(1, book.getISBN());
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error deleting book: {}", book, e);
      return false;
    }
  }

  @Override
  public Optional<Book> getById(String isbn) {
    try (Connection connection = getConnection();
         PreparedStatement stmt = connection.prepareStatement(GET_BOOK_BY_ID_SQL)) {
      stmt.setString(1, isbn);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return Optional.of(mapRowToBook(rs, connection));
        }
      }
    } catch (SQLException e) {
      logger.error("Error finding book by ID: {}", isbn, e);
    }
    return Optional.empty();
  }

  @Override
  public List<Book> getAll() {
    List<Book> books = new ArrayList<>();
    try (Connection connection = getConnection();
         PreparedStatement stmt = connection.prepareStatement(GET_ALL_BOOKS_SQL);
         ResultSet rs = stmt.executeQuery()) {
      while (rs.next()) {
        books.add(mapRowToBook(rs, connection));
      }
    } catch (SQLException e) {
      logger.error("Error finding all books", e);
    }
    return books;
  }



  private void setBookData(PreparedStatement stmt, Book book) throws SQLException {
    stmt.setString(1, book.getISBN());
    stmt.setString(2, book.getTitle());
    stmt.setString(3, book.getSubject());
    stmt.setString(4, book.getPublisher());
    stmt.setString(5, book.getLanguage());
    stmt.setInt(6, book.getNumberOfPages());
  }

  private Book mapRowToBook(ResultSet resultSet, Connection connection) throws SQLException {
    String isbn = resultSet.getString("isbn");
    String title = resultSet.getString("title");
    String subject = resultSet.getString("subject");
    String publisher = resultSet.getString("publisher");
    String language = resultSet.getString("language");
    int numberOfPages = resultSet.getInt("numberOfPages");
    Set<Author> authors = new AuthorDAO().getAll().stream()
        .filter(author -> {
          try (PreparedStatement stmt = connection.prepareStatement(GET_AUTHORS_BY_BOOK_SQL)) {
            stmt.setString(1, isbn);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
              if (result.getString("author_id").equals(author.getAuthorId())) {
                return true;
              }
            }
          } catch (SQLException e) {
            logger.error("Error finding authors by book: {}", isbn, e);
          }
          return false;
        })
        .collect(java.util.stream.Collectors.toSet());

    return new Book(isbn, title, subject, publisher, language, numberOfPages, authors);
  }

}

