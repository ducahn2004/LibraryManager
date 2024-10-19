package org.group4.base.notifications;

public class EmailNotification extends Notification {
  private String email;

  public EmailNotification(int notificationId, String content, String email) {
    super(notificationId, content);
    this.email = email;
  }
}
