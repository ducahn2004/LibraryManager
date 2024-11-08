package org.group4.test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import org.group4.base.notifications.Notification;
import org.group4.base.enums.NotificationType;
import org.junit.Test;
import java.time.LocalDate;

public class NotificationTest {

  @Test
  public void testNotificationCreation() {
    Notification notification = new Notification(NotificationType.INFO, "Test content");
    assertNotNull(notification.getNotificationId());
    assertEquals(LocalDate.now(), notification.getCreatedOn());
    assertEquals("Test content", notification.getContent());
  }

  @Test
  public void testSetContent() {
    Notification notification = new Notification(NotificationType.INFO, "Initial content");
    notification.setContent("Updated content");
    assertEquals("Updated content", notification.getContent());
  }

  @Test
  public void testGenerateNotificationId() {
    Notification notification = new Notification(NotificationType.INFO, "Test content");
    String notificationId = notification.getNotificationId();
    assertTrue(notificationId.startsWith(NotificationType.INFO.toString()));
    assertEquals(18, notificationId.length());
  }
}