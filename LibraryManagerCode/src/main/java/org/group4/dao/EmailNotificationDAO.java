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

  /** Column names in the email_notifications table. */
  private static final String COLUMN_NOTIFICATION_ID = "notificationId";
  private static final String COLUMN_TYPE = "type";
  private static final String COLUMN_EMAIL = "email";
  private static final String COLUMN_CONTENT = "content";
  private static final String COLUMN_CREATED_ON = "createdOn";

  /** SQL statements for CRUD operations on the email_notifications table. */
  private static final String ADD_NOTIFICATION_SQL =
      "INSERT INTO email_notifications ("
          + COLUMN_NOTIFICATION_ID + ", "
          + COLUMN_TYPE + ", "
          + COLUMN_EMAIL + ", "
          + COLUMN_CONTENT + ", "
          + COLUMN_CREATED_ON + ") "
          + "VALUES (?, ?, ?, ?, ?)";

  private static final String DELETE_NOTIFICATION_SQL =
      "DELETE FROM email_notifications WHERE " + COLUMN_NOTIFICATION_ID + " = ?";

  private static final String GET_NOTIFICATION_BY_ID_SQL =
      "SELECT * FROM email_notifications WHERE " + COLUMN_NOTIFICATION_ID + " = ?";

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
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_NOTIFICATION_BY_ID_SQL)) {
      preparedStatement.setString(1, notificationId);
      try (ResultSet rs = preparedStatement.executeQuery()) {
        if (rs.next()) {
          return Optional.ofNullable(mapRowToEmailNotification(rs));
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
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_NOTIFICATIONS_SQL);
        ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        emailNotifications.add(mapRowToEmailNotification(resultSet));
      }
    } catch (SQLException e) {
      logger.error("Error retrieving all email notifications", e);
    }
    return emailNotifications;
  }

  /**
   * Maps a row in the ResultSet to an EmailNotification object.
   *
   * @param resultSet The ResultSet to map
   * @return The EmailNotification object
   */
  private EmailNotification mapRowToEmailNotification(ResultSet resultSet) {
    try {
      return new EmailNotification(
          resultSet.getString(COLUMN_NOTIFICATION_ID),
          NotificationType.valueOf(resultSet.getString(COLUMN_TYPE)),
          resultSet.getString(COLUMN_CONTENT),
          resultSet.getString(COLUMN_EMAIL),
          resultSet.getDate(COLUMN_CREATED_ON).toLocalDate());
    } catch (SQLException e) {
      logger.error("Error mapping row to EmailNotification", e);
      return null;
    }
  }

  /**
   * Sets the data in the prepared statement for an EmailNotification object.
   *
   * @param preparedStatement The prepared statement to set the data in
   * @param emailNotification The EmailNotification object to set the data from
   */
  private void setData(PreparedStatement preparedStatement, EmailNotification emailNotification) {
    try {
      preparedStatement.setString(1, emailNotification.getNotificationId());
      preparedStatement.setString(2, emailNotification.getType().name());
      preparedStatement.setString(3, emailNotification.getEmail());
      preparedStatement.setString(4, emailNotification.getContent());
      preparedStatement.setDate(5, Date.valueOf(emailNotification.getCreatedOn()));
    } catch (SQLException e) {
      logger.error("Error setting data for email notification: {}", emailNotification, e);
    }
  }
}
