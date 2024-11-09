package org.group4.base.books;

import java.time.LocalDate;
import java.util.Optional;
import org.group4.database.BookBorrowDatabase;
import org.group4.base.users.Member;

/**
 * Represents a book lending, where a member borrows a book item for a specified period.
 */
public class BookLending {
  private final BookItem bookItem;
  private final Member member;
  private final LocalDate creationDate;
  private LocalDate dueDate;
  private LocalDate returnDate;

  /**
   * Constructor to initialize a BookLending instance.
   *
   * @param bookItem The book item being borrowed.
   * @param member The member borrowing the book.
   */
  public BookLending(BookItem bookItem, Member member) {
    this.bookItem = bookItem;
    this.member = member;
    this.creationDate = LocalDate.now();
    this.dueDate = creationDate.plusDays(14); // Default 14 days lending period
    this.returnDate = null; // Not returned yet
  }

  // Getter methods
  public LocalDate getCreationDate() {
    return creationDate;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public LocalDate getReturnDate() {
    return returnDate;
  }

  public BookItem getBookItem() {
    return bookItem;
  }

  public Member getMember() {
    return member;
  }

  // Setter methods
  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public void setReturnDate(LocalDate returnDate) {
    this.returnDate = returnDate;
  }

  /**
   * Fetches the details of a lending by the barcode of the book item.
   *
   * @param barcode The barcode of the book item.
   * @return An Optional containing the BookLending instance if found, or an empty Optional.
   */
  public static Optional<BookLending> fetchLendingDetails(String barcode) {
    return BookBorrowDatabase.getInstance().getItems().stream()
        .filter(lending -> lending.getBookItem().getBarcode().equals(barcode))
        .findFirst();
  }
}
