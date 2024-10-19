package org.group4.base.books;

import java.time.LocalDate;
import java.util.List;
import org.group4.base.entities.Author;
import org.group4.base.entities.Book;
import org.group4.base.enums.BookFormat;
import org.group4.base.enums.BookStatus;
import org.group4.base.database.BookItemDatabase;

public class BookItem extends Book {
  private final String barcode;
  private final boolean isReferenceOnly;
  private final LocalDate borrowed;
  private LocalDate dueDate;
  private final double price;
  private final BookFormat format;
  private BookStatus status;
  private final LocalDate dateOfPurchase;
  private final LocalDate publicationDate;

  // Constructor
  public BookItem(String ISBN, String title, String subject, String publisher, String language, int numberOfPages,
      List<Author> authors, String barcode, boolean isReferenceOnly, LocalDate borrowed, LocalDate dueDate, double price,
      BookFormat format, BookStatus status, LocalDate dateOfPurchase, LocalDate publicationDate) {
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

  // Getter
  public String getBarcode() {
    return barcode;
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
  public void setStatus(BookStatus status) {
    this.status = status;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  // Method
  public boolean checkOut() {
    return !this.isReferenceOnly && this.status == BookStatus.AVAILABLE;
  }

  public static BookItem fetchBookItemDetails(String barcode) {
    List<BookItem> bookItems = BookItemDatabase.getBookItems();
    for (BookItem bookItem : bookItems) {
      if (bookItem.getBarcode().equals(barcode)) {
        return bookItem;
      }
    }
    return null;
  }

  public void printDetails() {
    System.out.println("Barcode: " + getBarcode());
    System.out.println("Title: " + getTitle());
    System.out.println("Subject: " + getSubject());
    System.out.println("Publisher: " + getPublisher());
    System.out.println("Language: " + getLanguage());
    System.out.println("Number of Pages: " + getNumberOfPages());
    System.out.println("Authors: " + getAuthors());
    System.out.println("Reference Only: " + isReferenceOnly);
    System.out.println("Borrowed: " + getBorrowed());
    System.out.println("Due Date: " + getDueDate());
    System.out.println("Price: " + getPrice());
    System.out.println("Format: " + getFormat());
    System.out.println("Status: " + getStatus());
    System.out.println("Date of Purchase: " + getDateOfPurchase());
    System.out.println("Publication Date: " + getPublicationDate());
  }
}
