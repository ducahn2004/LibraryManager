package org.group4.dao.user;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.group4.dao.base.GenericDAO;
import org.group4.dao.base.BaseDAO;
import org.group4.model.user.Member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data Access Object for CRUD operations on {@link Member} entities in the database.
 * Implements {@link GenericDAO} to standardize database operations for Members.
 */
public class MemberDAO extends BaseDAO implements GenericDAO<Member, String> {

  /** The logger for MemberDAO. */
  private static final Logger logger = LoggerFactory.getLogger(MemberDAO.class);

  /** Column names in the members table. */
  private static final String COLUMN_MEMBER_ID = "memberId";
  private static final String COLUMN_NAME = "name";
  private static final String COLUMN_DATE_OF_BIRTH = "dateOfBirth";
  private static final String COLUMN_EMAIL = "email";
  private static final String COLUMN_PHONE_NUMBER = "phoneNumber";
  private static final String COLUMN_TOTAL_BOOKS_CHECKED_OUT = "total_book_checked_out";

  /** SQL statements for CRUD operations on the members table. */
  private static final String ADD_MEMBER_SQL =
      "INSERT INTO members ("
          + COLUMN_MEMBER_ID + ", "
          + COLUMN_NAME + ", "
          + COLUMN_DATE_OF_BIRTH + ", "
          + COLUMN_EMAIL + ", "
          + COLUMN_PHONE_NUMBER + ", "
          + COLUMN_TOTAL_BOOKS_CHECKED_OUT + ") "
          + "VALUES (?, ?, ?, ?, ?, ?)";

  private static final String UPDATE_MEMBER_SQL =
      "UPDATE members SET "
          + COLUMN_NAME + " = ?, "
          + COLUMN_DATE_OF_BIRTH + " = ?, "
          + COLUMN_EMAIL + " = ?, "
          + COLUMN_PHONE_NUMBER + " = ?, "
          + COLUMN_TOTAL_BOOKS_CHECKED_OUT + " = ? "
          + "WHERE " + COLUMN_MEMBER_ID + " = ?";

  private static final String DELETE_MEMBER_SQL
      = "DELETE FROM members WHERE " + COLUMN_MEMBER_ID + " = ?";

  private static final String FIND_MEMBER_BY_ID_SQL
      = "SELECT * FROM members WHERE " + COLUMN_MEMBER_ID + " = ?";

  private static final String FIND_ALL_MEMBERS_SQL = "SELECT * FROM members";

  private static final String FIND_MAX_MEMBER_ID_SQL =
      "SELECT MAX("+ COLUMN_MEMBER_ID +") FROM members WHERE "+ COLUMN_MEMBER_ID +" LIKE ?";

  @Override
  public boolean add(Member member) {
    // Adds a new member and sets a unique ID
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_MEMBER_SQL)) {
      String memberId = generateMemberId(connection); // Generate a unique ID
      member.setMemberId(memberId); // Set generated ID to the member
      preparedStatement.setString(1, memberId); // Set member ID in query
      preparedStatement.setString(2, member.getName());
      preparedStatement.setDate(3, Date.valueOf(member.getDateOfBirth()));
      preparedStatement.setString(4, member.getEmail());
      preparedStatement.setString(5, member.getPhoneNumber());
      preparedStatement.setInt(6, member.getTotalBooksCheckedOut());
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
   */
  private String generateMemberId(Connection connection) {
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
    } catch (SQLException e) {
      logger.error("Error generating member ID", e);
    }
    return currentYear + "0001"; // First ID for the year
  }

  @Override
  public boolean update(Member member) {
    // Updates information for an existing member
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MEMBER_SQL)) {
      preparedStatement.setString(1, member.getName());
      preparedStatement.setDate(2, Date.valueOf(member.getDateOfBirth()));
      preparedStatement.setString(3, member.getEmail());
      preparedStatement.setString(4, member.getPhoneNumber());
      preparedStatement.setInt(5, member.getTotalBooksCheckedOut());
      preparedStatement.setString(6, member.getMemberId());
      return preparedStatement.executeUpdate() > 0; // Return true if update successful
    } catch (SQLException e) {
      logger.error("Error updating member: {}", member, e);
      return false;
    }
  }

  @Override
  public boolean delete(String memberId) {
    // Deletes a member from the database by ID
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MEMBER_SQL)) {
      preparedStatement.setString(1, memberId); // Set member ID in query
      return preparedStatement.executeUpdate() > 0; // Return true if deletion successful
    } catch (SQLException e) {
      logger.error("Error deleting member with ID: {}", memberId, e);
      return false;
    }
  }

  @Override
  public Optional<Member> getById(String memberId) {
    // Finds a member by ID and returns it as Optional
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_MEMBER_BY_ID_SQL)) {
      preparedStatement.setString(1, memberId); // Set member ID in query
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        return Optional.ofNullable(mapRowToMember(resultSet)); // Map result to Member object
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
        ResultSet resultSet = preparedStatement.executeQuery()) {

      while (resultSet.next()) {
        members.add(mapRowToMember(resultSet)); // Map each row to a Member
      }
    } catch (SQLException e) {
      logger.error("Error finding all members", e);
    }
    return members;
  }

  /**
   * Maps a row from the members table to a Member object.
   *
   * @param resultSet the result set from a database query
   * @return the mapped Member object
   */
  private Member mapRowToMember(ResultSet resultSet) {
    try {
      return new Member(resultSet.getString(COLUMN_MEMBER_ID),
          resultSet.getString(COLUMN_NAME),
          resultSet.getDate(COLUMN_DATE_OF_BIRTH).toLocalDate(),
          resultSet.getString(COLUMN_EMAIL),
          resultSet.getString(COLUMN_PHONE_NUMBER),
          resultSet.getInt(COLUMN_TOTAL_BOOKS_CHECKED_OUT));
    } catch (SQLException e) {
      logger.error("Error mapping row to Member", e);
    }
    return null;
  }
}
