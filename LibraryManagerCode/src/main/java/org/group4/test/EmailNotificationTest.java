package org.group4.test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.fail;

import org.group4.base.notifications.EmailNotification;
import org.group4.base.enums.NotificationType;
import org.junit.Test;

public class EmailNotificationTest {

  @Test
  public void testEmailNotificationCreation() {
    EmailNotification emailNotification = new EmailNotification(NotificationType.INFO, "Test email content");
    assertNotNull(emailNotification.getNotificationId());
    assertEquals("Test email content", emailNotification.getContent());
    assertEquals(NotificationType.INFO, emailNotification.getType());
  }

  @Test
  public void testSendEmail() {
    try {
      EmailNotification.sendEmail("me", "me", "22022171@vnu.edu.vn",
          "Junit Test Email Notification", "Test Email Notification");
    } catch (Exception e) {
      fail("Email sending failed with exception: " + e.getMessage());
    }
  }
}