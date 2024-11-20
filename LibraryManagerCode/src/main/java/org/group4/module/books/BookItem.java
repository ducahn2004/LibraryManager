package org.group4.module.books;

import java.time.LocalDate;
import java.util.Set;

import org.group4.module.enums.BookFormat;
import org.group4.module.enums.BookStatus;

/**
 * Represents an instance of a Book that can be borrowed or referenced.
 * <p>This class encapsulates details specific to a particular book item, such as barcode, status,
 * price, and additional metadata beyond the general book information.</p>
 */
public class BookItem extends Book {

  /** Barcode of the book item, unique identifier for each instance */
  private String barcode;

  /** Indicates if the book is for reference only (cannot be borrowed) */
  private boolean isReferenceOnly;

  /** Date when the book item was borrowed */
  private LocalDate borrowed;

  /** Due date for returning the book item */
  private LocalDate dueDate;

  /** Price of the book item */
  private double price;

  /** Format of the book item (e.g., HARDCOVER, PAPERBACK) */
  private BookFormat format;

  /** Current status of the book item (e.g., AVAILABLE, LOST) */
  private BookStatus status;

  /** Date when the book item was purchased */
  private LocalDate dateOfPurchase;

  /** Publication date of the book item */
  private LocalDate publicationDate;

  /** Rack location where the book item is placed */
  private Rack placedAt;

  /**
   * Constructs a {@code BookItem} instance with specific details.
   *
   * @param book the book to create an item for
   * @param isReferenceOnly whether the book is for reference only
   * @param borrowed the date the book was borrowed
   * @param dueDate the due date for returning the book
   * @param price the price of the book item
   * @param format the format of the book item
   * @param status the status of the book item
   * @param dateOfPurchase the date the book item was purchased
   * @param publicationDate the publication date of the book item
   * @param placedAt the rack location of the book item
   */
  public BookItem(Book book, boolean isReferenceOnly, LocalDate borrowed,
      LocalDate dueDate, double price, BookFormat format, BookStatus status,
      LocalDate dateOfPurchase, LocalDate publicationDate, Rack placedAt) {
    super(book.getISBN(), book.getTitle(), book.getSubject(), book.getPublisher(),
        book.getLanguage(), book.getNumberOfPages(), book.getAuthors());
    this.isReferenceOnly = isReferenceOnly;
    this.borrowed = borrowed;
    this.dueDate = dueDate;
    this.price = price;
    this.format = format;
    this.status = status;
    this.dateOfPurchase = dateOfPurchase;
    this.publicationDate = publicationDate;
    this.placedAt = placedAt;
  }

  /**
   * Constructs a {@code BookItem} instance with specific details.
   *
   * @param book the book to create an item for
   * @param isReferenceOnly whether the book is for reference only
   * @param price the price of the book item
   * @param format the format of the book item
   * @param dateOfPurchase the date the book item was purchased
   * @param publicationDate the publication date of the book item
   * @param placedAt the rack location of the book item
   */
  public BookItem(Book book, boolean isReferenceOnly, double price, BookFormat format,
      LocalDate dateOfPurchase, LocalDate publicationDate, Rack placedAt) {
    super(book.getISBN(), book.getTitle(), book.getSubject(), book.getPublisher(),
        book.getLanguage(), book.getNumberOfPages(), book.getAuthors());
    this.isReferenceOnly = isReferenceOnly;
    this.price = price;
    this.format = format;
    this.status = BookStatus.AVAILABLE;
    this.dateOfPurchase = dateOfPurchase;
    this.publicationDate = publicationDate;
    this.placedAt = placedAt;
  }

  /**
   * Constructs a {@code BookItem} instance with specific details.
   *
   * @param ISBN the ISBN of the book
   * @param title the title of the book
   * @param subject the subject of the book
   * @param publisher the publisher of the book
   * @param language the language of the book
   * @param numberOfPages the number of pages in the book
   * @param authors the set of authors for the book
   * @param barcode the unique barcode for this book item
   * @param isReferenceOnly whether the book is for reference only
   * @param borrowed the date the book was borrowed
   * @param dueDate the due date for returning the book
   * @param price the price of the book item
   * @param format the format of the book item
   * @param status the status of the book item
   * @param dateOfPurchase the date the book item was purchased
   * @param publicationDate the publication date of the book item
   * @param placedAt the rack location of the book item
   */
  public BookItem(String ISBN, String title, String subject, String publisher, String language,
      int numberOfPages, Set<Author> authors, String barcode, boolean isReferenceOnly,
      LocalDate borrowed, LocalDate dueDate, double price, BookFormat format, BookStatus status,
      LocalDate dateOfPurchase, LocalDate publicationDate, Rack placedAt) {
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
    this.placedAt = placedAt;
  }

