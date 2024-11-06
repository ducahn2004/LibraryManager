package org.group4.librarymanagercode;

import org.group4.base.notifications.EmailNotification;

public class TestNotification {
  public static void main(String[] args) throws Exception {
      String userId = "me";
      String from = "me";
      String to = "22022189@vnu.edu.vn";
      String subject = "Bung hoc khong em";
      String bodyText = "Chieu nghi di!";

      EmailNotification.sendEmail(userId, from, to, subject, bodyText);
      System.out.println("Email sent successfully.");
  }
}