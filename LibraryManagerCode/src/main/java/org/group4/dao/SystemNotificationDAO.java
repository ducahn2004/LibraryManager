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
import org.group4.module.notifications.SystemNotification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data Access Object (DAO) for managing system notifications in the database.
 */
public class SystemNotificationDAO extends BaseDAO implements GenericDAO<SystemNotification, String> {

  /** Logger for the SystemNotificationDAO class. */
  private static final Logger logger = LoggerFactory.getLogger(SystemNotificationDAO.class);

  /** SQL query to add a new system notification to the database. */
  private static final String ADD_NOTIFICATION_SQL =
      "INSERT INTO system_notifications (notificationId, type, content, createdOn) "
          + "VALUES (?, ?, ?, ?)";

  /** SQL query to delete an existing system notification from the database. */
  private static final String DELETE_NOTIFICATION_SQL =
      "DELETE FROM system_notifications WHERE notificationId = ?";

  /** SQL query to retrieve a system notification by its unique ID. */
  private static final String GET_NOTIFICATION_BY_ID_SQL =
      "SELECT * FROM system_notifications WHERE notificationId = ?";

  /** SQL query to retrieve all system notifications from the database. */
  private static final String GET_ALL_NOTIFICATIONS_SQL = "SELECT * FROM system_notifications";

  /**
   * Adds a new system notification to the database.
   *
   * @param systemNotification The system notification to add.
   * @return true if the notification was added successfully, false otherwise.
   */
  public boolean add(SystemNotification systemNotification) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_NOTIFICATION_SQL)) {
      preparedStatement.setString(1, systemNotification.getNotificationId());
      preparedStatement.setString(2, systemNotification.getType().name());
      preparedStatement.setString(3, systemNotification.getContent());
      preparedStatement.setDate(4, Date.valueOf(systemNotification.getCreatedOn()));
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error adding system notification: {}", systemNotification, e);
      return false;
    }
  }

  /**
   * Deletes a system notification from the database by its ID.
   *
   * @param notificationId The ID of the notification to delete.
   * @return true if the notification was deleted successfully, false otherwise.
   */
  public boolean delete(String notificationId) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_NOTIFICATION_SQL)) {
      preparedStatement.setString(1, notificationId);
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error deleting system notification: {}", notificationId, e);
      return false;
    }
  }

  /**
   * Retrieves a system notification by its ID.
   *
   * @param notificationId The ID of the notification to retrieve.
   * @return An Optional containing the SystemNotification if found, or an empty Optional otherwise.
   */
  public Optional<SystemNotification> getById(String notificationId) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_NOTIFICATION_BY_ID_SQL)) {
      preparedStatement.setString(1, notificationId);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return Optional.of(mapRowToSystemNotification(resultSet));
        }
      }
    } catch (SQLException e) {
      logger.error("Error retrieving system notification: {}", notificationId, e);
    }
    return Optional.empty();
  }

  /**
   * Retrieves all system notifications from the database.
   *
   * @return A list of all system notifications.
   */
  public List<SystemNotification> getAll() {
    List<SystemNotification> notifications = new ArrayList<>();
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_NOTIFICATIONS_SQL);
        ResultSet rs = preparedStatement.executeQuery()) {
      while (rs.next()) {
        notifications.add(mapRowToSystemNotification(rs));
      }
    } catch (SQLException e) {
      logger.error("Error retrieving all system notifications", e);
    }
    return notifications;
  }

  /**
   * Maps a ResultSet row to a SystemNotification object.
   *
   * @param resultSet The ResultSet containing the row data.
   * @return A SystemNotification object.
   * @throws SQLException If an error occurs while mapping the data.
   */
  private SystemNotification mapRowToSystemNotification(ResultSet resultSet) throws SQLException {
    return new SystemNotification(
        resultSet.getString("notificationId"),
        NotificationType.valueOf(resultSet.getString("type")),
        resultSet.getString("content"),
        resultSet.getDate("createdOn").toLocalDate());
  }
}
