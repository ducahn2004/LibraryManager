package org.group4.model.notifications;

import org.group4.dao.FactoryDAO;
import org.group4.model.enums.NotificationType;

import java.time.LocalDate;

/**
 * Represents a system notification with a unique ID, creation date, type, and content.
 * This class extends the Notification class and is used to send system notifications
 * to users, such as reminders, alerts, and updates.
 */
public class SystemNotification extends Notification {

  /**
   * Constructs a SystemNotification with the specified type and content.
   *
   * @param type    The type of system notification, as defined by NotificationType
   */
  public SystemNotification(NotificationType type) {
    super(type);
  }

  /**
   * Constructs a SystemNotification with the specified ID, type, content, and creation date.
   *
   * @param notificationId The unique ID for the system notification
   * @param type           The type of system notification, as defined by NotificationType
   * @param content        The message or content of the system notification
   * @param createdOn      The date when the system notification was created
   */
  public SystemNotification(String notificationId, NotificationType type, String content,
      LocalDate createdOn) {
    super(notificationId, type, content, createdOn);
  }

  /**
   * Sends a system notification with the specified type and details.
   * The content of the notification is generated based on the type and details provided.
   *
   * @param type    The type of system notification to send
   * @param details The details or message to include in the notification
   */
  public static void sendNotification(NotificationType type, String details) {
    SystemNotification notification = new SystemNotification(type);
    String content = switch (type) {
      case ADD_BOOK_SUCCESS ->
          "The book has been successfully added to the library. Details: \n" + details;
      case DELETE_BOOK_SUCCESS ->
          "The book has been successfully deleted from the library. Details: \n" + details;
      case UPDATE_BOOK_SUCCESS ->
          "The book has been successfully updated in the library. Details: \n" + details;
      case ADD_BOOK_ITEM_SUCCESS ->
          "The book item has been successfully added to the library. Details: \n" + details;
      case DELETE_BOOK_ITEM_SUCCESS ->
          "The book item has been successfully deleted from the library. Details: \n" + details;
      case UPDATE_BOOK_ITEM_SUCCESS ->
          "The book item has been successfully updated in the library. Details: \n" + details;
      case ADD_MEMBER_SUCCESS ->
          "The member has been successfully added to the library. Details: \n" + details;
      case DELETE_MEMBER_SUCCESS ->
          "The member has been successfully deleted from the library. Details: \n" + details;
      case UPDATE_MEMBER_SUCCESS ->
          "The member has been successfully updated in the library. Details: \n" + details;
      default -> "This is a system notification. Details: \n" + details;
    };

    notification.setContent(content);
    FactoryDAO.getSystemNotificationDAO().add(notification);
  }

}
