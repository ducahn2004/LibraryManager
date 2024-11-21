package org.group4.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.group4.module.books.BookItem;
import org.group4.module.transactions.BookLending;
import org.group4.module.users.Member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data Access Object (DAO) class for CRUD operations on the {@link BookLending} entity in the
 * database. This class provides methods to add, update, delete, and retrieve book lendings using
 * JDBC connection. Each method is executed within a try-with-resources statement to ensure
 * proper resource handling.
 */
public class BookLendingDAO extends BaseDAO implements GenericDAO<BookLending, BookLending> {

  /** Logger for BookLendingDAO class. */
  private static final Logger logger = LoggerFactory.getLogger(BookLendingDAO.class);

  /** Column names for the book_lendings table */
  private static final String COLUMN_BARCODE = "barcode";
  private static final String COLUMN_MEMBER_ID = "member_id";
  private static final String COLUMN_LENDING_DATE = "lending_date";
  private static final String COLUMN_DUE_DATE = "due_date";
  private static final String COLUMN_RETURN_DATE = "return_date";

  /** SQL statements for CRUD operations on the book_lendings table */
  private static final String ADD_BOOK_LENDING_SQL =
      "INSERT INTO book_lendings ("
          + COLUMN_BARCODE + ", "
          + COLUMN_MEMBER_ID + ", "
          + COLUMN_LENDING_DATE + ", "
          + COLUMN_DUE_DATE + ", "
          + COLUMN_RETURN_DATE + ") "
          + "VALUES (?, ?, ?, ?, ?)";

  private static final String UPDATE_BOOK_LENDING_SQL =
      "UPDATE book_lendings SET "
          + COLUMN_LENDING_DATE + " = ?, "
          + COLUMN_DUE_DATE + " = ?, "
          + COLUMN_RETURN_DATE + " = ? "
          + "WHERE " + COLUMN_BARCODE + " = ? AND " + COLUMN_MEMBER_ID + " = ?";

  private static final String DELETE_BOOK_LENDING_SQL =
      "DELETE FROM book_lendings WHERE " + COLUMN_BARCODE + " = ? AND " + COLUMN_MEMBER_ID + " = ?";

  private static final String GET_BOOK_LENDING_BY_BARCODE_SQL =
      "SELECT * FROM book_lendings WHERE " + COLUMN_BARCODE + " = ?";

  private static final String GET_BOOK_LENDING_BY_MEMBER_SQL =
      "SELECT * FROM book_lendings WHERE " + COLUMN_MEMBER_ID + " = ?";

  private static final String GET_BOOK_LENDING_BY_ID_SQL =
      "SELECT * FROM book_lendings "
          + "WHERE " + COLUMN_BARCODE + " = ? AND " + COLUMN_MEMBER_ID + " = ?";

  private static final String GET_ALL_BOOK_LENDINGS_SQL =
      "SELECT * FROM book_lendings";

