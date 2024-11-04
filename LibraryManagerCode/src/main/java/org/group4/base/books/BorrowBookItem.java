package org.group4.base.books;

import java.time.LocalDate;
import org.group4.database.BookBorrowDatabase;
import org.group4.base.users.Member;

public class BorrowBookItem {
  private final BookItem bookItem;
  private final Member member;
  private final LocalDate creationDate;
  private LocalDate dueDate;
  private LocalDate returnDate;

  // Constructor
  public BorrowBookItem(BookItem bookItem, Member member) {
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

  public static BorrowBookItem fetchLendingDetails(String barcode) {
    return BookBorrowDatabase.getInstance().getItems().stream()
        .filter(lending -> lending.getBookItem().getBarcode().equals(barcode))
        .findFirst()
        .orElse(null);
  }

}