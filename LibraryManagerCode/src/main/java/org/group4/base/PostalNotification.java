package org.group4.base;

import java.time.LocalDate;

public class PostalNotification extends Notification {
  private Address address;

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
