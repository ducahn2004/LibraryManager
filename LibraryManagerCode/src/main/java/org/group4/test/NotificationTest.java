package org.group4.test;

import java.time.LocalDate;
import org.group4.model.enums.NotificationType;
import org.group4.model.notification.Notification;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class NotificationTest {

  public static class MockNotification extends Notification {

    public MockNotification(NotificationType type) {
      super(type);
    }

    public MockNotification(String notificationId, NotificationType type, String content,
        LocalDate createdOn) {
      super(notificationId, type, content, createdOn);
    }
  }

  private MockNotification mockNotification1;
  private MockNotification mockNotification2;

  @Before
  public void setUp() {
    mockNotification1 = new MockNotification(NotificationType.ADD_BOOK_ITEM_SUCCESS);
    mockNotification2 = new MockNotification("12345", NotificationType.BOOK_RETURN_SUCCESS,
        "Book returned successfully", LocalDate.of(2024, 11, 15));
  }

  @Test
  public void testGeneratedNotificationId() {

    assertNotNull(mockNotification1.getNotificationId());
    assertTrue(mockNotification1.getNotificationId()
        .startsWith(NotificationType.ADD_BOOK_ITEM_SUCCESS.toString()));
  }

  @Test
  public void testCreatedOnDefaultConstructor() {
    assertEquals(LocalDate.now(), mockNotification1.getCreatedOn());
  }

  @Test
  public void testCustomConstructor() {

    assertEquals("12345", mockNotification2.getNotificationId());
    assertEquals(NotificationType.BOOK_RETURN_SUCCESS, mockNotification2.getType());
    assertEquals("Book returned successfully", mockNotification2.getContent());
    assertEquals(LocalDate.of(2024, 11, 15), mockNotification2.getCreatedOn());
  }

  @Test
  public void testSetContent() {

    String newContent = "Updated content for notification.";
    mockNotification2.setContent(newContent);

    assertEquals(newContent, mockNotification2.getContent());
  }

  @Test
  public void testGetType() {

    assertEquals(NotificationType.ADD_BOOK_ITEM_SUCCESS, mockNotification1.getType());
  }

  @Test
  public void testToStringRepresentation() {
   
    String expectedIdPrefix = NotificationType.ADD_BOOK_ITEM_SUCCESS.toString();
    assertTrue(mockNotification1.getNotificationId().startsWith(expectedIdPrefix));
  }
}
