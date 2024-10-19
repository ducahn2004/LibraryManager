package org.group4.base.books;

import org.group4.base.database.BookLendingDatabase;
import org.group4.base.enums.BookStatus;
import java.time.LocalDate;
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

  public String processLending() {
    bookItem.setStatus(BookStatus.LOANED);
    bookItem.setDueDate(LocalDate.now().plusWeeks(2));
    member.setTotalBooksCheckedOut(member.getTotalBooksCheckedOut() + 1);
    BookLendingDatabase.getInstance().addItem(this);
    return "Book lending successful. Due date: " + bookItem.getDueDate();
  }

  public static BookLending fetchLendingDetails(String barcode) {
    return BookLendingDatabase.getInstance().getItems().stream()
        .filter(lending -> lending.getBookItem().getBarcode().equals(barcode))
        .findFirst()
        .orElse(null);
  }

}