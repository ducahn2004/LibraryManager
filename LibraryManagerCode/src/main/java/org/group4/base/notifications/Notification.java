package org.group4.base.notifications;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.group4.base.enums.NotificationType;

public class Notification {
  private final String notificationId;
  private final LocalDate createdOn;
  private final NotificationType type;
  private String content;

  // Constructor
  public Notification(NotificationType type, String content) {
    this.notificationId = generateNotificationId(type);
    this.createdOn = LocalDate.now();
    this.type = type;
    this.content = content;
  }

  private String generateNotificationId(NotificationType type) {
    return type.toString() + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
  }

  public String getNotificationId() {
    return notificationId;
  }

  public LocalDate getCreatedOn() {
    return createdOn;
  }

  public String getContent() {
    return content;
  }

  public NotificationType getType() {
    return type;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
