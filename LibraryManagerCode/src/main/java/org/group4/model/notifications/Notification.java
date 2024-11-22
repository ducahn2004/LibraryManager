package org.group4.model.notifications;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.group4.model.enums.NotificationType;

/**
 * Represents a notification with a unique ID, creation date, type, and content.
 * This abstract class serves as a base for various types of notifications, each
 * capable of being sent and customized based on its type and content.
 */
public abstract class Notification {

  /** Unique identifier for the notification, generated from type and timestamp. */
  private final String notificationId;

  /** Date when the notification was created. */
  private final LocalDate createdOn;

  /** Type of the notification as defined by the NotificationType enum. */
  private final NotificationType type;

  /** Content or message of the notification. */
  private String content;

  /**
   * Constructs a new Notification with a specified type and content.
   * <p>The notification ID is generated using the type and the current timestamp.</p>
   *
   * @param type    The type of notification, as defined by NotificationType
   */
  public Notification(NotificationType type) {
    this.notificationId = type.toString() + "_" + LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    this.createdOn = LocalDate.now();
    this.type = type;
    this.content = "";
  }

  /**
   * Constructs a new Notification with specified ID, type, content, and creation date.
   *
   * @param notificationId The unique ID for the notification
   * @param type           The type of notification, as defined by NotificationType
   * @param content        The message or content of the notification
   * @param createdOn      The date when the notification was created
   */
  public Notification(String notificationId, NotificationType type, String content,
      LocalDate createdOn) {
    this.notificationId = notificationId;
    this.createdOn = createdOn;
    this.type = type;
    this.content = content;
  }

  /**
   * Returns the unique ID of the notification.
   *
   * @return A String representing the notification ID
   */
  public String getNotificationId() {
    return notificationId;
  }

  /**
   * Returns the date the notification was created.
   *
   * @return The creation date as a LocalDate object
   */
  public LocalDate getCreatedOn() {
    return createdOn;
  }

  /**
   * Returns the content of the notification.
   *
   * @return The notification content as a String
   */
  public String getContent() {
    return content;
  }

  /**
   * Returns the type of the notification.
   *
   * @return The NotificationType enum value representing the notification type
   */
  public NotificationType getType() {
    return type;
  }

  /**
   * Updates the content of the notification with new information.
   *
   * @param content The new content to set for the notification
   */
  public void setContent(String content) {
    this.content = content;
  }
}
