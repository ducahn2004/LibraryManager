package org.group4.base.notifications;

import java.util.Random;

import org.group4.database.NotificationDatabase;

import org.group4.base.entities.Address;
import org.group4.base.users.Member;

public class PostalNotification extends Notification {
  private Address address;

  public PostalNotification(int notificationId, String content, Address address) {
    super(notificationId, content);
    this.address = address;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  @Override
  public void printNotification() {
    super.printNotification();
    System.out.println("Address: " + getAddress());
  }

  public static void sendPostalNotification(Member member, String content) {
    int notificationId = new Random().nextInt();
    PostalNotification postalNotification = new PostalNotification(notificationId, content, member.getPerson().getAddress());
    NotificationDatabase.addNotification(member, postalNotification);
    member.receiveNotification(postalNotification);
  }
}