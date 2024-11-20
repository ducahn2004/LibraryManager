package org.group4.controller;

import org.group4.module.notifications.EmailNotification;

public class TestNotification {

  public static void main(String[] args) throws Exception {
    String userId = "me";
    String from = "me";
    String to = "22022168@vnu.edu.vn";
    String subject = "Thông báo khẩn: Chuẩn bị đi bảo tàng quân sự vào chủ nhật tuần sau";
    String bodyText = "CCó thể rủ thêm bạn hoặc dẫn theo người yêu cũng được. Xả stress sau thi giữa kì và yêu nước hơn";

    EmailNotification.sendEmail(userId, from, to, subject, bodyText);
    System.out.println("Email sent successfully.");
  }

}