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
 * <p>It implements the {@link GenericDAO} interface to provide standard methods
 *    for persisting and retrieving {@link BookItem} data.</p>
 */
public class BookItemDAO extends BaseDAO implements GenericDAO<BookItem, String> {

  /** Logger for BookItemDAO class. */
  private static final Logger logger = LoggerFactory.getLogger(BookItemDAO.class);

  /** Column names for the book_items table */
  private static final String COLUMN_BARCODE = "barcode";
  private static final String COLUMN_ISBN = "ISBN";
  private static final String COLUMN_IS_REFERENCE_ONLY = "isReferenceOnly";
  private static final String COLUMN_BORROWED = "borrowed";
  private static final String COLUMN_DUE_DATE = "dueDate";
  private static final String COLUMN_PRICE = "price";
  private static final String COLUMN_FORMAT = "format";
  private static final String COLUMN_STATUS = "status";
  private static final String COLUMN_DATE_OF_PURCHASE = "dateOfPurchase";
  private static final String COLUMN_PUBLICATION_DATE = "publicationDate";
  private static final String COLUMN_RACK_NUMBER = "rackNumber";
  private static final String COLUMN_MAX_BARCODE = "max_barcode";

  /** SQL statements for CRUD operations on the book_items table */
  private static final String ADD_BOOK_ITEM_SQL =
      "INSERT INTO book_items ("
          + COLUMN_BARCODE + ", "
          + COLUMN_ISBN + ", "
          + COLUMN_IS_REFERENCE_ONLY + ", "
          + COLUMN_BORROWED + ", "
          + COLUMN_DUE_DATE + ", "
          + COLUMN_PRICE + ", "
          + COLUMN_FORMAT + ", "
          + COLUMN_STATUS + ", "
          + COLUMN_DATE_OF_PURCHASE + ", "
          + COLUMN_PUBLICATION_DATE + ", "
          + COLUMN_RACK_NUMBER + ") "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  private static final String UPDATE_BOOK_ITEM_SQL =
      "UPDATE book_items SET "
          + COLUMN_IS_REFERENCE_ONLY + " = ?, "
          + COLUMN_BORROWED + " = ?, "
          + COLUMN_DUE_DATE + " = ?, "
          + COLUMN_PRICE + " = ?, "
          + COLUMN_FORMAT + " = ?, "
          + COLUMN_STATUS + " = ?, "
          + COLUMN_DATE_OF_PURCHASE + " = ?, "
          + COLUMN_PUBLICATION_DATE
          + " = ?, " + COLUMN_RACK_NUMBER + " = ? "
          + "WHERE " + COLUMN_BARCODE + " = ?";

  private static final String DELETE_BOOK_ITEM_SQL =
      "DELETE FROM book_items WHERE " + COLUMN_BARCODE + " = ?";

  private static final String GET_ALL_BOOK_ITEMS_SQL = "SELECT * FROM book_items";

  private static final String GET_BOOK_ITEM_BY_BARCODE_SQL =
      "SELECT * FROM book_items WHERE " + COLUMN_BARCODE + " = ?";

  private static final String GET_BOOK_ITEM_BY_ISBN_SQL =
      "SELECT * FROM book_items WHERE " + COLUMN_ISBN + " = ?";

  private static final String GET_MAX_BARCODE_SQL =
      "SELECT MAX(" + COLUMN_BARCODE + ") AS " + COLUMN_MAX_BARCODE + " FROM book_items WHERE "
          + COLUMN_BARCODE + " LIKE ?";

