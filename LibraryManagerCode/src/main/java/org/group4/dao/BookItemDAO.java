package org.group4.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.group4.module.books.Book;
import org.group4.module.books.BookItem;
import org.group4.module.books.Rack;
import org.group4.module.enums.BookFormat;
import org.group4.module.enums.BookStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BookItemDAO is responsible for CRUD operations on {@link BookItem} data in the database.
 * It implements the {@link GenericDAO} interface to provide standard methods
 * for persisting and retrieving {@link BookItem} data.
 */
public class BookItemDAO extends BaseDAO implements GenericDAO<BookItem, String> {

  /** Logger for BookItemDAO class. */
  private static final Logger logger = LoggerFactory.getLogger(BookItemDAO.class);

  /** SQL query to add a new book item to the database. */
  private static final String ADD_BOOK_ITEM_SQL =
      "INSERT INTO book_items (barcode, ISBN, isReferenceOnly, borrowed, dueDate, price, format, "
          + "status, dateOfPurchase, publicationDate, rackNumber) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  /** SQL query to update an existing book item in the database. */
  private static final String UPDATE_BOOK_ITEM_SQL =
      "UPDATE book_items SET isReferenceOnly = ?, borrowed = ?, dueDate = ?, price = ?, format = ?, "
          + "status = ?, dateOfPurchase = ?, publicationDate = ?, rackNumber = ? WHERE barcode = ?";

  /** SQL query to delete a book item from the database by barcode. */
  private static final String DELETE_BOOK_ITEM_SQL = "DELETE FROM book_items WHERE barcode = ?";

  /** SQL query to find a book item by barcode. */
  private static final String GET_ALL_BOOK_ITEMS_SQL = "SELECT * FROM book_items";

  /** SQL query to find a book item by ISBN. */
  private static final String GET_BOOK_ITEM_BY_ISBN_SQL = "SELECT * FROM book_items WHERE ISBN = ?";

  /** SQL query to find the maximum barcode for a given ISBN. */
  private static final String GET_MAX_BARCODE_SQL =
      "SELECT MAX(barcode) AS max_barcode FROM book_items WHERE barcode LIKE ?";

  /** SQL query to check if a rack exists in the database. */
  private static final String CHECK_RACK_SQL = "SELECT 1 FROM racks WHERE numberRack = ?";

