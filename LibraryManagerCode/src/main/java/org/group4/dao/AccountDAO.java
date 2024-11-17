package org.group4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.group4.module.users.Account;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp {@code AccountDAO} chịu trách nhiệm xử lý các thao tác cơ sở dữ liệu liên quan đến
 * đối tượng {@code Account}, như xác minh thông tin đăng nhập và cập nhật tài khoản.
 */
public class AccountDAO extends BaseDAO {

  /** Logger cho lớp AccountDAO. */
  private static final Logger logger = LoggerFactory.getLogger(AccountDAO.class);

  /** Câu lệnh SQL để lấy thông tin tài khoản dựa vào ID từ cơ sở dữ liệu. */
  private static final String GET_ACCOUNT_BY_ID_SQL = "SELECT * FROM account WHERE id = ?";

  /** Câu lệnh SQL để cập nhật mật khẩu của tài khoản trong cơ sở dữ liệu. */
  private static final String UPDATE_ACCOUNT_SQL = "UPDATE account SET password = ? WHERE id = ?";

  /** Câu lệnh SQL để lấy mật khẩu của tài khoản từ cơ sở dữ liệu. */
  private static final String GET_ACCOUNT_PASSWORD_SQL = "SELECT password FROM account WHERE id = ?";

  /**
   * Lấy tài khoản dựa vào ID từ cơ sở dữ liệu.
   *
   * @param id ID của tài khoản
   * @return {@code Optional} chứa tài khoản nếu tìm thấy,
   *         hoặc một {@code Optional} rỗng nếu không tìm thấy
   */
  public Optional<Account> getById(String id) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ACCOUNT_BY_ID_SQL)) {
      preparedStatement.setString(1, id);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return Optional.of(new Account(resultSet.getString("id"),
              resultSet.getString("password")));
        }
      }
    } catch (SQLException e) {
      logger.error("Lỗi khi lấy tài khoản bằng ID: {}", id, e);
    }
    return Optional.empty();
  }

  /**
   * Cập nhật mật khẩu của tài khoản trong cơ sở dữ liệu.
   *
   * @param account đối tượng tài khoản với thông tin đã được cập nhật
   * @return {@code true} nếu cập nhật thành công, {@code false} nếu thất bại
   */
  public boolean update(Account account) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT_SQL)) {
      preparedStatement.setString(1, account.getPassword());
      preparedStatement.setString(2, account.getId());
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Lỗi khi cập nhật tài khoản: {}", account.getId(), e);
      return false;
    }
  }

  /**
   * Lấy mật khẩu đã được mã hóa lưu trữ trong cơ sở dữ liệu cho một ID tài khoản cụ thể.
   *
   * @param id ID của tài khoản
   * @return {@code Optional} chứa mật khẩu mã hóa nếu tồn tại,
   *         hoặc một {@code Optional} rỗng nếu không tìm thấy
   */
  public Optional<String> getStoredHashedPassword(String id) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ACCOUNT_PASSWORD_SQL)) {
      preparedStatement.setString(1, id);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return Optional.of(resultSet.getString("password"));
        }
      }
    } catch (SQLException e) {
      logger.error("Lỗi khi lấy mật khẩu mã hóa cho tài khoản ID: {}", id, e);
    }
    return Optional.empty();
  }

  /**
   * Xác minh thông tin đăng nhập bằng cách kiểm tra mật khẩu đã mã hóa.
   *
   * @param id       ID của tài khoản
   * @param password mật khẩu dạng văn bản thường để xác minh
   * @return {@code true} nếu mật khẩu khớp với mật khẩu đã lưu trữ,
   *         {@code false} nếu không khớp
   */
  public boolean verifyCredentials(String id, String password) {
    // Lấy mật khẩu mã hóa đã lưu trữ từ cơ sở dữ liệu
    Optional<String> storedHashedPassword = getStoredHashedPassword(id);

    // Kiểm tra mật khẩu
    return storedHashedPassword.isPresent() && BCrypt.checkpw(password, storedHashedPassword.get());
  }
}
