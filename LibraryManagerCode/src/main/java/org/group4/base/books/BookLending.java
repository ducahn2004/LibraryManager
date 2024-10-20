package org.group4.base.books;

import java.time.LocalDate;

import org.group4.database.BookLendingDatabase;

import org.group4.base.enums.BookStatus;
import org.group4.base.enums.RevervationStatus;
import org.group4.base.users.Member;

public class BookLending {
  private final BookItem bookItem;
  private final Member member;
  private final LocalDate creationDate;
  private LocalDate dueDate;
  private LocalDate returnDate;

  // Constructor
  public BookLending(BookItem bookItem, Member member) {
    this.bookItem = bookItem;
    this.member = member;
    this.creationDate = LocalDate.now();
    this.dueDate = creationDate.plusDays(14);
    this.returnDate = null;
  }

  // Getters
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

  // Setters
  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public void setReturnDate(LocalDate returnDate) {
    this.returnDate = returnDate;
  }

  /**
   * Method to process lending of a book.
   * Set the book status to LOANED, set the due date to 2 weeks from now.
   * Increment the total books checked out
   *
   * @return String message indicating the success of the lending
   */
  public String processLending() {
    bookItem.setStatus(BookStatus.LOANED);
    bookItem.setDueDate(LocalDate.now().plusWeeks(2));
    member.setTotalBooksCheckedOut(member.getTotalBooksCheckedOut() + 1);
    BookLendingDatabase.getInstance().addItem(this);
    return "Book lending successful. Due date: " + bookItem.getDueDate();
  }

  public String processRenew() {
    if (bookItem.getStatus() == BookStatus.LOANED) {
      BookReservation reservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
      if (reservation == null || reservation.getStatus() != RevervationStatus.WAITING) {
        bookItem.setDueDate(bookItem.getDueDate().plusWeeks(2));
        setDueDate(bookItem.getDueDate());
        return "Book renewal successful. New due date: " + bookItem.getDueDate();
      } else {
        return "Book renewal failed. Book is reserved.";
      }
    } else {
      return "Book renewal failed. Book is not loaned.";
    }
  }

  /**
   * Method to fetch the lending details by barcode from the database.
   *
   * @return the lending details if found, otherwise null
   */
  public static BookLending fetchLendingDetails(String barcode) {
    return BookLendingDatabase.getInstance().getItems().stream()
        .filter(lending -> lending.getBookItem().getBarcode().equals(barcode))
        .findFirst()
        .orElse(null);
  }

}