package org.group4.base.notifications;

import java.util.Random;
import org.group4.database.NotificationDatabase;
import org.group4.base.users.Member;

public class EmailNotification extends Notification {
  private String email;

  public EmailNotification(int notificationId, String content, String email) {
    super(notificationId, content);
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public void printNotification() {
    super.printNotification();
    System.out.println("Email: " + getEmail());
  }

  public static void sendEmailNotification(Member member, String content) {
    int notificationId = new Random().nextInt();
    EmailNotification emailNotification = new EmailNotification(notificationId, content, member.getPerson().getEmail());
    NotificationDatabase.addNotification(member, emailNotification);
    member.receiveNotification(emailNotification);
  }
}