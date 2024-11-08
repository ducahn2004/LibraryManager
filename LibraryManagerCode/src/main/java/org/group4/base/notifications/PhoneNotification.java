package org.group4.base.notifications;

import org.group4.base.enums.NotificationType;

public class PhoneNotification extends Notification {
  private final String phoneNumber;

  public PhoneNotification(NotificationType type, String content, String phoneNumber) {
    super(type, content);
    this.phoneNumber = phoneNumber;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

}