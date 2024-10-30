package org.group4.base.books;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.group4.base.entities.Book;
import org.group4.base.enums.BookFormat;
import org.group4.base.enums.BookStatus;
import org.group4.database.BookItemDatabase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BookItem extends Book {

  private final String barcode;
  private final boolean isReferenceOnly;
  private LocalDate borrowed;
  private LocalDate dueDate;
  private final double price;
  private final BookFormat format;
  private BookStatus status;
  private final LocalDate dateOfPurchase;
  private final LocalDate publicationDate;

  // Constructor
  public BookItem(@NotNull Book book, String barcode, boolean isReferenceOnly, double price,
      BookFormat format, LocalDate dateOfPurchase, LocalDate publicationDate) {
    super(book.getISBN(), book.getTitle(), book.getSubject(), book.getPublisher(),
        book.getLanguage(), book.getNumberOfPages(), book.getAuthors());
    this.barcode = barcode;
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

  public String getReference() {
    return (isReferenceOnly) ? "Yes" : "No";
  }
  

  // Setter
  public void setStatus(BookStatus status) {
    this.status = status;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public void setBorrowed(LocalDate borrowed) {
    this.borrowed = borrowed;
  }

  /**
   * Method check out a book item.
   *
   * @return true if the book item is available and not reference only, otherwise false
   */
  public boolean checkOut() {
    return !this.isReferenceOnly && this.status == BookStatus.AVAILABLE;
  }

  /**
   * Method fetches the book item details by barcode.
   *
   * @return the book item if found, otherwise null
   */
  @Nullable
  public static BookItem fetchBookItemDetails(String barcode) {
    List<BookItem> bookItems = BookItemDatabase.getInstance().getItems();
    for (BookItem bookItem : bookItems) {
      if (bookItem.getBarcode().equals(barcode)) {
        return bookItem;
      }
    }
    return null;
  }

  // Method prints the book item details.
  @Override
  public void printDetails() {
    super.printDetails();
    System.out.println("Barcode: " + getBarcode());
    System.out.println("Reference only: " + isReferenceOnly);
    System.out.println("Borrowed: " + getBorrowed());
    System.out.println("Due date: " + getDueDate());
    System.out.println("Price: " + getPrice());
    System.out.println("Format: " + getFormat());
    System.out.println("Status: " + getStatus());
    System.out.println("Date of purchase: " + getDateOfPurchase());
    System.out.println("Publication date: " + getPublicationDate());
  }
}
