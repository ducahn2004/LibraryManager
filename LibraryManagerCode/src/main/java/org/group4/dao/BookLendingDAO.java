package org.group4.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
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

  /** SQL query to add a new book lending to the database. */
  private static final String ADD_BOOK_LENDING_SQL =
      "INSERT INTO book_lendings (barcode, memberId, lendingDate, dueDate, returnDate) "
          + "VALUES (?, ?, ?, ?, ?)";

  /** SQL query to update an existing book lending in the database. */
  private static final String UPDATE_BOOK_LENDING_SQL =
      "UPDATE book_lendings SET lendingDate = ?, dueDate = ?, returnDate = ? "
          + "WHERE barcode = ? AND memberId = ?";

  /** SQL query to delete a book lending from the database by barcode and member ID. */
  private static final String DELETE_BOOK_LENDING_SQL =
      "DELETE FROM book_lendings WHERE barcode = ? AND memberId = ?";

  /** SQL query to find a book lending by barcode and member ID. */
  private static final String GET_BOOK_LENDING_BY_BARCODE_SQL =
      "SELECT * FROM book_lendings WHERE barcode = ?";

  private static final String GET_BOOK_LENDING_BY_MEMBER_SQL =
      "SELECT * FROM book_lendings WHERE memberId = ?";

  /** SQL query to find all book lendings in the database. */
  private static final String GET_ALL_BOOK_LENDINGS_SQL = "SELECT * FROM book_lendings";

  @Override
  public boolean add(BookLending bookLending) {
    try (Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(ADD_BOOK_LENDING_SQL)) {
      setBookLendingData(stmt, bookLending, false);
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error adding book lending: {}", bookLending, e);
      return false;
    }
  }

  @Override
  public boolean update(BookLending bookLending) {
    try (Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(UPDATE_BOOK_LENDING_SQL)) {
      setBookLendingData(stmt, bookLending, true);
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error updating book lending: {}", bookLending, e);
      return false;
    }
  }

  @Override
  public boolean delete(BookLending bookLending) {
    try (Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(DELETE_BOOK_LENDING_SQL)) {
      stmt.setString(1, bookLending.getBookItem().getBarcode());
      stmt.setString(2, bookLending.getMember().getMemberId());
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error deleting book lending: {}", bookLending, e);
      return false;
    }
  }

  /**
   * Retrieves a list of book lendings from the database by barcode.
   *
   * @param barcode the barcode of the book item to find lendings for
   * @return a list of book lendings with the given barcode
   */
  public List<BookLending> getByBarcode(String barcode) {
    List<BookLending> bookLendings = new ArrayList<>();
    try (Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(GET_BOOK_LENDING_BY_BARCODE_SQL)) {
      stmt.setString(1, barcode);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          bookLendings.add(mapRowToBookLending(rs));
        }
      }
    } catch (SQLException e) {
      logger.error("Error finding book lendings by barcode: {}", barcode, e);
    }
    return bookLendings;
  }

  /**
   * Retrieves a list of book lendings from the database by member ID.
   *
   * @param memberId the ID of the member to find lendings for
   * @return a list of book lendings with the given member ID
   */
  public List<BookLending> getByMember(String memberId) {
    List<BookLending> bookLendings = new ArrayList<>();
    try (Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(GET_BOOK_LENDING_BY_MEMBER_SQL)) {
      stmt.setString(1, memberId);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          bookLendings.add(mapRowToBookLending(rs));
        }
      }
    } catch (SQLException e) {
      logger.error("Error finding book lendings by member ID: {}", memberId, e);
    }
    return bookLendings;
  }

  @Override
  public Collection<BookLending> getAll() {
    List<BookLending> bookLendings = new ArrayList<>();
    try (Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(GET_ALL_BOOK_LENDINGS_SQL);
        ResultSet rs = stmt.executeQuery()) {
      while (rs.next()) {
        bookLendings.add(mapRowToBookLending(rs));
      }
    } catch (SQLException e) {
      logger.error("Error finding all book lendings", e);
    }
    return bookLendings;
  }

  /**
   * Maps a row in a ResultSet to a BookLending object.
   *
   * @param resultSet the ResultSet to map
   * @return the BookLending object mapped from the ResultSet
   * @throws SQLException if an SQL error occurs during mapping
   */
  private BookLending mapRowToBookLending(ResultSet resultSet) throws SQLException {
    Optional<BookItem> bookItemOpt =
        FactoryDAO.getBookItemDAO().getById(resultSet.getString("barcode"));
    Optional<Member> memberOpt =
        FactoryDAO.getMemberDAO().getById(resultSet.getString("memberId"));

    if (bookItemOpt.isPresent() && memberOpt.isPresent()) {
        BookItem bookItem = bookItemOpt.get();
        Member member = memberOpt.get();
        LocalDate lendingDate = resultSet.getDate("lendingDate").toLocalDate();
        LocalDate dueDate = resultSet.getDate("dueDate").toLocalDate();
        LocalDate returnDate = resultSet.getDate("returnDate") != null ? resultSet.getDate("returnDate").toLocalDate() : null;
        return new BookLending(bookItem, member, lendingDate, dueDate, returnDate);
    } else {
        throw new SQLException("BookItem or Member not found for the given IDs.");
    }
  }

  /**
   * Sets the data for a BookLending object in a PreparedStatement.
   *
   * @param preparedStatement the PreparedStatement to set data in
   * @param bookLending the BookLending object to set data from
   * @param isUpdate true if the PreparedStatement is for an update, false if for an insert
   * @throws SQLException if an SQL error occurs during setting data
   */
  private void setBookLendingData(PreparedStatement preparedStatement, BookLending bookLending,
      boolean isUpdate) throws SQLException {

    int index = 1;

    if (!isUpdate) {
      preparedStatement.setString(index++, bookLending.getBookItem().getBarcode());
      preparedStatement.setString(index++, bookLending.getMember().getMemberId());
    }
    preparedStatement.setDate(index++, Date.valueOf(bookLending.getLendingDate()));
    preparedStatement.setDate(index++, Date.valueOf(bookLending.getDueDate()));

    // Set return date if present, else set to NULL
    if (bookLending.getReturnDate().isPresent()) {
      preparedStatement.setDate(index++, Date.valueOf(bookLending.getReturnDate().get()));
    } else {
      preparedStatement.setNull(index++, Types.DATE);
    }

    // Set remaining fields for update if necessary
    if (isUpdate) {
      preparedStatement.setString(index++, bookLending.getBookItem().getBarcode());
      preparedStatement.setString(index, bookLending.getMember().getMemberId());
    }
  }
}