  @Override
  public boolean add(BookLending bookLending) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_BOOK_LENDING_SQL)) {
      preparedStatement.setString(1, bookLending.getBookItem().getBarcode());
      preparedStatement.setString(2, bookLending.getMember().getMemberId());
      preparedStatement.setDate(3, Date.valueOf(bookLending.getLendingDate()));
      preparedStatement.setDate(4, Date.valueOf(bookLending.getDueDate()));
      if (bookLending.getReturnDate().isPresent()) {
        preparedStatement.setDate(5, Date.valueOf(bookLending.getReturnDate().get()));
      } else {
        preparedStatement.setNull(5, Types.DATE);
      }
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error adding book lending: {}", bookLending, e);
      return false;
    }
  }

  @Override
  public boolean update(BookLending bookLending) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOK_LENDING_SQL)) {
      preparedStatement.setDate(1, Date.valueOf(bookLending.getLendingDate()));
      preparedStatement.setDate(2, Date.valueOf(bookLending.getDueDate()));
      if (bookLending.getReturnDate().isPresent()) {
        preparedStatement.setDate(3, Date.valueOf(bookLending.getReturnDate().get()));
      } else {
        preparedStatement.setNull(3, Types.DATE);
      }
      preparedStatement.setString(4, bookLending.getBookItem().getBarcode());
      preparedStatement.setString(5, bookLending.getMember().getMemberId());
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error updating book lending: {}", bookLending, e);
      return false;
    }
  }

  @Override
  public boolean delete(BookLending bookLending) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOK_LENDING_SQL)) {
      preparedStatement.setString(1, bookLending.getBookItem().getBarcode());
      preparedStatement.setString(2, bookLending.getMember().getMemberId());
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error deleting book lending: {}", bookLending, e);
      return false;
    }
  }

  @Override
  public List<BookLending> getAll() {
    List<BookLending> bookLendings = new ArrayList<>();
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BOOK_LENDINGS_SQL);
        ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        bookLendings.add(mapRowToBookLending(resultSet));
      }
    } catch (SQLException e) {
      logger.error("Error retrieving all book lendings", e);
    }
    return bookLendings;
  }

  /**
   * Retrieves all book lendings from the database by the barcode of the book item.
   *
   * @param barcode The barcode of the book item to retrieve book lendings for
   * @return A list of all book lendings for the book item with the given barcode
   */
  public List<BookLending> getByBarcode(String barcode) {
    List<BookLending> bookLendings = new ArrayList<>();
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_BOOK_LENDING_BY_BARCODE_SQL)) {
      preparedStatement.setString(1, barcode);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          bookLendings.add(mapRowToBookLending(resultSet));
        }
      }
    } catch (SQLException e) {
      logger.error("Error finding book lendings by barcode: {}", barcode, e);
    }
    return bookLendings;
  }

  /**
   * Retrieves all book lendings from the database by the member ID of the member.
   *
   * @param memberId The ID of the member to retrieve book lendings for
   * @return A list of all book lendings for the member with the given ID
   */
  public List<BookLending> getByMemberId(String memberId) {
    List<BookLending> bookLendings = new ArrayList<>();
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_BOOK_LENDING_BY_MEMBER_SQL)) {
      preparedStatement.setString(1, memberId);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          bookLendings.add(mapRowToBookLending(resultSet));
        }
      }
    } catch (SQLException e) {
      logger.error("Error finding book lendings by member ID: {}", memberId, e);
    }
    return bookLendings;
  }

  /**
   * Retrieves a book lending from the database by the barcode of the book item and the member ID of
   * the member.
   *
   * @param barcode The barcode of the book item
   * @param memberId The ID of the member
   * @return An {@code Optional} containing the book lending if found, or empty otherwise
   */
  public Optional<BookLending> getById(String barcode, String memberId) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_BOOK_LENDING_BY_ID_SQL)) {
      preparedStatement.setString(1, barcode);
      preparedStatement.setString(2, memberId);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return Optional.ofNullable(mapRowToBookLending(resultSet));
        }
      }
    } catch (SQLException e) {
      logger.error("Error finding book lending by barcode and member ID: {} {}", barcode, memberId, e);
    }
    return Optional.empty();
  }

  /**
   * Maps a row in the ResultSet to a BookLending object.
   *
   * @param resultSet the ResultSet containing book lending data
   * @return a BookLending object with data from the ResultSet
   */
  private BookLending mapRowToBookLending(ResultSet resultSet) {
    try {
      // Retrieve BookItem and Member objects from the database
      BookItem bookItem = FactoryDAO.getBookItemDAO()
          .getById(resultSet.getString(COLUMN_BARCODE))
          .orElseThrow(() -> new SQLException("BookItem not found"));

      // Retrieve Member object from the database
      Member member = FactoryDAO.getMemberDAO()
          .getById(resultSet.getString(COLUMN_MEMBER_ID))
          .orElseThrow(() -> new SQLException("Member not found"));

      // Create BookLending object from ResultSet data
      LocalDate lendingDate = resultSet.getDate(COLUMN_LENDING_DATE).toLocalDate();
      LocalDate dueDate = resultSet.getDate(COLUMN_DUE_DATE).toLocalDate();
      LocalDate returnDate = resultSet.getDate(COLUMN_RETURN_DATE) != null
          ? resultSet.getDate(COLUMN_RETURN_DATE).toLocalDate()
          : null;
      return new BookLending(bookItem, member, lendingDate, dueDate, returnDate);
    } catch (SQLException e) {
      logger.error("Error mapping row to BookLending", e);
      return null;
    }
  }
}
