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
 */
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
  private Rack placedAt;

  /**
   * Constructor to initialize a BookItem.
   *
   * @param book The base book information.
   * @param isReferenceOnly Whether the book is for reference only.
   * @param price The price of the book item.
   * @param format The format of the book item.
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

  // Private method to generate barcode
  private String generateBarcode(String isbn) {
    return isbn + "-" + UUID.randomUUID().toString();
  }

  // Getter methods
  public String getBarcode() {
    return barcode;
  }

  public String getReference() {
    return isReferenceOnly ? "Yes" : "No";
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

  public Rack getPlacedAt() {
    return placedAt;
  }

  // Setter methods
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

  public void setPlacedAt(Rack placedAt) {
    this.placedAt = placedAt;
  }

  /**
   * Checks whether the book can be checked out. A book can only be checked out if it is
   * not for reference and is available.
   *
   * @return true if the book can be checked out, false otherwise.
   */
  public boolean checkOut() {
    return !isReferenceOnly && status == BookStatus.AVAILABLE;
  }

  /**
   * Fetches BookItem details by barcode.
   *
   * @param barcode The barcode of the book item.
   * @return An Optional containing the BookItem if found, or an empty Optional if not found.
   */
  public static Optional<BookItem> fetchBookItemDetails(String barcode) {
    return BookItemDatabase.getInstance().getItems().stream()
        .filter(bookItem -> bookItem.getBarcode().equals(barcode))
        .findFirst();
  }
}
