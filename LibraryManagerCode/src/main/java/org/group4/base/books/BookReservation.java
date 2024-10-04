package org.group4.base.books;

import java.time.LocalDate;
import org.group4.base.enums.RevervationStatus;
import org.group4.base.notifications.Notification;

public class BookReservation {
  private LocalDate creationDate;
  private RevervationStatus status;
  private String bookItemBarcode;
  private String memberId;

  public BookReservation(LocalDate creationDate, RevervationStatus status, String bookItemBarcode, String memberId) {
    this.creationDate = creationDate;
    this.status = status;
    this.bookItemBarcode = bookItemBarcode;
    this.memberId = memberId;
  }
  public LocalDate getCreationDate() {
    return creationDate;
  }

  public RevervationStatus getStatus() {
    return status;
  }

  public String getBookItemBarcode() {
    return bookItemBarcode;
  }

  public String getMemberId() {
    return memberId;
  }

  public void setStatus(RevervationStatus status) {
    this.status = status;
  }


  public static BookReservation fetchReservationDetails(String bookItemBarcode) {
    return null;
    // return database.getBookReservationByBarCode(bookItem);
  }



}
