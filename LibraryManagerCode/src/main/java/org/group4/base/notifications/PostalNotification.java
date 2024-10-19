package org.group4.base.notifications;

import org.group4.base.entities.Address;

public class PostalNotification extends Notification {
  private Address address;

  // Constructor
  public PostalNotification(int notificationId, String content, Address address) {
    super(notificationId, content);
    this.address = address;
  }

}
