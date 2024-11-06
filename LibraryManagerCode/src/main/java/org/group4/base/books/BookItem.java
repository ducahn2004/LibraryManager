package org.group4.base.books;

import java.util.List;
import java.time.LocalDate;
import java.util.UUID;
import org.group4.base.enums.BookFormat;
import org.group4.base.enums.BookStatus;
import org.group4.database.BookItemDatabase;

public class BookItem extends Book {

  private final String barcode;
  private boolean isReferenceOnly;
  private LocalDate borrowed;
  private LocalDate dueDate;
  private double price;
  private BookFormat format;
  private BookStatus status;
  private LocalDate dateOfPurchase;
  private LocalDate publicationDate;

  // Constructor
  public BookItem(Book book, boolean isReferenceOnly, double price, BookFormat format,
      LocalDate dateOfPurchase, LocalDate publicationDate) {
    super(book.getISBN(), book.getTitle(), book.getSubject(), book.getPublisher(), book.getLanguage(),
        book.getNumberOfPages(), book.getAuthors());
    this.barcode = book.getISBN() + "-" + UUID.randomUUID();
    this.isReferenceOnly = isReferenceOnly;
    this.borrowed = null;
    this.dueDate = null;
    this.price = price;
    this.format = format;
    this.status = BookStatus.AVAILABLE;
    this.dateOfPurchase = dateOfPurchase;
    this.publicationDate = publicationDate;
  }

  // Getter
  public String getBarcode() {
    return barcode;
  }

  public String getReference() {
    return (isReferenceOnly) ? "Yes" : "No";
  }

  public LocalDate getBorrowed() {
    return borrowed;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public double getPrice() {
    return price;
  }

  public BookFormat getFormat() {
    return format;
  }

  public BookStatus getStatus() {
    return status;
  }

  public LocalDate getDateOfPurchase() {
    return dateOfPurchase;
  }

  public LocalDate getPublicationDate() {
    return publicationDate;
  }

  // Setter
  public void setReferenceOnly(boolean referenceOnly) {
    isReferenceOnly = referenceOnly;
  }

  public void setBorrowed(LocalDate borrowed) {
    this.borrowed = borrowed;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public void setFormat(BookFormat format) {
    this.format = format;
  }

  public void setStatus(BookStatus status) {
    this.status = status;
  }

  public void setDateOfPurchase(LocalDate dateOfPurchase) {
    this.dateOfPurchase = dateOfPurchase;
  }

  public void setPublicationDate(LocalDate publicationDate) {
    this.publicationDate = publicationDate;
  }

  public boolean checkOut() {
    return !this.isReferenceOnly && this.status == BookStatus.AVAILABLE;
  }

  public static BookItem fetchBookItemDetails(String barcode) {
    List<BookItem> bookItems = BookItemDatabase.getInstance().getItems();
    for (BookItem bookItem : bookItems) {
      if (bookItem.getBarcode().equals(barcode)) {
        return bookItem;
      }
    }
    return null;
  }
}
