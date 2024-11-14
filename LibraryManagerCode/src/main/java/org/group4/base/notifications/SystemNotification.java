package org.group4.base.notifications;

import org.group4.base.enums.NotificationType;

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
   * Sends the system notification.
   * <p>Implementation can vary depending on system requirements, such as displaying on a dashboard
   * or writing to a system log. Currently, this method does not contain specific sending logic.</p>
   */
  @Override
  public void sendNotification() {
    // Send the notification to the system (e.g., log, dashboard display)
  }
}
