package org.group4.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lớp trừu tượng BaseDAO cung cấp cấu hình tập trung để quản lý kết nối cơ sở dữ liệu
 * bằng cách sử dụng HikariCP - một công cụ quản lý kết nối JDBC hiệu suất cao.
 * Lớp này khởi tạo một connection pool và cung cấp phương thức để lấy kết nối cơ sở dữ liệu
 * phục vụ cho các lớp DAO.
 */
public abstract class BaseDAO {

  /** Logger để ghi lại thông tin và lỗi. */
  private static final Logger logger = Logger.getLogger(BaseDAO.class.getName());

  /**
   * HikariDataSource là đối tượng quản lý connection pool, được cấu hình để
   * kết nối tới cơ sở dữ liệu MySQL với các thiết lập tối ưu cho việc sử dụng connection pool.
   */
  private static final HikariDataSource dataSource;

  // Khối static để cấu hình và khởi tạo HikariCP
  static {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:mysql://192.168.31.193:3307/library_database"); // URL kết nối cơ sở dữ liệu
    config.setUsername("Satoh"); // Tên đăng nhập cơ sở dữ liệu
    config.setPassword("Tranducanh@"); // Mật khẩu cơ sở dữ liệu
    config.setMaximumPoolSize(10); // Số lượng kết nối tối đa trong connection pool

    dataSource = new HikariDataSource(config);
    logger.log(Level.INFO, "Khởi tạo connection pool thành công");
  }

  /**
   * Lấy một kết nối cơ sở dữ liệu từ connection pool HikariCP.
   * Kết nối này cần được đóng sau khi sử dụng để trả lại cho pool.
   *
   * @return một đối tượng Connection để tương tác với cơ sở dữ liệu.
   * @throws SQLException nếu có lỗi truy cập cơ sở dữ liệu.
   */
  protected Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

  /**
   * Đóng HikariCP data source và giải phóng tất cả tài nguyên được sử dụng bởi connection pool.
   * Phương thức này nên được gọi khi ứng dụng dừng hoạt động.
   */
  public static void closeDataSource() {
    if (dataSource != null && !dataSource.isClosed()) {
      dataSource.close();
      logger.log(Level.INFO, "Đã đóng connection pool");
    }
  }
}
