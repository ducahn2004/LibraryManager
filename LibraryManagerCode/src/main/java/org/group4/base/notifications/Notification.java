package org.group4.base.notifications;

import java.time.LocalDate;
import java.util.Random;

import org.group4.base.users.Member;
import org.group4.database.NotificationDatabase;

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

  public static void sendNotification(Member member, String content) {
    int notificationId = new Random().nextInt();
    Notification notification = new Notification(notificationId, content);
    NotificationDatabase.addNotification(member, notification);
    member.receiveNotification(notification);
  }

  public void printNotification() {
    System.out.println("Notification ID: " + getNotificationId());
    System.out.println("Created On: " + getCreatedOn());
    System.out.println("Content: " + getContent());
  }


}