  /**
   * Retrieves the barcode of the book item.
   *
   * @return the barcode as a String
   */
  public String getBarcode() {
    return barcode;
  }

  /**
   * Checks if the book item is for reference only.
   *
   * @return true if the book is reference-only, false otherwise
   */
  public boolean getIsReferenceOnly() {
    return isReferenceOnly;
  }

  /**
   * Retrieves the date when the book item was borrowed.
   *
   * @return the borrowed date as LocalDate
   */
  public LocalDate getBorrowed() {
    return borrowed;
  }

  /**
   * Retrieves the due date for returning the book item.
   *
   * @return the due date as LocalDate
   */
  public LocalDate getDueDate() {
    return dueDate;
  }

  /**
   * Retrieves the price of the book item.
   *
   * @return the price as a double
   */
  public double getPrice() {
    return price;
  }

  /**
   * Retrieves the format of the book item.
   *
   * @return the format as BookFormat
   */
  public BookFormat getFormat() {
    return format;
  }

  /**
   * Retrieves the current status of the book item.
   *
   * @return the status as BookStatus
   */
  public BookStatus getStatus() {
    return status;
  }

  /**
   * Retrieves the date of purchase of the book item.
   *
   * @return the date of purchase as LocalDate
   */
  public LocalDate getDateOfPurchase() {
    return dateOfPurchase;
  }

  /**
   * Retrieves the publication date of the book item.
   *
   * @return the publication date as LocalDate
   */
  public LocalDate getPublicationDate() {
    return publicationDate;
  }

  /**
   * Retrieves the rack location where the book item is placed.
   *
   * @return the rack where the book item is placed
   */
  public Rack getPlacedAt() {
    return placedAt;
  }

  /**
   * Sets the barcode of the book item.
   *
   * @param barcode the new barcode
   */
  public void setBarcode(String barcode) {
    this.barcode = barcode;
  }

  /**
   * Sets whether the book item is for reference only.
   *
   * @param referenceOnly true if the book is for reference only, false otherwise
   */
  public void setReferenceOnly(boolean referenceOnly) {
    isReferenceOnly = referenceOnly;
  }

  /**
   * Sets the date when the book item was borrowed.
   *
   * @param borrowed the new borrowed date
   */
  public void setBorrowed(LocalDate borrowed) {
    this.borrowed = borrowed;
  }

  /**
   * Sets the due date for returning the book item.
   *
   * @param dueDate the new due date
   */
  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  /**
   * Sets the price of the book item.
   *
   * @param price the new price
   */
  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * Sets the format of the book item.
   *
   * @param format the new format
   */
  public void setFormat(BookFormat format) {
    this.format = format;
  }

  /**
   * Sets the current status of the book item.
   *
   * @param status the new status
   */
  public void setStatus(BookStatus status) {
    this.status = status;
  }

  /**
   * Sets the date of purchase of the book item.
   *
   * @param dateOfPurchase the new date of purchase
   */
  public void setDateOfPurchase(LocalDate dateOfPurchase) {
    this.dateOfPurchase = dateOfPurchase;
  }

  /**
   * Sets the publication date of the book item.
   *
   * @param publicationDate the new publication date
   */
  public void setPublicationDate(LocalDate publicationDate) {
    this.publicationDate = publicationDate;
  }

  /**
   * Sets the rack where the book item is placed.
   *
   * @param placedAt the new rack location
   */
  public void setPlacedAt(Rack placedAt) {
    this.placedAt = placedAt;
  }

  /**
   * Checks if the book item can be checked out.
   * <p>A book item can be checked out only if it is not for reference and is currently available.</p>
   *
   * @return true if the book item can be checked out, false otherwise
   */
  public boolean checkOut() {
    return !isReferenceOnly && status == BookStatus.AVAILABLE;
  }

  @Override
  public String toString() {
    return "BookItem{\n" +
        "  barcode = '" + barcode + "',\n" +
        "  isReferenceOnly =" + isReferenceOnly + ",\n" +
        "  borrowed = " + borrowed + ",\n" +
        "  dueDate = " + dueDate + ",\n" +
        "  price = " + price + ",\n" +
        "  format = " + format + ",\n" +
        "  status = " + status + ",\n" +
        "  dateOfPurchase = " + dateOfPurchase + ",\n" +
        "  publicationDate = " + publicationDate + ",\n" +
        "  placedAt = " + placedAt.toString() + "\n" +
        '}';
  }

}
