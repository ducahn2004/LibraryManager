package org.group4.service.interfaces;

import java.sql.SQLException;
import java.util.List;
import org.group4.model.notification.EmailNotification;
import org.group4.model.notification.SystemNotification;

public interface NotificationManager {

  /**
   * Get all system notifications.
   *
   * @return a list of all system notifications
   * @throws SQLException if an error occurs while retrieving the notifications
   */
  List<SystemNotification> getAllSystemNotifications() throws SQLException;

  /**
   * Get all email notifications.
   *
   * @return a list of all email notifications
   */
  List<EmailNotification> getAllEmailNotifications() throws SQLException;

}
