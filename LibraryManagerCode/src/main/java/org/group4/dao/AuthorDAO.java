package org.group4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.group4.module.books.Author;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp DAO (Data Access Object) phục vụ cho việc thao tác dữ liệu của thực thể {@link Author}.
 * Lớp này cung cấp các phương thức thêm, sửa, xóa, và truy vấn thông tin từ bảng "authors" trong cơ sở dữ liệu.
 */
public class AuthorDAO extends BaseDAO implements GenericDAO<Author, String> {

  /** Logger cho lớp AuthorDAO. */
  private static final Logger logger = LoggerFactory.getLogger(AuthorDAO.class);

  /** Câu lệnh SQL để thêm tác giả mới vào cơ sở dữ liệu. */
  private static final String ADD_AUTHOR_SQL = "INSERT INTO authors (author_id, name) VALUES (?, ?)";

  /** Câu lệnh SQL để cập nhật thông tin tác giả trong cơ sở dữ liệu. */
  private static final String UPDATE_AUTHOR_SQL = "UPDATE authors SET name = ? WHERE author_id = ?";

  /** Câu lệnh SQL để xóa tác giả khỏi cơ sở dữ liệu dựa trên ID. */
  private static final String DELETE_AUTHOR_SQL = "DELETE FROM authors WHERE author_id = ?";

  /** Câu lệnh SQL để tìm tác giả theo ID. */
  private static final String GET_AUTHOR_BY_ID_SQL = "SELECT * FROM authors WHERE author_id = ?";

  /** Câu lệnh SQL để lấy tất cả các tác giả từ cơ sở dữ liệu. */
  private static final String GET_ALL_AUTHORS_SQL = "SELECT * FROM authors";

  /** Câu lệnh SQL để tìm ID lớn nhất của tác giả trong cơ sở dữ liệu. */
  private static final String GET_MAX_AUTHOR_ID_SQL = "SELECT MAX(author_id) AS max_id FROM authors";

  /**
   * Thêm một tác giả mới vào cơ sở dữ liệu.
   *
   * @param author đối tượng Author cần thêm.
   * @return {@code true} nếu thêm thành công, {@code false} nếu thất bại.
   */
  @Override
  public boolean add(Author author) {
    try (Connection connection = getConnection()) {
      // Tạo ID mới cho tác giả
      String newAuthorId = generateAuthorId(connection);
      author.setAuthorId(newAuthorId);

      // Chuẩn bị và thực thi câu lệnh SQL INSERT
      try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_AUTHOR_SQL)) {
        preparedStatement.setString(1, author.getAuthorId());
        preparedStatement.setString(2, author.getName());
        return preparedStatement.executeUpdate() > 0;
      }
    } catch (SQLException e) {
      logger.error("Lỗi khi thêm tác giả: {}", author.getAuthorId(), e);
    }
    return false;
  }

  /**
   * Cập nhật thông tin tác giả trong cơ sở dữ liệu.
   *
   * @param author đối tượng Author chứa thông tin cần cập nhật.
   * @return {@code true} nếu cập nhật thành công, {@code false} nếu thất bại.
   */
  @Override
  public boolean update(Author author) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AUTHOR_SQL)) {
      preparedStatement.setString(1, author.getName());
      preparedStatement.setString(2, author.getAuthorId());
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Lỗi khi cập nhật tác giả với ID {}: {}", author.getAuthorId(), e.getMessage());
    }
    return false;
  }

  /**
   * Xóa một tác giả khỏi cơ sở dữ liệu dựa trên ID.
   *
   * @param authorId ID của tác giả cần xóa.
   * @return {@code true} nếu xóa thành công, {@code false} nếu thất bại.
   */
  @Override
  public boolean delete(String authorId) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_AUTHOR_SQL)) {
      preparedStatement.setString(1, authorId);
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Lỗi khi xóa tác giả với ID {}: {}", authorId, e.getMessage());
    }
    return false;
  }

  /**
   * Tìm một tác giả theo ID.
   *
   * @param authorId ID của tác giả cần tìm.
   * @return {@code Optional<Author>} chứa đối tượng Author nếu tìm thấy, hoặc rỗng nếu không có.
   */
  @Override
  public Optional<Author> getById(String authorId) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_AUTHOR_BY_ID_SQL)) {
      preparedStatement.setString(1, authorId);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return Optional.of(mapRowToAuthor(resultSet));
        }
      }
    } catch (SQLException e) {
      logger.error("Lỗi khi tìm tác giả theo ID {}: {}", authorId, e.getMessage());
    }
    return Optional.empty();
  }

  /**
   * Lấy tất cả các tác giả từ cơ sở dữ liệu.
   *
   * @return {@code Set<Author>} tập hợp các đối tượng Author.
   */
  @Override
  public Set<Author> getAll() {
    Set<Author> authors = new HashSet<>();
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_AUTHORS_SQL);
        ResultSet resultSet = preparedStatement.executeQuery()) {

      while (resultSet.next()) {
        authors.add(mapRowToAuthor(resultSet));
      }
    } catch (SQLException e) {
      logger.error("Lỗi khi lấy danh sách tất cả tác giả: {}", e.getMessage());
    }
    return authors;
  }

  /**
   * Ánh xạ một hàng từ {@link ResultSet} sang đối tượng {@link Author}.
   *
   * @param resultSet kết quả truy vấn từ cơ sở dữ liệu.
   * @return đối tượng Author chứa dữ liệu ánh xạ từ ResultSet.
   * @throws SQLException nếu có lỗi truy vấn.
   */
  private Author mapRowToAuthor(ResultSet resultSet) throws SQLException {
    String authorId = formatAuthorId(resultSet.getInt("author_id"));
    String name = resultSet.getString("name");
    return new Author(authorId, name);
  }

  /**
   * Định dạng ID tác giả theo tiền tố "AUTHOR-" và đệm số 0 để đảm bảo nhất quán.
   *
   * @param id ID dạng số từ cơ sở dữ liệu.
   * @return chuỗi ID đã định dạng với tiền tố.
   */
  private String formatAuthorId(int id) {
    return "AUTHOR-" + String.format("%03d", id);
  }

  /**
   * Tạo ID mới cho tác giả bằng cách tìm ID lớn nhất hiện tại và tăng lên 1.
   *
   * @param connection kết nối cơ sở dữ liệu.
   * @return ID mới dạng "AUTHOR-XXX".
   * @throws SQLException nếu có lỗi truy vấn.
   */
  private String generateAuthorId(Connection connection) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(GET_MAX_AUTHOR_ID_SQL);
        ResultSet resultSet = statement.executeQuery()) {
      if (resultSet.next()) {
        String maxId = resultSet.getString("max_id");
        if (maxId != null) {
          // Lấy phần số, tăng lên 1 và định dạng lại ID
          int nextId = Integer.parseInt(maxId.replace("AUTHOR-", "")) + 1;
          return formatAuthorId(nextId);
        }
      }
    }
    return formatAuthorId(1); // ID mặc định nếu không có bản ghi nào
  }

}
