package org.group4.model.enums;

/**
 * Represents the different types of notifications that can be sent.
 * This enum is used to categorize various notifications in the system,
 * helping to handle different scenarios, such as reminders, alerts, and updates.
 */
public enum NotificationType {

  /** Notification for welcoming a new user to the system. */
  FORGOT_PASSWORD,

  /** Notification for reminding the user of an upcoming due date for a borrowed item. */
  DUE_DATE_REMINDER,

  /** Notification for informing the user that an item is overdue. */
  OVERDUE_NOTIFICATION,

  /** Notification for informing the user of a fine associated with their account. */
  FINE_TRANSACTION,

  /** Notification for confirming a successful book borrowing transaction. */
  BOOK_BORROW_SUCCESS,

  /** Notification for confirming a successful book return transaction. */
  BOOK_RETURN_SUCCESS,

  /** Notification for confirming a successful book renewal transaction. */
  ADD_BOOK_SUCCESS,

  /** Notification for confirming a successful book deletion transaction. */
  DELETE_BOOK_SUCCESS,

  /** Notification for confirming a successful book update transaction. */
  UPDATE_BOOK_SUCCESS,

  /** Notification for confirming a successful book item addition transaction. */
  ADD_BOOK_ITEM_SUCCESS,

  /** Notification for confirming a successful book item deletion transaction. */
  DELETE_BOOK_ITEM_SUCCESS,

  /** Notification for confirming a successful book item update transaction. */
  UPDATE_BOOK_ITEM_SUCCESS,

  /** Notification for confirming a successful member addition transaction. */
  ADD_MEMBER_SUCCESS,

  /** Notification for confirming a successful member deletion transaction. */
  DELETE_MEMBER_SUCCESS,

  /** Notification for confirming a successful member update transaction. */
  UPDATE_MEMBER_SUCCESS,
}
