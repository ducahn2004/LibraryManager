package org.group4.model.transaction;

import java.time.LocalDate;
import java.util.Optional;
import org.group4.model.book.BookItem;
import org.group4.model.user.Member;

/**
 * Represents a book lending transaction, where a member borrows a book item for a specific period.
 * Includes details such as the book's barcode, the member's ID, and the lending, due, and return
 * dates.
 */
public class BookLending {

  private final BookItem bookItem;
  private final Member member;
  private final LocalDate lendingDate;
  private final LocalDate dueDate;
  private LocalDate returnDate;

  /**
   * Constructs a new {@code BookLending} instance with the specified book item and member. The
   * lending date is set to the current date, and the due date is set to 14 days from the lending
   * date. The return date is initially null, indicating that the book has not yet been returned.
   *
   * @param bookItem the book item being lent
   * @param member   the member borrowing the book item
   */
  public BookLending(BookItem bookItem, Member member) {
    this.bookItem = bookItem;
    this.member = member;
    this.lendingDate = LocalDate.now();
    this.dueDate = lendingDate.plusDays(14); // Default 14-day lending period
    this.returnDate = null; // Not returned yet
  }

  /**
   * Constructs a new {@code BookLending} instance with the specified book item, member, and dates.
   *
   * @param bookItem    the book item being lent
   * @param member      the member borrowing the book item
   * @param lendingDate the date when the book item was lent
   * @param dueDate     the date when the book item is due for return
   * @param returnDate  the date when the book item was returned
   */
  public BookLending(BookItem bookItem, Member member, LocalDate lendingDate, LocalDate dueDate,
      LocalDate returnDate) {
    this.bookItem = bookItem;
    this.member = member;
    this.lendingDate = lendingDate;
    this.dueDate = dueDate;
    this.returnDate = returnDate;
  }

  /**
   * Returns the book item being lent.
   *
   * @return The book item being lent.
   */
  public BookItem getBookItem() {
    return bookItem;
  }

  /**
   * Returns the member borrowing the book item.
   *
   * @return The member borrowing the book item.
   */
  public Member getMember() {
    return member;
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
   * @return an {@link Optional} containing the return date if it is not null, or an empty
   * {@link Optional} if the return date is null.
   */
  public Optional<LocalDate> getReturnDate() {
    return Optional.ofNullable(returnDate);
  }

  /**
   * Sets the return date for the book item.
   *
   * @param returnDate The date the book was returned.
   */
  public void setReturnDate(LocalDate returnDate) {
    this.returnDate = returnDate;
  }

  @Override
  public String toString() {
    return "book = " + bookItem.getTitle() + ",\n"
        + "member = " + member.getName() + ",\n"
        + "lendingDate = " + lendingDate.toString() + ",\n"
        + "dueDate = " + dueDate.toString() + ",\n"
        + "returnDate = " + (returnDate != null ? returnDate.toString() : "null");
  }

}
