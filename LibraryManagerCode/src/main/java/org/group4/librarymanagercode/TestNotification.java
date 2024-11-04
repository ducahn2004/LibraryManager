package org.group4.librarymanagercode;

import org.group4.base.notifications.EmailNotification;

public class TestNotification {
  public static void main(String[] args) throws Exception {
      String userId = "me";
      String from = "me";
      String to = "22022123@vnu.edu.vn";
      String subject = "Xuống đây ngồi với anh em";
      String bodyText = "Anh em ơi, xuống đây ngồi với anh em đi!";

      EmailNotification.sendEmail(userId, from, to, subject, bodyText);
      System.out.println("Email sent successfully.");
  }
}