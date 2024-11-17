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

  private static final String GET_MAX_BARCODE_SQL =
      "SELECT MAX(barcode) AS max_barcode FROM book_items";

  @Override
  public boolean add(BookItem bookItem) {
    try (Connection connection = getConnection()) {
      // Generate new barcode
      String newBarcode = generateBarcode(connection, bookItem.getISBN());
      bookItem.setBarcode(newBarcode);

      // Prepare and execute the SQL INSERT statement
      try (PreparedStatement stmt = connection.prepareStatement(ADD_BOOK_ITEM_SQL)) {
        setBookItemData(stmt, bookItem, false);
        return stmt.executeUpdate() > 0;
      }
    } catch (SQLException e) {
      logger.error("Error adding book item: {}", bookItem, e);
      return false;
    }
  }

  @Override
  public boolean update(BookItem bookItem) {
    try (Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(UPDATE_BOOK_ITEM_SQL)) {

      setBookItemData(stmt, bookItem, true);
      stmt.setString(10, bookItem.getBarcode());  // Set barcode for WHERE clause
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error updating book item: {}", bookItem, e);
      return false;
    }
  }

  @Override
  public boolean delete(String barcode) {
    try (Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(DELETE_BOOK_ITEM_SQL)) {

      stmt.setString(1, barcode);
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error deleting book item with barcode: {}", barcode, e);
      return false;
    }
  }

  @Override
  public Optional<BookItem> getById(String barcode) throws SQLException {
    try (Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(GET_ALL_BOOK_ITEMS_SQL)) {

      stmt.setString(1, barcode);
      try (ResultSet rs = stmt.executeQuery()) {
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
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(GET_ALL_BOOK_ITEMS_SQL);
        ResultSet resultSet = stmt.executeQuery()) {

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
    Book book = new BookDAO().getById(resultSet.getString("isbn"))
        .orElseThrow(() -> new SQLException("Book not found for ISBN"));

    // Retrieve the associated Rack object using rackNumber from the database
    Rack rack = new RackDAO().getById(resultSet.getInt("rackNumber"))
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
        resultSet.getDate("borrowed").toLocalDate(),
        resultSet.getDate("dueDate").toLocalDate(),
        resultSet.getDouble("price"),
        BookFormat.valueOf(resultSet.getString("format")),
        BookStatus.valueOf(resultSet.getString("status")),
        resultSet.getDate("dateOfPurchase").toLocalDate(),
        resultSet.getDate("publicationDate").toLocalDate(),
        rack
    );
  }

  /**
   * Sets the data for a BookItem into a PreparedStatement.
   *
   * @param preparedStatement the PreparedStatement to set data into.
   * @param bookItem the BookItem object containing data to be set.
   * @throws SQLException if a database access error occurs.
   */
  private void setBookItemData(PreparedStatement preparedStatement, BookItem bookItem,
      boolean isUpdate) throws SQLException {

    int index = 1;

    // Set data for ADD or UPDATE
    if (!isUpdate) {
      preparedStatement.setString(index++, bookItem.getBarcode());
    }

    preparedStatement.setString(index++, bookItem.getISBN());
    preparedStatement.setBoolean(index++, bookItem.getIsReferenceOnly());
    preparedStatement.setDate(index++, Date.valueOf(bookItem.getBorrowed()));
    preparedStatement.setDate(index++, Date.valueOf(bookItem.getDueDate()));
    preparedStatement.setDouble(index++, bookItem.getPrice());
    preparedStatement.setString(index++, bookItem.getFormat().name());
    preparedStatement.setString(index++, bookItem.getStatus().name());
    preparedStatement.setDate(index++, Date.valueOf(bookItem.getDateOfPurchase()));
    preparedStatement.setDate(index++, Date.valueOf(bookItem.getPublicationDate()));
    preparedStatement.setInt(index, bookItem.getPlacedAt().getNumberRack());

    if (isUpdate) {
      preparedStatement.setString(index, bookItem.getBarcode());
    }
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
   * @param connection the database connection to use
   * @return the new barcode as a String
   * @throws SQLException if a database access error occurs
   */
  public String generateBarcode(Connection connection, String isbn) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement(GET_MAX_BARCODE_SQL)) {
      preparedStatement.setString(1, isbn + "-%");
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          String maxBarcode = resultSet.getString("max_barcode");
          if (maxBarcode != null) {
            String[] parts = maxBarcode.split("-");
            int nextBarcode = Integer.parseInt(parts[1]) + 1;
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
    String query = "SELECT * FROM book_items WHERE ISBN = ?";
    try (Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(query)) {
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

}
