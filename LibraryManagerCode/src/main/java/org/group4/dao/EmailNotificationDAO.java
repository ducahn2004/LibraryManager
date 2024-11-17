package org.group4.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.group4.module.enums.NotificationType;
import org.group4.module.notifications.EmailNotification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailNotificationDAO extends BaseDAO implements GenericDAO<EmailNotification, String> {

  /** Logger for the EmailNotification class. */
  private static final Logger logger = LoggerFactory.getLogger(EmailNotificationDAO.class);

  /** SQL query to add a new email notification to the database. */
  private static final String ADD_NOTIFICATION_SQL =
      "INSERT INTO email_notifications (notificationId, type, email, content, createdOn) "
          + "VALUES (?, ?, ?, ?, ?)";

  /** SQL query to delete an existing email notification from the database. */
  private static final String DELETE_NOTIFICATION_SQL =
      "DELETE FROM email_notifications WHERE notificationId = ?";

  /** SQL query to retrieve an email notification by its unique ID. */
  private static final String GET_NOTIFICATION_BY_ID_SQL =
      "SELECT * FROM email_notifications WHERE notificationId = ?";

  /** SQL query to retrieve all email notifications from the database. */
  private static final String GET_ALL_NOTIFICATIONS_SQL = "SELECT * FROM email_notifications";

  @Override
  public boolean add(EmailNotification emailNotification) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_NOTIFICATION_SQL)) {
      setData(preparedStatement, emailNotification);
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error adding email notification: {}", emailNotification, e);
      return false;
    }
  }

  @Override
  public boolean delete(String notificationId) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_NOTIFICATION_SQL)) {
      preparedStatement.setString(1, notificationId);
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error deleting email notification with ID: {}", notificationId, e);
      return false;
    }
  }

  @Override
  public Optional<EmailNotification> getById(String notificationId) {
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(GET_NOTIFICATION_BY_ID_SQL)) {
      stmt.setString(1, notificationId);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return Optional.of(mapRowToEmailNotification(rs));
        }
      }
    } catch (SQLException e) {
      logger.error("Error retrieving email notification with ID: {}", notificationId, e);
    }
    return Optional.empty();
  }

  @Override
  public List<EmailNotification> getAll() {
    List<EmailNotification> emailNotifications = new ArrayList<>();
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(GET_ALL_NOTIFICATIONS_SQL);
        ResultSet rs = stmt.executeQuery()) {
      while (rs.next()) {
        emailNotifications.add(mapRowToEmailNotification(rs));
      }
    } catch (SQLException e) {
      logger.error("Error retrieving all email notifications", e);
    }
    return emailNotifications;
  }

  /**
   * Maps a row from the email_notifications table to an EmailNotification object.
   *
   * @param rs The ResultSet containing the row data
   * @return An EmailNotification object with the data from the ResultSet
   * @throws SQLException If an error occurs while retrieving data from the ResultSet
   */
  private EmailNotification mapRowToEmailNotification(ResultSet rs) throws SQLException {
    return new EmailNotification(
        rs.getString("notificationId"),
        NotificationType.valueOf(rs.getString("type")),
        rs.getString("content"),
        rs.getString("email"),
        rs.getDate("createdOn").toLocalDate());
  }

  /**
   * Sets the data of an EmailNotification object to a PreparedStatement.
   *
   * @param preparedStatement The PreparedStatement to set the data to
   * @param emailNotification The EmailNotification object to retrieve the data from
   * @throws SQLException If an error occurs while setting the data
   */
  private void setData(PreparedStatement preparedStatement, EmailNotification emailNotification)
      throws SQLException {
    preparedStatement.setString(1, emailNotification.getNotificationId());
    preparedStatement.setString(2, emailNotification.getType().name());
    preparedStatement.setString(3, emailNotification.getEmail());
    preparedStatement.setString(4, emailNotification.getContent());
    preparedStatement.setDate(5, Date.valueOf(emailNotification.getCreatedOn()));
  }
}
