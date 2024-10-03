package org.group4.base.books;

import java.time.LocalDate;
import java.util.List;
import org.group4.base.entities.Author;
import org.group4.base.entities.Book;
import org.group4.base.enums.BookFormat;
import org.group4.base.enums.BookStatus;

public class BookItem extends Book {
  private String barcode;
  private boolean isReferenceOnly;
  private LocalDate borrowed;
  private LocalDate dueDate;
  private double price;
  private BookFormat format;
  private BookStatus status;
  private LocalDate dateOfPurchase;
  private LocalDate publicationDate;

  public BookItem(String ISBN, String title, String subject, String publisher, String language,
      int numberOfPages, List<Author> authors, String barcode, boolean isReferenceOnly, LocalDate borrowed,
      LocalDate dueDate, double price, BookFormat format, BookStatus status,
      LocalDate dateOfPurchase, LocalDate publicationDate) {
    super(ISBN, title, subject, publisher, language, numberOfPages, authors);
    this.barcode = barcode;
    this.isReferenceOnly = isReferenceOnly;
    this.borrowed = borrowed;
    this.dueDate = dueDate;
    this.price = price;
    this.format = format;
    this.status = status;
    this.dateOfPurchase = dateOfPurchase;
    this.publicationDate = publicationDate;
  }

  public String getBarcode() {
    return barcode;
  }

  public void setBarcode(String barcode) {
    this.barcode = barcode;
  }

  public boolean isReferenceOnly() {
    return isReferenceOnly;
  }

  public void setReferenceOnly(boolean isReferenceOnly) {
    this.isReferenceOnly = isReferenceOnly;
  }

  public LocalDate getBorrowed() {
    return borrowed;
  }

  public void setBorrowed(LocalDate borrowed) {
    this.borrowed = borrowed;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public BookFormat getFormat() {
    return format;
  }

  public void setFormat(BookFormat format) {
    this.format = format;
  }

  public BookStatus getStatus() {
    return status;
  }

  public void setStatus(BookStatus status) {
    this.status = status;
  }

  public LocalDate getDateOfPurchase() {
    return dateOfPurchase;
  }

  public void setDateOfPurchase(LocalDate dateOfPurchase) {
    this.dateOfPurchase = dateOfPurchase;
  }

  public LocalDate getPublicationDate() {
    return publicationDate;
  }

  public void setPublicationDate(LocalDate publicationDate) {
    this.publicationDate = publicationDate;
  }

  public boolean checkOut() {
    if (this.isReferenceOnly) {
      return false;
    }
    if (this.status == BookStatus.AVAILABLE) {
      this.status = BookStatus.LOANED;
      this.borrowed = LocalDate.now();
      this.dueDate = this.borrowed.plusDays(14);
      return true;
    }
    return false;
  }
}
