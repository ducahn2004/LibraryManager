package org.group4.base.books;

import java.time.LocalDate;
import org.group4.base.enums.RevervationStatus;

public class BookReservation {
  private LocalDate creationDate;
  private RevervationStatus status;

  public RevervationStatus getStatus() {
    return status;
  }

  public BookReservation fetchReservationDetails() {
    this.creationDate = LocalDate.now();
    this.status = RevervationStatus.PENDING;
    return this;
  }
}
