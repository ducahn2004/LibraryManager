package org.group4.model.notification;

import java.time.LocalDate;
import org.group4.model.enums.NotificationType;

/**
 * Represents a system notification with a unique ID, creation date, type, and content.
 */
public class SystemNotification extends Notification {

  /**
   * Constructs a SystemNotification with the specified type and content.
   *
   * @param type The type of system notification, as defined by NotificationType.
   */
  public SystemNotification(NotificationType type) {
    super(type);
  }

  /**
   * Constructs a SystemNotification with the specified ID, type, content, and creation date.
   *
   * @param notificationId The unique ID for the system notification.
   * @param type           The type of system notification, as defined by NotificationType.
   * @param content        The message or content of the system notification.
   * @param createdOn      The date when the system notification was created.
   */
  public SystemNotification(String notificationId, NotificationType type, String content,
      LocalDate createdOn) {
    super(notificationId, type, content, createdOn);
  }
}
