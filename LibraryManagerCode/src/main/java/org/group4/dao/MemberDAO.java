package org.group4.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.group4.module.users.Member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data Access Object cho các thao tác CRUD trên các thực thể {@link Member} trong cơ sở dữ liệu.
 * Thực hiện {@link GenericDAO} để chuẩn hóa các thao tác cơ sở dữ liệu cho Member.
 */
public class MemberDAO extends BaseDAO implements GenericDAO<Member, String> {

  /** Logger cho MemberDAO. */
  private static final Logger logger = LoggerFactory.getLogger(MemberDAO.class);

  /** Câu lệnh SQL để thêm một thành viên mới vào cơ sở dữ liệu. */
  private static final String ADD_MEMBER_SQL =
      "INSERT INTO members (memberId, name, dateOfBirth, email, phoneNumber, "
          + "total_book_checked_out) VALUES (?, ?, ?, ?, ?, ?)";

  /** Câu lệnh SQL để cập nhật thông tin thành viên hiện có trong cơ sở dữ liệu. */
  private static final String UPDATE_MEMBER_SQL =
      "UPDATE members SET name = ?, dateOfBirth = ?, email = ?, phoneNumber = ?, "
          + "total_book_checked_out = ?  WHERE memberID = ?";

  /** Câu lệnh SQL để xóa một thành viên khỏi cơ sở dữ liệu dựa vào ID. */
  private static final String DELETE_MEMBER_SQL = "DELETE FROM members WHERE memberID = ?";

  /** Câu lệnh SQL để tìm một thành viên theo ID. */
  private static final String FIND_MEMBER_BY_ID_SQL = "SELECT * FROM members WHERE memberID = ?";

  /** Câu lệnh SQL để tìm tất cả các thành viên trong cơ sở dữ liệu. */
  private static final String FIND_ALL_MEMBERS_SQL = "SELECT * FROM members";

  /** Câu lệnh SQL để tìm ID thành viên lớn nhất trong năm hiện tại. */
  private static final String FIND_MAX_MEMBER_ID_SQL =
      "SELECT MAX(memberID) FROM members WHERE memberID LIKE ?";

  //** Câu lệnh tìm tổng cố thành viên trong hiện tại
  private static final String FIND_TOTAL_MEMBERS_SQL = "SELECT COUNT(*) FROM members";

  @Override
  public boolean add(Member member) {
    // Thêm một thành viên mới và thiết lập ID duy nhất
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_MEMBER_SQL)) {
      String memberId = generateMemberId(connection); // Tạo một ID duy nhất
      member.setMemberId(memberId); // Gán ID được tạo cho thành viên
      preparedStatement.setString(1, memberId); // Thiết lập ID vào truy vấn
      preparedStatement.setString(2, member.getName());
      preparedStatement.setDate(3, Date.valueOf(member.getDateOfBirth()));
      preparedStatement.setString(4, member.getEmail());
      preparedStatement.setString(5, member.getPhoneNumber());
      preparedStatement.setInt(6, member.getTotalBooksCheckedOut());
      return preparedStatement.executeUpdate() > 0; // Trả về true nếu thêm thành công
    } catch (SQLException e) {
      logger.error("Lỗi khi thêm thành viên: {}", member, e);
      return false;
    }
  }

  /**
   * Tạo ID thành viên duy nhất dựa trên năm hiện tại và bộ đếm tăng dần.
   *
   * @param connection kết nối cơ sở dữ liệu
   * @return ID thành viên được tạo
   * @throws SQLException nếu xảy ra lỗi truy cập cơ sở dữ liệu
   */
  private String generateMemberId(Connection connection) throws SQLException {
    String currentYear = String.valueOf(LocalDate.now().getYear());
    String likePattern = currentYear + "%";
    try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_MAX_MEMBER_ID_SQL)) {
      preparedStatement.setString(1, likePattern); // Lọc ID theo năm hiện tại
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        String maxId = resultSet.getString(1);
        if (maxId != null) {
          int nextId = Integer.parseInt(maxId.substring(4)) + 1;
          return currentYear + String.format("%04d", nextId); // Định dạng thành năm + ID 4 chữ số
        }
      }
    }
    return currentYear + "0001"; // ID đầu tiên trong năm
  }

  @Override
  public boolean update(Member member) {
    // Cập nhật thông tin cho thành viên hiện có
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MEMBER_SQL)) {
      preparedStatement.setString(1, member.getName());
      preparedStatement.setDate(2, Date.valueOf(member.getDateOfBirth()));
      preparedStatement.setString(3, member.getEmail());
      preparedStatement.setString(4, member.getPhoneNumber());
      preparedStatement.setInt(5, member.getTotalBooksCheckedOut());
      preparedStatement.setString(6, member.getMemberId());
      return preparedStatement.executeUpdate() > 0; // Trả về true nếu cập nhật thành công
    } catch (SQLException e) {
      logger.error("Lỗi khi cập nhật thành viên: {}", member, e);
      return false;
    }
  }

  @Override
  public boolean delete(String memberId) {
    // Xóa một thành viên khỏi cơ sở dữ liệu theo ID
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MEMBER_SQL)) {
      preparedStatement.setString(1, memberId); // Thiết lập ID thành viên vào truy vấn
      return preparedStatement.executeUpdate() > 0; // Trả về true nếu xóa thành công
    } catch (SQLException e) {
      logger.error("Lỗi khi xóa thành viên với ID: {}", memberId, e);
      return false;
    }
  }

  @Override
  public Optional<Member> getById(String memberId) {
    // Tìm một thành viên theo ID và trả về dạng Optional
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_MEMBER_BY_ID_SQL)) {
      preparedStatement.setString(1, memberId); // Thiết lập ID thành viên vào truy vấn
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        return Optional.of(mapRowToMember(resultSet)); // Ánh xạ kết quả sang đối tượng Member
      }
    } catch (SQLException e) {
      logger.error("Lỗi khi tìm thành viên theo ID: {}", memberId, e);
    }
    return Optional.empty();
  }

  @Override
  public List<Member> getAll() {
    // Lấy danh sách tất cả thành viên
    List<Member> members = new ArrayList<>();
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_MEMBERS_SQL);
        ResultSet resultSet = preparedStatement.executeQuery()) {

      while (resultSet.next()) {
        members.add(mapRowToMember(resultSet)); // Ánh xạ từng hàng sang đối tượng Member
      }
    } catch (SQLException e) {
      logger.error("Lỗi khi lấy danh sách tất cả thành viên", e);
    }
    return members;
  }

   //Tra ve so luong thanh vien
  public int getTotalMembers() {
    int totalMembers = 0;
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_TOTAL_MEMBERS_SQL)) {
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        totalMembers = resultSet.getInt(1);
      }
    } catch (SQLException e) {
      logger.error("Error total member", e);
    }
    return totalMembers;
  }


  /**
   * Ánh xạ một hàng từ kết quả truy vấn sang đối tượng Member.
   *
   * @param resultSet kết quả từ truy vấn SQL
   * @return đối tượng Member
   * @throws SQLException nếu có lỗi SQL xảy ra trong quá trình ánh xạ
   */
  private Member mapRowToMember(ResultSet resultSet) throws SQLException {
    return new Member(resultSet.getString("memberID"),
        resultSet.getString("name"),
        resultSet.getDate("dateOfBirth").toLocalDate(),
        resultSet.getString("email"),
        resultSet.getString("phoneNumber"),
        resultSet.getInt("total_book_checked_out"));
  }
}
