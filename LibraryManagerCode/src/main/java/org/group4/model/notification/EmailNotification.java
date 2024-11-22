package org.group4.model.notification;

import org.group4.model.enums.NotificationType;

import java.time.LocalDate;

/**
 * Represents an email notification with a recipient's email address.
 */
public class EmailNotification extends Notification {

  /** Recipient's email address. */
  private final String email;

  /**
   * Constructs an EmailNotification with specified type and email.
   *
   * @param type  The type of email notification.
   * @param email The recipient's email address.
   */
  public EmailNotification(NotificationType type, String email) {
    super(type);
    this.email = email;
  }

  /**
   * Constructs an EmailNotification with specified ID, type, content, email, and creation date.
   *
   * @param notificationId The unique ID for the email notification.
   * @param type           The type of email notification.
   * @param content        The content of the email notification.
   * @param email          The recipient's email address.
   * @param createdOn      The date the email notification was created.
   */
  public EmailNotification(String notificationId, NotificationType type, String content,
      String email, LocalDate createdOn) {
    super(notificationId, type, content, createdOn);
    this.email = email;
  }

  /**
   * Gets the recipient's email address.
   *
   * @return The recipient's email address.
   */
  public String getEmail() {
    return email;
  }
}
