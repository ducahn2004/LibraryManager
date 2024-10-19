// BookReservation.java
package org.group4.base.books;

import java.time.LocalDate;
import java.util.List;
import org.group4.base.enums.RevervationStatus;
import org.group4.base.database.BookReservationDatabase;
import org.jetbrains.annotations.Nullable;
import org.group4.base.users.Member;

public class BookReservation {
  private final LocalDate creationDate;
  private RevervationStatus status;
  private final BookItem bookItem;
  private final Member member;

  // Constructor
  public BookReservation(BookItem bookItem, Member member) {
    this.bookItem = bookItem;
    this.status = RevervationStatus.NONE;
    this.creationDate = LocalDate.now();
    this.member = member;
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

  public Member getMember() { // Add this getter
    return member;
  }

  // Setter
  public void setStatus(RevervationStatus status) {
    this.status = status;
  }

  @Nullable
  public static BookReservation fetchReservationDetails(String barcode) {
    List<BookReservation> reservations = BookReservationDatabase.getBookReservations();

    for (BookReservation reservation : reservations) {
      if (reservation.getBookItem().getBarcode().equals(barcode)) {
        return reservation;
      }
    }

    return null;
  }
}