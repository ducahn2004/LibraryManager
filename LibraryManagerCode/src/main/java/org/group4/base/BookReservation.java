package org.group4.base;

import java.time.LocalDate;

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
