package org.group4.base;
import java.time.LocalDate;

public class BookItem {
  private String barcode;
  private boolean isReferenceOnly;
  private LocalDate borrowed;
  private LocalDate dueDate;
  private double price;
  private BookFormat format;
  private BookStatus status;
  private LocalDate dateOfPurchase;
  private LocalDate publicationDate;

  public boolean checkOut() {
    return false;
  }
}