  private static final String CHECK_RACK_SQL =
      "SELECT 1 FROM racks WHERE " + COLUMN_RACK_NUMBER + " = ?";

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
  public Optional<BookItem> getById(String barcode) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_BOOK_ITEM_BY_BARCODE_SQL)) {
      preparedStatement.setString(1, barcode);
      try (ResultSet rs = preparedStatement.executeQuery()) {
        if (rs.next()) {
          return Optional.ofNullable(mapRowToBookItem(rs));
        }
      }
    } catch (SQLException e) {
      logger.error("Error retrieving book item by barcode: {}", barcode, e);
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
        BookItem bookItem = mapRowToBookItem(resultSet); // Map row to BookItem object
        bookItems.add(bookItem);
      }
    } catch (SQLException e) {
      logger.error("Error retrieving all book items", e);
    }
    return bookItems;
  }

  /**
   * Maps a row in the ResultSet to a {@link BookItem} object.
   *
   * @param resultSet the ResultSet to map
   * @return a BookItem object
   */
  private BookItem mapRowToBookItem(ResultSet resultSet) {
    try {
      // Retrieve the associated Book object using ISBN from the database
      Book book = FactoryDAO.getBookDAO().getById(resultSet.getString("isbn"))
          .orElseThrow(() -> new SQLException("Book not found for ISBN"));

      // Retrieve the associated Rack object using rackNumber from the database
      Rack rack = FactoryDAO.getRackDAO().getById(resultSet.getInt("rackNumber"))
          .orElseThrow(() -> new SQLException("Rack not found for rack number"));

      // Construct and return a new BookItem directly with values from ResultSet and associated objects
      return new BookItem(
          book.getISBN(), book.getTitle(), book.getSubject(), book.getPublisher(),
          book.getLanguage(), book.getNumberOfPages(), book.getAuthors(),
          resultSet.getString(COLUMN_BARCODE),
          resultSet.getBoolean(COLUMN_IS_REFERENCE_ONLY),
          resultSet.getDate(COLUMN_BORROWED) != null ?
              resultSet.getDate(COLUMN_BORROWED).toLocalDate() : null,
          resultSet.getDate(COLUMN_DUE_DATE) != null ?
              resultSet.getDate(COLUMN_DUE_DATE).toLocalDate() : null,
          resultSet.getDouble(COLUMN_PRICE),
          BookFormat.valueOf(resultSet.getString(COLUMN_FORMAT)),
          BookStatus.valueOf(resultSet.getString(COLUMN_STATUS)),
          resultSet.getDate(COLUMN_DATE_OF_PURCHASE).toLocalDate(),
          resultSet.getDate(COLUMN_PUBLICATION_DATE).toLocalDate(),
          rack
      );
    } catch (SQLException e) {
      logger.error("Error mapping row to BookItem", e);
    }
    return null;
  }

  /**
   * Formats  barcode for a book item.
   *
   * @param isbn the ISBN of the book
   * @param barcode the barcode number
   * @return the formatted barcode as a String
   */
  private String formatBarcode(String isbn, int barcode) {
    return isbn + String.format("-%04d", barcode); // Format the barcode with leading zeros
  }

  /**
   * Generates a new barcode for a book item.
   *
   * @param connection the database connection
   * @param isbn the ISBN of the book
   * @return the generated barcode as a String
   */
  public String generateBarcode(Connection connection, String isbn)  {
    try (PreparedStatement preparedStatement = connection.prepareStatement(GET_MAX_BARCODE_SQL)) {
      preparedStatement.setString(1, isbn + "-%");
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          String maxBarcode = resultSet.getString(COLUMN_MAX_BARCODE);
          if (maxBarcode != null && maxBarcode.matches("^" + isbn + "-\\d{4}$")) {
            String[] parts = maxBarcode.split("-"); // Split the barcode by hyphen
            int nextBarcode = Integer.parseInt(parts[parts.length - 1]) + 1; // Increment the barcode
            return formatBarcode(isbn, nextBarcode);
          }
        }
      }
    } catch (SQLException e) {
      logger.error("Error generating barcode for ISBN: {}", isbn, e);
    }
    return formatBarcode(isbn, 1); // Return the first barcode if no existing barcodes found
  }

  /**
   * Retrieves all book items associated with a specific ISBN.
   *
   * @param isbn the ISBN of the book
   * @return a list of book items associated with the ISBN
   */
  public List<BookItem> getAllByIsbn(String isbn) {
    List<BookItem> bookItems = new ArrayList<>();
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_BOOK_ITEM_BY_ISBN_SQL)) {
      preparedStatement.setString(1, isbn);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          bookItems.add(mapRowToBookItem(resultSet));
        }
      }
    } catch (SQLException e) {
      logger.error("Error retrieving book items by ISBN: {}", isbn, e);
    }
    return bookItems;
  }

  /**
   * Checks if a rack exists in the database.
   *
   * @param connection the database connection
   * @param rackNumber the rack number to check
   * @return true if the rack exists, false otherwise
   */
  private boolean rackExists(Connection connection, int rackNumber)  {
    try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_RACK_SQL)) {
      preparedStatement.setInt(1, rackNumber);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        return resultSet.next();
      }
    } catch (SQLException e) {
      logger.error("Error checking rack number: {}", rackNumber, e);
      return false;
    }
  }
}
