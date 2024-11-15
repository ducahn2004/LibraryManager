package org.group4.module.notifications;

import org.group4.module.enums.NotificationType;

import java.time.LocalDate;

/**
 * Represents a system-level notification that is intended to be displayed or logged within the system.
 * This notification type is categorized under {@link NotificationType#SYSTEM}.
 */
public class SystemNotification extends Notification {

  /**
   * Constructs a SystemNotification with the specified content.
   * <p>This notification is automatically assigned the type {@link NotificationType#SYSTEM}.</p>
   *
   * @param content The message or content of the system notification.
   */
  public SystemNotification(String content) {
    super(NotificationType.SYSTEM, content);
  }

  /**
   * Constructs a SystemNotification with the specified ID, content, and creation date.
   * <p>This notification is automatically assigned the type {@link NotificationType#SYSTEM}.</p>
   *
   * @param notificationId The unique ID for the system notification
   * @param content The message or content of the system notification
   * @param createdOn The date when the system notification was created
   */
  public SystemNotification(String notificationId, String content, LocalDate createdOn) {
    super(notificationId, NotificationType.SYSTEM, content, createdOn);
  }

  @Override
  public void sendNotification() throws Exception {

  }
}