  @Override
  public boolean add(BookItem bookItem) {
    try (Connection connection = getConnection()) {
      // Check if the rack exists in the database
      if (bookItem.getPlacedAt() == null || !rackExists(connection, bookItem.getPlacedAt().getNumberRack())) {
        logger.error("Invalid rack or rack does not exist");
        return false;
      }

      // Generate a new barcode
      String newBarcode = generateBarcode(connection, bookItem.getISBN());
      bookItem.setBarcode(newBarcode);

      // Insert new book item into database
      try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_BOOK_ITEM_SQL)) {
        preparedStatement.setString(1, newBarcode);
        preparedStatement.setString(2, bookItem.getISBN());
        preparedStatement.setBoolean(3, bookItem.getIsReferenceOnly());
        preparedStatement.setDate(4, bookItem.getBorrowed() != null ?
            Date.valueOf(bookItem.getBorrowed()) : null);
        preparedStatement.setDate(5, bookItem.getDueDate() != null ?
            Date.valueOf(bookItem.getDueDate()) : null);
        preparedStatement.setDouble(6, bookItem.getPrice());
        preparedStatement.setString(7, bookItem.getFormat().name());
        preparedStatement.setString(8, bookItem.getStatus().name());
        preparedStatement.setDate(9, Date.valueOf(bookItem.getDateOfPurchase()));
        preparedStatement.setDate(10, Date.valueOf(bookItem.getPublicationDate()));
        preparedStatement.setInt(11, bookItem.getPlacedAt().getNumberRack());

        return preparedStatement.executeUpdate() > 0; // Return true if successful
      }
    } catch (SQLException e) {
      logger.error("Error adding book item: {}", bookItem, e); // Log error
      return false;
    }
  }

  @Override
  public boolean update(BookItem bookItem) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOK_ITEM_SQL)) {
      preparedStatement.setBoolean(1, bookItem.getIsReferenceOnly());
      preparedStatement.setDate(2, bookItem.getBorrowed() != null ?
          Date.valueOf(bookItem.getBorrowed()) : null);
      preparedStatement.setDate(3, bookItem.getDueDate() != null ?
          Date.valueOf(bookItem.getDueDate()) : null);
      preparedStatement.setDouble(4, bookItem.getPrice());
      preparedStatement.setString(5, bookItem.getFormat().name());
      preparedStatement.setString(6, bookItem.getStatus().name());
      preparedStatement.setDate(7, Date.valueOf(bookItem.getDateOfPurchase()));
      preparedStatement.setDate(8, Date.valueOf(bookItem.getPublicationDate()));
      preparedStatement.setInt(9, bookItem.getPlacedAt().getNumberRack());
      preparedStatement.setString(10, bookItem.getBarcode());
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error updating book item: {}", bookItem, e);
      return false;
    }
  }

  @Override
  public boolean delete(String barcode) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOK_ITEM_SQL)) {
      preparedStatement.setString(1, barcode);
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error deleting book item with barcode: {}", barcode, e);
      return false;
    }
  }

  @Override
  public Optional<BookItem> getById(String barcode) throws SQLException {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BOOK_ITEMS_SQL)) {
      preparedStatement.setString(1, barcode);
      try (ResultSet rs = preparedStatement.executeQuery()) {
        if (rs.next()) {
          return Optional.of(mapRowToBookItem(rs));
        }
      }
    }
    return Optional.empty();
  }

  @Override
  public List<BookItem> getAll() {
    List<BookItem> bookItems = new ArrayList<>();
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BOOK_ITEMS_SQL);
        ResultSet resultSet = preparedStatement.executeQuery()) {

      while (resultSet.next()) {
        BookItem bookItem = mapRowToBookItem(resultSet);
        bookItems.add(bookItem);
      }
    } catch (SQLException e) {
      logger.error("Error retrieving all book items", e);
    }
    return bookItems;
  }

  /**
   * Maps a row from the result set to a BookItem object.
   *
   * @param resultSet the ResultSet containing the row data to be mapped.
   * @return the mapped BookItem object.
   * @throws SQLException if an error occurs while accessing the ResultSet or the database.
   */
  private BookItem mapRowToBookItem(ResultSet resultSet) throws SQLException {
    // Retrieve the associated Book object using ISBN from the database
    Book book = FactoryDAO.getBookDAO().getById(resultSet.getString("isbn"))
        .orElseThrow(() -> new SQLException("Book not found for ISBN"));

    // Retrieve the associated Rack object using rackNumber from the database
    Rack rack = FactoryDAO.getRackDAO().getById(resultSet.getInt("rackNumber"))
        .orElseThrow(() -> new SQLException("Rack not found for rack number"));

    // Construct and return a new BookItem directly with values from ResultSet and associated objects
    return new BookItem(
        book.getISBN(),
        book.getTitle(),
        book.getSubject(),
        book.getPublisher(),
        book.getLanguage(),
        book.getNumberOfPages(),
        book.getAuthors(),
        resultSet.getString("barcode"),
        resultSet.getBoolean("isReferenceOnly"),
        resultSet.getDate("borrowed") != null ?
            resultSet.getDate("borrowed").toLocalDate() : null,
        resultSet.getDate("dueDate") != null ?
            resultSet.getDate("dueDate").toLocalDate() : null,
        resultSet.getDouble("price"),
        BookFormat.valueOf(resultSet.getString("format")),
        BookStatus.valueOf(resultSet.getString("status")),
        resultSet.getDate("dateOfPurchase").toLocalDate(),
        resultSet.getDate("publicationDate").toLocalDate(),
        rack
    );
  }

  /**
   * Formats  barcode for a book item.
   *
   * @param isbn the ISBN of the book
   * @param barcode the barcode number
   * @return the formatted barcode as a String
   */
  private String formatBarcode(String isbn, int barcode) {
    return isbn + String.format("-%04d", barcode);
  }

  /**
   * Generates a new barcode for a book item.
   *
   * @param connection the database connection
   * @param isbn the ISBN of the book
   * @return the generated barcode as a String
   * @throws SQLException if a database access error occurs
   */
  public String generateBarcode(Connection connection, String isbn) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement(GET_MAX_BARCODE_SQL)) {
      preparedStatement.setString(1, isbn + "-%");
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          String maxBarcode = resultSet.getString("max_barcode");
          if (maxBarcode != null && maxBarcode.matches("^" + isbn + "-\\d{4}$")) {
            String[] parts = maxBarcode.split("-");
            int nextBarcode = Integer.parseInt(parts[parts.length - 1]) + 1;
            return formatBarcode(isbn, nextBarcode);
          }
        }
      }
    }
    return formatBarcode(isbn, 1);
  }


  /**
   * Retrieves all BookItems for the given ISBN.
   *
   * @param isbn the ISBN of the book
   * @return a list of BookItems associated with the given ISBN
   * @throws SQLException if a database access error occurs
   */
  public List<BookItem> getAllByIsbn(String isbn) throws SQLException {
    List<BookItem> bookItems = new ArrayList<>();
    try (Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(GET_BOOK_ITEM_BY_ISBN_SQL)) {
      stmt.setString(1, isbn);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          bookItems.add(mapRowToBookItem(rs));
        }
      }
    } catch (SQLException e) {
      logger.error("Error retrieving book items for ISBN: {}", isbn, e);
      throw e;
    }
    return bookItems;
  }

  /**
   * Checks if a rack exists in the database.
   *
   * @param connection the database connection
   * @param rackNumber the rack number to check
   * @return true if the rack exists, false otherwise
   * @throws SQLException if a database access error occurs
   */
  private boolean rackExists(Connection connection, int rackNumber) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_RACK_SQL)) {
      preparedStatement.setInt(1, rackNumber);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        return resultSet.next();
      }
    }
  }
}
