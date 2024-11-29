package org.group4.test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import org.group4.model.enums.NotificationType;
import org.group4.model.notification.EmailNotification;
import org.junit.Before;
import org.junit.Test;

public class EmailNotificationTest {

  private EmailNotification emailNotification;

  @Before
  public void setUp() {
    // Set up any required data for the tests
    String notificationId = "123";
    NotificationType type = NotificationType.BOOK_BORROW_SUCCESS;
    String content = "You have successfully borrowed the book.";
    String email = "user@example.com";
    LocalDate createdOn = LocalDate.now();

    // Create an EmailNotification instance for testing
    emailNotification = new EmailNotification(notificationId, type, content, email, createdOn);
  }

  @Test
  public void testConstructorWithTypeAndEmail() {
    // Arrange
    NotificationType type = NotificationType.FORGOT_PASSWORD;
    String email = "forgotpassword@example.com";

    // Act
    EmailNotification notification = new EmailNotification(type, email);

    // Assert
    assertNotNull(notification);
    assertEquals(type, notification.getType());
    assertEquals(email, notification.getEmail());
  }

  @Test
  public void testGetEmail() {
    // Act
    String email = emailNotification.getEmail();

    // Assert
    assertEquals("user@example.com", email);
  }

  @Test
  public void testConstructorWithAllFields() {
    // Act
    String notificationId = "124";
    NotificationType type = NotificationType.DUE_DATE_REMINDER;
    String content = "This is a reminder that your due date is approaching.";
    String email = "reminder@example.com";
    LocalDate createdOn = LocalDate.now();

    EmailNotification notification = new EmailNotification(
        notificationId, type, content, email, createdOn);

    // Assert
    assertNotNull(notification);
    assertEquals(notificationId, notification.getNotificationId());
    assertEquals(type, notification.getType());
    assertEquals(content, notification.getContent());
    assertEquals(email, notification.getEmail());
    assertEquals(createdOn, notification.getCreatedOn());
  }

  @Test
  public void testNullEmail() {
    // Act
    EmailNotification notification = new EmailNotification(NotificationType.FINE_TRANSACTION, null);

    // Assert
    assertNotNull(notification);
    assertNull(notification.getEmail());
  }

  @Test
  public void testNotificationType() {
    // Act
    NotificationType type = emailNotification.getType();

    // Assert
    assertEquals(NotificationType.BOOK_BORROW_SUCCESS, type);
  }
}
