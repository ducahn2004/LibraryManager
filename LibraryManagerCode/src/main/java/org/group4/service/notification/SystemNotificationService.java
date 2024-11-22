package org.group4.service.notification;

import org.group4.dao.base.FactoryDAO;
import org.group4.model.enums.NotificationType;
import org.group4.model.notification.SystemNotification;

/**
 * Service for handling system notifications.
 */
public class SystemNotificationService {

  private static final SystemNotificationService instance = new SystemNotificationService();

  /** Private constructor to prevent instantiation from other classes. */
  private SystemNotificationService() {}

  /**
   * Gets the singleton instance of the SystemNotificationService.
   *
   * @return The SystemNotificationService instance.
   */
  public static SystemNotificationService getInstance() {
    return instance;
  }

  /**
   * Sends a system notification with the specified type and details.
   *
   * @param type    The type of system notification to send.
   * @param details The details or message to include in the notification.
   */
  public void sendNotification(NotificationType type, String details) {
    SystemNotification notification = new SystemNotification(type);
    String content = generateContent(type, details);
    notification.setContent(content);

    // Store the notification using DAO
    FactoryDAO.getSystemNotificationDAO().add(notification);
  }

  /**
   * Generates the content for a system notification based on its type and details.
   *
   * @param type    The type of system notification.
   * @param details The details or message to include in the notification.
   * @return The generated content for the notification.
   */
  private String generateContent(NotificationType type, String details) {
    return switch (type) {
      case ADD_BOOK_SUCCESS
          -> "The book has been successfully added to the library. Details: \n" + details;
      case DELETE_BOOK_SUCCESS
          -> "The book has been successfully deleted from the library. Details: \n" + details;
      case UPDATE_BOOK_SUCCESS
          -> "The book has been successfully updated in the library. Details: \n" + details;
      case ADD_BOOK_ITEM_SUCCESS
          -> "The book item has been successfully added to the library. Details: \n" + details;
      case DELETE_BOOK_ITEM_SUCCESS
          -> "The book item has been successfully deleted from the library. Details: \n" + details;
      case UPDATE_BOOK_ITEM_SUCCESS
          -> "The book item has been successfully updated in the library. Details: \n" + details;
      case ADD_MEMBER_SUCCESS
          -> "The member has been successfully added to the library. Details: \n" + details;
      case DELETE_MEMBER_SUCCESS
          -> "The member has been successfully deleted from the library. Details: \n" + details;
      case UPDATE_MEMBER_SUCCESS
          -> "The member has been successfully updated in the library. Details: \n" + details;
      default -> "This is a system notification. Details: \n" + details;
    };
  }
}
