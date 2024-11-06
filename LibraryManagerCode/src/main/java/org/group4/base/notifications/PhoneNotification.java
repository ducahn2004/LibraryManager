package org.group4.base.notifications;

public class PhoneNotification extends Notification {
  private final String phoneNumber;

  public PhoneNotification(int notificationId, String content, String phoneNumber) {
    super(notificationId, content);
    this.phoneNumber = phoneNumber;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

}