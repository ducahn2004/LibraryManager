package org.group4.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.group4.module.notifications.Notification;

/**
 * Lớp NotificationDAO triển khai giao diện GenericDAO cho các thao tác CRUD (Tạo, Đọc, Cập nhật, Xóa)
 * trên thực thể Notification. Hiện tại, các phương thức trong lớp này chưa được triển khai.
 */
public class NotificationDAO implements GenericDAO<Notification, String> {

  // Hàm tạo mặc định
  NotificationDAO() {
  }

  /**
   * Thêm một thông báo mới vào cơ sở dữ liệu.
   *
   * @param entity Thực thể Notification cần thêm vào.
   * @return false vì phương thức chưa được triển khai.
   */
  @Override
  public boolean add(Notification entity) {
    return false;
  }

  /**
   * Cập nhật thông báo trong cơ sở dữ liệu.
   *
   * @param entity Thực thể Notification cần cập nhật.
   * @return false vì phương thức chưa được triển khai.
   */
  @Override
  public boolean update(Notification entity) {
    return false;
  }

  /**
   * Xóa một thông báo theo ID.
   *
   * @param s ID của thông báo cần xóa.
   * @return false vì phương thức chưa được triển khai.
   * @throws SQLException Nếu có lỗi truy vấn cơ sở dữ liệu.
   */
  @Override
  public boolean delete(String s) throws SQLException {
    return false;
  }

  /**
   * Lấy thông báo theo ID.
   *
   * @param s ID của thông báo cần tìm.
   * @return Optional.empty() vì phương thức chưa được triển khai.
   * @throws SQLException Nếu có lỗi truy vấn cơ sở dữ liệu.
   */
  @Override
  public Optional<Notification> getById(String s) throws SQLException {
    return Optional.empty();
  }

  /**
   * Lấy tất cả các thông báo từ cơ sở dữ liệu.
   *
   * @return Danh sách trống vì phương thức chưa được triển khai.
   */
  @Override
  public Collection<Notification> getAll() {
    return List.of();
  }
}
