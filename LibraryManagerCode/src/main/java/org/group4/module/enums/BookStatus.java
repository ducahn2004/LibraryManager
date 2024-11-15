package org.group4.module.enums;

/**
 * Represents the possible statuses of a book in a library or catalog system.
 * This enum is used to track the current state of a book, whether it is available for loan,
 * currently loaned out, lost, or if there is no status assigned.
 */
public enum BookStatus {
  /** The book is currently available for loan. */
  AVAILABLE,

  /** The book is currently loaned out to a user. */
  LOANED,

  /** The book has been reported lost and is no longer available. */
  LOST,

  /** No status has been assigned to the book yet. */
  NONE

}
