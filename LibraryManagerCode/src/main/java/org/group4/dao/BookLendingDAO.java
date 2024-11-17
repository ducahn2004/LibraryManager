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

public class BookLendingDAO extends BaseDAO implements GenericDAO<BookLending, BookLending> {

  /** Logger cho lớp BookLendingDAO. */
  private static final Logger logger = LoggerFactory.getLogger(BookLendingDAO.class);

  /** Câu lệnh SQL để thêm một book lending mới vào cơ sở dữ liệu. */
  private static final String ADD_BOOK_LENDING_SQL =
      "INSERT INTO book_lendings (barcode, memberId, lendingDate, dueDate, returnDate) "
          + "VALUES (?, ?, ?, ?, ?)";

  /** Câu lệnh SQL để cập nhật một book lending hiện có trong cơ sở dữ liệu. */
  private static final String UPDATE_BOOK_LENDING_SQL =
      "UPDATE book_lendings SET lendingDate = ?, dueDate = ?, returnDate = ? "
          + "WHERE barcode = ? AND memberId = ?";

  /** Câu lệnh SQL để xóa một book lending khỏi cơ sở dữ liệu bằng barcode và member ID. */
  private static final String DELETE_BOOK_LENDING_SQL =
      "DELETE FROM book_lendings WHERE barcode = ? AND memberId = ?";

  /** Câu lệnh SQL để tìm một book lending bằng barcode và member ID. */
  private static final String GET_BOOK_LENDING_BY_ID_SQL =
      "SELECT * FROM book_lendings WHERE barcode = ? AND memberId = ?";

  /** Câu lệnh SQL để tìm tất cả các book lendings trong cơ sở dữ liệu. */
  private static final String GET_ALL_BOOK_LENDINGS_SQL = "SELECT * FROM book_lendings";

  @Override
  public boolean add(BookLending bookLending) {
    try (Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(ADD_BOOK_LENDING_SQL)) {
      setBookLendingData(stmt, bookLending, false);
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Lỗi khi thêm book lending: {}", bookLending, e);
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
      logger.error("Lỗi khi cập nhật book lending: {}", bookLending, e);
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
      logger.error("Lỗi khi xóa book lending: {}", bookLending, e);
      return false;
    }
  }

  @Override
  public Optional<BookLending> getById(BookLending bookLending) throws SQLException {
    try (Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(GET_BOOK_LENDING_BY_ID_SQL)) {
      stmt.setString(1, bookLending.getBookItem().getBarcode());
      stmt.setString(2, bookLending.getMember().getMemberId());
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return Optional.of(mapRowToBookLending(rs));
        }
      }
    } catch (SQLException e) {
      logger.error("Lỗi khi tìm book lending bằng ID: {}", bookLending, e);
    }
    return Optional.empty();
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
      logger.error("Lỗi khi tìm tất cả book lendings", e);
    }
    return bookLendings;
  }

  /**
   * Ánh xạ một hàng trong ResultSet thành đối tượng BookLending.
   *
   * @param resultSet ResultSet để ánh xạ
   * @return đối tượng BookLending được ánh xạ từ ResultSet
   * @throws SQLException nếu có lỗi SQL xảy ra trong quá trình ánh xạ
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
      throw new SQLException("Không tìm thấy BookItem hoặc Member với các ID đã cho.");
    }
  }

  /**
   * Thiết lập dữ liệu cho một đối tượng BookLending trong PreparedStatement.
   *
   * @param preparedStatement PreparedStatement để thiết lập dữ liệu
   * @param bookLending đối tượng BookLending để lấy dữ liệu
   * @param isUpdate true nếu PreparedStatement là cho cập nhật, false nếu cho thêm mới
   * @throws SQLException nếu có lỗi SQL xảy ra trong quá trình thiết lập dữ liệu
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

    // Thiết lập ngày trả nếu có, nếu không thì thiết lập NULL
    if (bookLending.getReturnDate().isPresent()) {
      preparedStatement.setDate(index++, Date.valueOf(bookLending.getReturnDate().get()));
    } else {
      preparedStatement.setNull(index++, Types.DATE);
    }

    // Thiết lập các trường còn lại cho cập nhật nếu cần thiết
    if (isUpdate) {
      preparedStatement.setString(index++, bookLending.getBookItem().getBarcode());
      preparedStatement.setString(index, bookLending.getMember().getMemberId());
    }
  }
}
