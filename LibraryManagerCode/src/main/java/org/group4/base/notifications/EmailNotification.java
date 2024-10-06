package org.group4.base.notifications;

import java.time.LocalDate;

/**
 * Quan ly thong bao qua email.
 */
public class EmailNotification extends Notification {
  private String email; // Dia chi email cua nguoi nhan thong bao.

  /**
   * Tao thong bao qua email moi.
   * @param notificationId Ma so cua thong bao.
   * @param createdOn Ngay tao thong bao.
   * @param content Noi dung thong bao.
   * @param email Dia chi email cua nguoi nhan thong bao.
   */
  public EmailNotification(int notificationId, LocalDate createdOn, String content, String email) {
    super(notificationId, createdOn, content);
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
