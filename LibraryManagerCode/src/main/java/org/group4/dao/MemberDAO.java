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
 * Data Access Object for CRUD operations on {@link Member} entities in the database.
 * Implements {@link GenericDAO} to standardize database operations for Members.
 */
public class MemberDAO extends BaseDAO implements GenericDAO<Member, String> {

  /** The logger for MemberDAO. */
  private static final Logger logger = LoggerFactory.getLogger(MemberDAO.class);

  /** SQL query to add a new member to the database. */
  private static final String ADD_MEMBER_SQL =
      "INSERT INTO members (memberId, name, dateOfBirth, email, phoneNumber) VALUES (?, ?, ?, ?, ?)";

  /** SQL query to update an existing member in the database. */
  private static final String UPDATE_MEMBER_SQL =
      "UPDATE members SET name = ?, dateOfBirth = ?, email = ?, phoneNumber = ? WHERE memberID = ?";

  /** SQL query to delete a member from the database by ID. */
  private static final String DELETE_MEMBER_SQL = "DELETE FROM members WHERE memberID = ?";

  /** SQL query to find a member by ID. */
  private static final String FIND_MEMBER_BY_ID_SQL = "SELECT * FROM members WHERE memberID = ?";

  /** SQL query to find all members in the database. */
  private static final String FIND_ALL_MEMBERS_SQL = "SELECT * FROM members";

  /** SQL query to find the maximum member ID for the current year. */
  private static final String FIND_MAX_MEMBER_ID_SQL =
      "SELECT MAX(memberID) FROM members WHERE memberID LIKE ?";

  @Override
  public boolean add(Member member) {
    // Adds a new member and sets a unique ID
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_MEMBER_SQL)) {
      String memberId = generateMemberId(connection); // Generate a unique ID
      member.setMemberId(memberId); // Set generated ID to the member
      setMemberData(preparedStatement, member); // Set data from member object
      return preparedStatement.executeUpdate() > 0; // Return true if addition successful
    } catch (SQLException e) {
      logger.error("Error adding member: {}", member, e);
      return false;
    }
  }

  /**
   * Generates a unique member ID based on the current year and incrementing counter.
   *
   * @param connection the database connection
   * @return the generated member ID
   * @throws SQLException if a database access error occurs
   */
  private String generateMemberId(Connection connection) throws SQLException {
    String currentYear = String.valueOf(LocalDate.now().getYear());
    String likePattern = currentYear + "%";
    try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_MAX_MEMBER_ID_SQL)) {
      preparedStatement.setString(1, likePattern); // Filter IDs for the current year
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        String maxId = resultSet.getString(1);
        if (maxId != null) {
          int nextId = Integer.parseInt(maxId.substring(4)) + 1;
          return currentYear + String.format("%04d", nextId); // Format to year + 4-digit ID
        }
      }
    }
    return currentYear + "0001"; // First ID for the year
  }

  @Override
  public boolean update(Member member) {
    // Updates information for an existing member
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MEMBER_SQL)) {
      setMemberData(preparedStatement, member); // Set data from member object
      preparedStatement.setString(5, member.getMemberId());
      return preparedStatement.executeUpdate() > 0; // Return true if update successful
    } catch (SQLException e) {
      logger.error("Error updating member: {}", member, e);
      return false;
    }
  }

  @Override
  public boolean delete(Member member) {
    // Deletes a member from the database by ID
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MEMBER_SQL)) {
      preparedStatement.setString(1, member.getMemberId());
      return preparedStatement.executeUpdate() > 0; // Return true if deletion successful
    } catch (SQLException e) {
      logger.error("Error deleting member: {}", member, e);
      return false;
    }
  }

  @Override
  public Optional<Member> getById(String memberId) {
    // Finds a member by ID and returns it as Optional
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_MEMBER_BY_ID_SQL)) {
      preparedStatement.setString(1, memberId); // Set member ID in query
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        return Optional.of(mapRowToMember(rs)); // Map result to Member object
      }
    } catch (SQLException e) {
      logger.error("Error finding member by ID: {}", memberId, e);
    }
    return Optional.empty();
  }

  @Override
  public List<Member> getAll() {
    // Retrieves a list of all members
    List<Member> members = new ArrayList<>();
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_MEMBERS_SQL);
        ResultSet rs = preparedStatement.executeQuery()) {

      while (rs.next()) {
        members.add(mapRowToMember(rs)); // Map each row to a Member
      }
    } catch (SQLException e) {
      logger.error("Error finding all members", e);
    }
    return members;
  }

  /**
   * Maps the current row in the ResultSet to a Member object.
   *
   * @param rs the ResultSet containing member data
   * @return a new Member populated with data from the ResultSet
   * @throws SQLException if any database access error occurs
   */
  private Member mapRowToMember(ResultSet rs) throws SQLException {
    // Map attributes from ResultSet to a new Member object
    String memberId = rs.getString("memberID");
    String name = rs.getString("name");
    Date dateOfBirth = rs.getDate("dateOfBirth");
    String email = rs.getString("email");
    String phoneNumber = rs.getString("phoneNumber");

    // Initialize Member and set the memberId
    Member member = new Member(name, dateOfBirth.toLocalDate(), email, phoneNumber);
    member.setMemberId(memberId);
    return member;
  }

  /**
   * Populates a PreparedStatement with Member data.
   * Used to avoid redundant data-setting code.
   *
   * @param preparedStatement the PreparedStatement to populate
   * @param member the Member object providing data
   * @throws SQLException if a database access error occurs
   */
  private void setMemberData(PreparedStatement preparedStatement, Member member) throws SQLException {
    preparedStatement.setString(1, member.getName());
    preparedStatement.setDate(2, Date.valueOf(member.getDateOfBirth()));
    preparedStatement.setString(3, member.getEmail());
    preparedStatement.setString(4, member.getPhoneNumber());
  }
}
