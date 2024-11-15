package org.group4.base.notifications;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.group4.base.enums.NotificationType;

/**
 * Represents a notification with a unique ID, creation date, type, and content. This class is
 * designed to serve as a base for various types of notifications.
 */
public class Notification {

  // Unique ID for the notification
  private final String notificationId;
  // Date and time when the notification was created
  private final LocalDateTime createdOn;
  // Type of notification, defined by NotificationType enum
  private final NotificationType type;
  // Content of the notification message
  private String content;

  /**
   * Constructs a new Notification with the specified type and content. The notification ID is
   * generated based on type and timestamp.
   *
   * @param type    The type of notification, specified by NotificationType enum.
   * @param content The content or message of the notification.
   */
  public Notification(NotificationType type, String content) {
    this.notificationId = generateNotificationId(type);
    this.createdOn = LocalDateTime.now();
    this.type = type;
    this.content = content;
  }

  /**
   * Generates a unique notification ID by combining the type and a timestamp.
   *
   * @param type The type of notification.
   * @return A unique notification ID as a String.
   */
  private String generateNotificationId(NotificationType type) {
    return type.toString() + LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
  }

  /**
   * Returns the unique ID of the notification.
   *
   * @return The notification ID as a String.
   */
  public String getNotificationId() {
    return notificationId;
  }

  /**
   * Returns the date the notification was created.
   *
   * @return The creation date of the notification.
   */
  public LocalDateTime getCreatedOn() {
    return createdOn;
  }

  /**
   * Returns the content of the notification.
   *
   * @return The notification content as a String.
   */
  public String getContent() {
    return content;
  }

  /**
   * Returns the type of the notification.
   *
   * @return The NotificationType enum representing the type.
   */
  public NotificationType getType() {
    return type;
  }

  /**
   * Updates the content of the notification.
   *
   * @param content The new content for the notification.
   */
  public void setContent(String content) {
    this.content = content;
  }

}
