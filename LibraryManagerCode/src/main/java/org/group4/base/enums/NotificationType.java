package org.group4.base.enums;

/**
 * Represents the different types of notifications that can be sent.
 * This enum is used to categorize various notifications in the system,
 * helping to handle different scenarios, such as reminders, alerts, and updates.
 */
public enum NotificationType {
  /** Notification for reminding the user of an upcoming due date for a borrowed item. */
  DUE_DATE_REMINDER,

  /** Notification for informing the user that an item is overdue. */
  OVERDUE_NOTIFICATION,

  /** Notification for informing the user of a fine associated with their account. */
  FINE_NOTIFICATION,

  /** Notification for resetting the user's password. */
  PASSWORD_RESET,

  /** Notification for informing the user about the arrival of new books in the system. */
  NEW_BOOK_ARRIVAL,

  /** Notification for confirming a successful book borrowing transaction. */
  BOOK_BORROW_SUCCESS,

  /** Notification for confirming a successful book return transaction. */
  BOOK_RETURN_SUCCESS,

  /** General information notification for various non-critical updates. */
  INFO

}
