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
          + "status, dateOfPurchase, publicationDate, rackNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  /** SQL query to update an existing book item in the database. */
  private static final String UPDATE_BOOK_ITEM_SQL =
      "UPDATE book_items SET isReferenceOnly = ?, borrowed = ?, dueDate = ?, price = ?, format = ?, status = ?, "
          + "dateOfPurchase = ?, publicationDate = ?, rackNumber = ? WHERE barcode = ?";

  /** SQL query to delete a book item from the database by barcode. */
  private static final String DELETE_BOOK_ITEM_SQL = "DELETE FROM book_items WHERE barcode = ?";

  /** SQL query to find a book item by barcode. */
  private static final String GET_ALL_BOOK_ITEMS_SQL = "SELECT * FROM book_items";

  /**
   * Adds a new {@link BookItem} to the database.
   *
   * @param bookItem the BookItem object to be added.
   * @return true if the book item was successfully added, false otherwise.
   */
  @Override
  public boolean add(BookItem bookItem) {
    try (Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(ADD_BOOK_ITEM_SQL)) {

      setBookItemData(stmt, bookItem, false);
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error adding book item: {}", bookItem, e);
      return false;
    }
  }

  /**
   * Updates an existing {@link BookItem} in the database.
   *
   * @param bookItem the BookItem object to be updated.
   * @return true if the book item was successfully updated, false otherwise.
   */
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

  /**
   * Deletes a {@link BookItem} from the database by barcode.
   *
   * @param bookItem the BookItem to be deleted.
   * @return true if the book item was successfully deleted, false otherwise.
   */
  @Override
  public boolean delete(BookItem bookItem) {
    try (Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(DELETE_BOOK_ITEM_SQL)) {

      stmt.setString(1, bookItem.getBarcode());
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error deleting book item: {}", bookItem, e);
      return false;
    }
  }

  /**
   * Retrieves a {@link BookItem} from the database by barcode.
   *
   * @param barcode the barcode of the book item to retrieve.
   * @return an Optional containing the found BookItem, or empty if not found.
   * @throws SQLException if an SQL error occurs during retrieval.
   */
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

  /**
   * Retrieves all {@link BookItem}s from the database.
   *
   * @return a list of all BookItems.
   */
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
    // Common fields for both add and update
    preparedStatement.setString(1, bookItem.getBarcode());
    preparedStatement.setString(2, bookItem.getISBN());
    preparedStatement.setBoolean(3, bookItem.getIsReferenceOnly());
    preparedStatement.setDate(4, Date.valueOf(bookItem.getBorrowed()));
    preparedStatement.setDate(5, Date.valueOf(bookItem.getDueDate()));
    preparedStatement.setDouble(6, bookItem.getPrice());
    preparedStatement.setString(7, bookItem.getFormat().name());
    preparedStatement.setString(8, bookItem.getStatus().name());
    preparedStatement.setDate(9, Date.valueOf(bookItem.getDateOfPurchase()));
    preparedStatement.setDate(10, Date.valueOf(bookItem.getPublicationDate()));
    preparedStatement.setInt(11, bookItem.getPlacedAt().getNumberRack());

    // Set barcode for WHERE clause in UPDATE
    if (isUpdate) {
      preparedStatement.setString(12, bookItem.getBarcode());
    }
  }

}
