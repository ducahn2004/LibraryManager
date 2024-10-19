package org.group4.base.notifications;

import java.time.LocalDate;
import java.util.Random;

import org.group4.base.users.Member;
import org.group4.base.books.BookItem;
import org.group4.base.database.NotificationDatabase;

/**
 * Lop co so cho cac loai thong bao.
 */
public class Notification {
  private final int notificationId; // Ma so cua thong bao.
  private final LocalDate createdOn; // Ngay tao thong bao.
  private String content; // Noi dung thong bao.

  /**
   * Tao mot thong bao moi.
   * @param notificationId Ma so cua thong bao.
   * @param content Noi dung thong bao.
   */
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
    NotificationDatabase.getNotifications().add(notification);
    member.receiveNotification(notification);
  }


}
