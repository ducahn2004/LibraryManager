package org.group4.base.books;

import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.time.LocalDate;

import org.group4.base.database.BookReservationDatabase;

import org.group4.base.enums.BookStatus;
import org.group4.base.enums.RevervationStatus;
import org.group4.base.users.Member;

public class BookReservation {
  private final LocalDate creationDate;
  private RevervationStatus status;
  private final BookItem bookItem;
  private final Member member;

  // Constructor
  public BookReservation(BookItem bookItem, Member member) {
    this.bookItem = bookItem;
    this.member = member;
    this.creationDate = LocalDate.now();
    this.status = RevervationStatus.NONE;
  }

  // Getter
  public BookItem getBookItem() {
    return bookItem;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public RevervationStatus getStatus() {
    return status;
  }

  public Member getMember() {
    return member;
  }

  // Setter
  public void setStatus(RevervationStatus status) {
    this.status = status;
  }

  public String processReservation() {
    if (bookItem.getStatus() == BookStatus.AVAILABLE) {
      this.setStatus(RevervationStatus.COMPLETED);
      bookItem.setStatus(BookStatus.RESERVED);
      BookReservationDatabase.getInstance().addItem(this);
      return "Successfully reserved! You can check out the book";
    } else {
      this.setStatus(RevervationStatus.WAITING);
      BookReservationDatabase.getInstance().addItem(this);
      return "Book is currently loaned. Reservation is waiting. Due date: " + bookItem.getDueDate();
    }
  }

  @Nullable
  public static BookReservation fetchReservationDetails(String barcode) {
    List<BookReservation> reservations = BookReservationDatabase.getInstance().getItems();

    for (BookReservation reservation : reservations) {
      if (reservation.getBookItem().getBarcode().equals(barcode)) {
        return reservation;
      }
    }

    return null;
  }

  public void printDetails() {
    System.out.println("BookItem: " + bookItem.getBarcode());
    System.out.println("Member: " + member.getId());
    System.out.println("Creation Date: " + getCreationDate());
    System.out.println("Status: " + getStatus());
  }
}