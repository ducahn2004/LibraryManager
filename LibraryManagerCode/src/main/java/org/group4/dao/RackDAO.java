package org.group4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.group4.module.books.Rack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RackDAO triển khai giao diện GenericDAO để thực hiện các thao tác CRUD trên bảng 'racks'.
 * Lớp này chịu trách nhiệm quản lý việc thêm, cập nhật, xóa và lấy thông tin các giá sách (Rack).
 */
public class RackDAO extends BaseDAO implements GenericDAO<Rack, Integer> {

  private static final Logger logger = LoggerFactory.getLogger(RackDAO.class);

  // Câu lệnh SQL để thêm mới một giá sách vào cơ sở dữ liệu.
  private static final String ADD_RACK_SQL = "INSERT INTO racks (numberRack, locationIdentifier) VALUES (?, ?)";

  // Câu lệnh SQL để cập nhật thông tin của một giá sách.
  private static final String UPDATE_RACK_SQL = "UPDATE racks SET locationIdentifier = ? WHERE numberRack "
      + "= ?";

  // Câu lệnh SQL để xóa một giá sách khỏi cơ sở dữ liệu.
  private static final String DELETE_RACK_SQL = "DELETE FROM racks WHERE numberRack = ?";

  // Câu lệnh SQL để lấy tất cả các giá sách trong cơ sở dữ liệu.
  private static final String GET_ALL_RACKS_SQL = "SELECT * FROM racks";

  // Câu lệnh SQL để tìm giá sách theo mã số (numberRack).
  private static final String GET_RACK_BY_ID_SQL = "SELECT * FROM racks WHERE numberRack = ?";

  /**
   * Thêm một giá sách mới vào cơ sở dữ liệu.
   *
   * @param rack Đối tượng Rack chứa thông tin cần thêm vào.
   * @return true nếu việc thêm mới thành công, false nếu có lỗi.
   */
  @Override
  public boolean add(Rack rack) {
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(ADD_RACK_SQL)) {
      stmt.setInt(1, rack.getNumberRack());
      stmt.setString(2, rack.getLocationIdentifier());
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error adding rack: {}", rack, e);
      return false;
    }
  }

  /**
   * Cập nhật thông tin của một giá sách.
   *
   * @param rack Đối tượng Rack chứa thông tin cần cập nhật.
   * @return true nếu việc cập nhật thành công, false nếu có lỗi.
   */
  @Override
  public boolean update(Rack rack) {
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(UPDATE_RACK_SQL)) {
      stmt.setString(1, rack.getLocationIdentifier());
      stmt.setInt(2, rack.getNumberRack());
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error updating rack: {}", rack, e);
      return false;
    }
  }

  /**
   * Xóa một giá sách theo mã số numberRack.
   *
   * @param numberRack Mã số của giá sách cần xóa.
   * @return true nếu việc xóa thành công, false nếu có lỗi.
   */
  @Override
  public boolean delete(Integer numberRack) {
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(DELETE_RACK_SQL)) {
      stmt.setInt(1, numberRack);
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error deleting rack: {}", numberRack, e);
      return false;
    }
  }

  /**
   * Lấy thông tin của một giá sách theo mã số numberRack.
   *
   * @param numberRack Mã số của giá sách cần tìm.
   * @return Một Optional chứa đối tượng Rack nếu tìm thấy, hoặc Optional.empty nếu không tìm thấy.
   * @throws SQLException Nếu có lỗi truy vấn cơ sở dữ liệu.
   */
  @Override
  public Optional<Rack> getById(Integer numberRack) throws SQLException {
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(GET_RACK_BY_ID_SQL)) {
      stmt.setInt(1, numberRack);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return Optional.of(mapRowToRack(rs));
        }
      }
    }
    return Optional.empty();
  }

  /**
   * Lấy tất cả các giá sách từ cơ sở dữ liệu.
   *
   * @return Danh sách các đối tượng Rack.
   */
  @Override
  public List<Rack> getAll() {
    List<Rack> racks = new ArrayList<>();
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(GET_ALL_RACKS_SQL);
        ResultSet rs = stmt.executeQuery()) {
      while (rs.next()) {
        racks.add(mapRowToRack(rs));

      }
    } catch (SQLException e) {
      logger.error("Error getting all racks", e);
    }
    return racks;
  }

  /**
   * Chuyển đổi một dòng kết quả từ ResultSet thành đối tượng Rack.
   *
   * @param rs ResultSet chứa dữ liệu của giá sách.
   * @return Một đối tượng Rack được ánh xạ từ ResultSet.
   * @throws SQLException Nếu có lỗi truy vấn cơ sở dữ liệu.
   */
  private Rack mapRowToRack(ResultSet rs) throws SQLException {
    int numberRack = rs.getInt("numberRack");
    String locationIdentifier = rs.getString("locationIdentifier");
    return new Rack(numberRack, locationIdentifier);
  }
}
