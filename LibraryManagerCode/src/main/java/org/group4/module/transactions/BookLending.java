package org.group4.module.transactions;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Represents a book lending transaction, where a member borrows a book item for a specific period.
 * Includes details such as the book's barcode, the member's ID, and the lending, due, and return dates.
 */
public class BookLending {

  /** The barcode of the book item being lent. */
  private final String barcode;

  /** The ID of the member borrowing the book item. */
  private final String memberId;

  /** The date when the book item was lent. */
  private final LocalDate lendingDate;

  /** The date when the book item is due for return. */
  private LocalDate dueDate;

  /** The date when the book item was returned, or null if not yet returned. */
  private LocalDate returnDate;

  /**
   * Creates a new BookLending instance with the current date as the lending date.
   * The due date is set to 14 days after the lending date.
   *
   * @param barcode The barcode of the book item.
   * @param memberId The ID of the member borrowing the book.
   */
  public BookLending(String barcode, String memberId) {
    this.barcode = barcode;
    this.memberId = memberId;
    this.lendingDate = LocalDate.now();
    this.dueDate = lendingDate.plusDays(14); // Default 14-day lending period
    this.returnDate = null; // Not returned yet
  }

  /**
   * Creates a new BookLending instance with specified lending, due, and return dates.
   *
   * @param barcode The barcode of the book item.
   * @param memberId The ID of the member borrowing the book.
   * @param lendingDate The date the book was lent.
   * @param dueDate The date the book is due for return.
   * @param returnDate The date the book was returned, or null if not yet returned.
   */
  public BookLending(String barcode, String memberId, LocalDate lendingDate, LocalDate dueDate, LocalDate returnDate) {
    this.barcode = barcode;
    this.memberId = memberId;
    this.lendingDate = lendingDate;
    this.dueDate = dueDate;
    this.returnDate = returnDate;
  }

  /**
   * Returns the barcode of the book item being lent.
   *
   * @return The barcode of the book item.
   */
  public String getBarcode() {
    return barcode;
  }

  /**
   * Returns the ID of the member borrowing the book item.
   *
   * @return The member ID.
   */
  public String getMemberId() {
    return memberId;
  }

  /**
   * Returns the lending date.
   *
   * @return The date when the book item was lent.
   */
  public LocalDate getLendingDate() {
    return lendingDate;
  }

  /**
   * Returns the due date for returning the book item.
   *
   * @return The due date.
   */
  public LocalDate getDueDate() {
    return dueDate;
  }

  /**
   * Returns the return date if present.
   *
   * @return an {@link Optional} containing the return date if it is not null,
   *         or an empty {@link Optional} if the return date is null.
   */
  public Optional<LocalDate> getReturnDate() {
    return Optional.ofNullable(returnDate);
  }


  /**
   * Sets the due date for the book item.
   *
   * @param dueDate The new due date for the book.
   */
  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  /**
   * Sets the return date for the book item.
   *
   * @param returnDate The date the book was returned.
   */
  public void setReturnDate(LocalDate returnDate) {
    this.returnDate = returnDate;
  }
}
