package org.group4.test;

import org.group4.model.enums.NotificationType;
import org.group4.model.notification.SystemNotification;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Unit test class for the SystemNotification class.
 */
public class SystemNotificationTest {

  private SystemNotification systemNotification;

  @Before
  public void setUp() {
    // Set up data for the test
    String notificationId = "123";
    NotificationType type = NotificationType.BOOK_BORROW_SUCCESS; // Sample notification type
    String content = "You have successfully borrowed the book.";
    LocalDate createdOn = LocalDate.now();

    // Create a SystemNotification instance for testing
    systemNotification = new SystemNotification(notificationId, type, content, createdOn);
  }

  @Test
  public void testConstructorWithType() {
    // Arrange
    NotificationType type = NotificationType.FORGOT_PASSWORD; // Sample notification type

    // Act
    SystemNotification notification = new SystemNotification(type);

    // Assert
    assertNotNull(notification);
    assertEquals(type, notification.getType());
  }

  @Test
  public void testConstructorWithAllFields() {
    // Act
    String notificationId = "124";
    NotificationType type = NotificationType.DUE_DATE_REMINDER; // Sample notification type
    String content = "This is a reminder that your due date is approaching.";
    LocalDate createdOn = LocalDate.now();

    SystemNotification notification =
        new SystemNotification(notificationId, type, content, createdOn);

    // Assert
    assertNotNull(notification);
    assertEquals(notificationId, notification.getNotificationId());
    assertEquals(type, notification.getType());
    assertEquals(content, notification.getContent());
    assertEquals(createdOn, notification.getCreatedOn());
  }

  @Test
  public void testGetType() {
    // Act
    NotificationType type = systemNotification.getType();

    // Assert
    assertEquals(NotificationType.BOOK_BORROW_SUCCESS, type);
  }

  @Test
  public void testGetNotificationId() {
    // Act
    String notificationId = systemNotification.getNotificationId();

    // Assert
    assertEquals("123", notificationId);
  }

  @Test
  public void testGetContent() {
    // Act
    String content = systemNotification.getContent();

    // Assert
    assertEquals("You have successfully borrowed the book.", content);
  }

  @Test
  public void testGetCreatedOn() {
    // Act
    LocalDate createdOn = systemNotification.getCreatedOn();

    // Assert
    assertEquals(LocalDate.now(), createdOn);
  }
}
