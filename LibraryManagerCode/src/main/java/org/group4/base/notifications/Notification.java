package org.group4.base.notifications;

import java.time.LocalDate;

/**
 * Lop co so cho cac loai thong bao.
 */
public class Notification {
  private int notificationId; // Ma so cua thong bao.
  private LocalDate createdOn; // Ngay tao thong bao.
  private String content; // Noi dung thong bao.

  /**
   * Tao mot thong bao moi.
   * @param notificationId Ma so cua thong bao.
   * @param createdOn Ngay tao thong bao.
   * @param content Noi dung thong bao.
   */
  public Notification(int notificationId, LocalDate createdOn, String content) {
    this.notificationId = notificationId;
    this.createdOn = createdOn;
    this.content = content;
  }

  public int getNotificationId() {
    return notificationId;
  }

  public void setNotificationId(int notificationId) {
    this.notificationId = notificationId;
  }

  public LocalDate getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(LocalDate createdOn) {
    this.createdOn = createdOn;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public boolean sendNotification() {
    if (this.content == null || this.content.isEmpty()) {
      return false;
    }
    return true;
  }


}
