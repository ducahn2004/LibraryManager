package org.group4.base.notifications;

import java.util.Random;

import org.group4.database.NotificationDatabase;

import org.group4.base.users.Member;

public class PhoneNotification extends Notification {
  private final String phoneNumber;

  public PhoneNotification(int notificationId, String content, String phoneNumber) {
    super(notificationId, content);
    this.phoneNumber = phoneNumber;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  @Override
  public void printNotification() {
    super.printNotification();
    System.out.println("Phone number: " + phoneNumber);
  }

}