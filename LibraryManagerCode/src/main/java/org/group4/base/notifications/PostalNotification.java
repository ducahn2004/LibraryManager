package org.group4.base.notifications;

import java.time.LocalDate;
import org.group4.base.entities.Address;

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
