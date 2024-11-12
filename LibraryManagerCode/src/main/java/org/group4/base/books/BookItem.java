package org.group4.base.books;

import java.time.LocalDate;
import java.util.UUID;
import java.util.Optional;
import org.group4.base.catalog.Rack;
import org.group4.base.enums.BookFormat;
import org.group4.base.enums.BookStatus;
import org.group4.database.BookItemDatabase;

/**
 * Represents a BookItem, which is an instance of a Book that can be borrowed or referenced.
 * A BookItem contains specific details such as barcode, status, price, and more, in addition to
 * the book's general information.
 */
public class BookItem extends Book {

  // Barcode uniquely identifying the book item
  private final String barcode;

  // Whether the book is for reference only (cannot be borrowed)
  private boolean isReferenceOnly;

  // The date the book was borrowed
  private LocalDate borrowed;

  // The due date for returning the book
  private LocalDate dueDate;

  // Price of the book item
  private double price;

  // Format of the book (e.g., hardcover, paperback)
  private BookFormat format;

  // Current status of the book (e.g., available, loaned, lost)
  private BookStatus status;

  // Date when the book item was purchased
  private LocalDate dateOfPurchase;

  // Publication date of the book item
  private LocalDate publicationDate;

  // The rack where the book item is placed
  private Rack placedAt;

  /**
   * Constructor to initialize a BookItem.
   *
   * @param book The base book information.
   * @param isReferenceOnly Whether the book is for reference only (cannot be borrowed).
   * @param price The price of the book item.
   * @param format The format of the book item (e.g., hardcover, ebook).
   * @param dateOfPurchase The purchase date of the book item.
   * @param publicationDate The publication date of the book item.
   * @param placedAt The rack where the book item is placed.
   */
  public BookItem(Book book, boolean isReferenceOnly, double price, BookFormat format,
      LocalDate dateOfPurchase, LocalDate publicationDate, Rack placedAt) {
    super(book.getISBN(), book.getTitle(), book.getSubject(), book.getPublisher(), book.getLanguage(),
        book.getNumberOfPages(), book.getAuthors());
    this.barcode = generateBarcode(book.getISBN());
    this.isReferenceOnly = isReferenceOnly;
    this.status = BookStatus.AVAILABLE;
    this.dateOfPurchase = dateOfPurchase;
    this.publicationDate = publicationDate;
    this.placedAt = placedAt;
  }

  // Private method to generate a unique barcode based on the ISBN
  private String generateBarcode(String isbn) {
    return isbn + "-" + UUID.randomUUID().toString();
  }

  // Getter methods

  /**
   * Returns the barcode of the book item.
   *
   * @return Barcode as a String
   */
  public String getBarcode() {
    return barcode;
  }

  /**
   * Returns whether the book is for reference only.
   *
   * @return "Yes" if the book is for reference only, "No" otherwise
   */
  public String getReference() {
    return isReferenceOnly ? "Yes" : "No";
  }

  /**
   * Returns the date when the book was borrowed.
   *
   * @return The borrowed date as LocalDate
   */
  public LocalDate getBorrowed() {
    return borrowed;
  }

  /**
   * Returns the due date for returning the book.
   *
   * @return The due date as LocalDate
   */
  public LocalDate getDueDate() {
    return dueDate;
  }

  /**
   * Returns the price of the book item.
   *
   * @return Price as a double
   */
  public double getPrice() {
    return price;
  }

  /**
   * Returns the format of the book item.
   *
   * @return The format as BookFormat
   */
  public BookFormat getFormat() {
    return format;
  }

  /**
   * Returns the current status of the book item.
   *
   * @return The status as BookStatus
   */
  public BookStatus getStatus() {
    return status;
  }

  /**
   * Returns the date of purchase of the book item.
   *
   * @return The date of purchase as LocalDate
   */
  public LocalDate getDateOfPurchase() {
    return dateOfPurchase;
  }

  /**
   * Returns the publication date of the book item.
   *
   * @return The publication date as LocalDate
   */
  public LocalDate getPublicationDate() {
    return publicationDate;
  }

  /**
   * Returns the rack where the book item is placed.
   *
   * @return The rack where the book item is placed
   */
  public Rack getPlacedAt() {
    return placedAt;
  }

  // Setter methods

  /**
   * Sets whether the book is for reference only.
   *
   * @param referenceOnly Whether the book is for reference only
   */
  public void setReferenceOnly(boolean referenceOnly) {
    isReferenceOnly = referenceOnly;
  }

  /**
   * Sets the date when the book was borrowed.
   *
   * @param borrowed The borrowed date
   */
  public void setBorrowed(LocalDate borrowed) {
    this.borrowed = borrowed;
  }

  /**
   * Sets the due date for returning the book.
   *
   * @param dueDate The due date
   */
  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  /**
   * Sets the price of the book item.
   *
   * @param price The price to set
   */
  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * Sets the format of the book item.
   *
   * @param format The format to set
   */
  public void setFormat(BookFormat format) {
    this.format = format;
  }

  /**
   * Sets the status of the book item.
   *
   * @param status The status to set
   */
  public void setStatus(BookStatus status) {
    this.status = status;
  }

  /**
   * Sets the date of purchase of the book item.
   *
   * @param dateOfPurchase The date of purchase to set
   */
  public void setDateOfPurchase(LocalDate dateOfPurchase) {
    this.dateOfPurchase = dateOfPurchase;
  }

  /**
   * Sets the publication date of the book item.
   *
   * @param publicationDate The publication date to set
   */
  public void setPublicationDate(LocalDate publicationDate) {
    this.publicationDate = publicationDate;
  }

  /**
   * Sets the rack where the book item is placed.
   *
   * @param placedAt The rack to set
   */
  public void setPlacedAt(Rack placedAt) {
    this.placedAt = placedAt;
  }

  /**
   * Checks whether the book item can be checked out.
   * A book item can be checked out only if it is not for reference and is currently available.
   *
   * @return true if the book can be checked out, false otherwise
   */
  public boolean checkOut() {
    return !isReferenceOnly && status == BookStatus.AVAILABLE;
  }

  /**
   * Fetches BookItem details by barcode.
   *
   * @param barcode The barcode of the book item
   * @return An Optional containing the BookItem if found, or an empty Optional if not found
   */
  public static Optional<BookItem> fetchBookItemDetails(String barcode) {
    return BookItemDatabase.getInstance().getItems().stream()
        .filter(bookItem -> bookItem.getBarcode().equals(barcode))
        .findFirst();
  }
  
}
