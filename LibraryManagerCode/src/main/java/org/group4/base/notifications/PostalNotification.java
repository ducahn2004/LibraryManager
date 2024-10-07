package org.group4.base.notifications;

import java.time.LocalDate;
import org.group4.base.entities.Address;

/**
 * Quan ly thong bao qua buu dien.
 */
public class PostalNotification extends Notification {
  private Address address; // Dia chi nhan thong bao.

  /**
   * Tao thong bao qua buu dien moi.
   * @param notificationId Ma so cua thong bao.
   * @param createdOn Ngay tao thong bao.
   * @param content Noi dung thong bao.
   * @param address Dia chi nhan thong bao.
   */
  public PostalNotification(int notificationId, LocalDate createdOn, String content,
      Address address) {
    super(notificationId, createdOn, content);
    this.address = address;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }
  
  
}
