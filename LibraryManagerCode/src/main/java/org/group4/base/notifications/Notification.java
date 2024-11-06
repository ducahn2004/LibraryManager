package org.group4.base.notifications;

import java.time.LocalDate;

public class Notification {
  private final int notificationId;
  private final LocalDate createdOn;
  private String content;

  // Constructor
  public Notification(int notificationId, String content) {
    this.notificationId = notificationId;
    this.createdOn = LocalDate.now();
    this.content = content;
  }

  public int getNotificationId() {
    return notificationId;
  }

  public LocalDate getCreatedOn() {
    return createdOn;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
