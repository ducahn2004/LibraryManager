package org.group4.base.books;

import java.time.LocalDate;
import java.util.List;
import org.group4.base.entities.Author;
import org.group4.base.entities.Book;
import org.group4.base.enums.BookFormat;
import org.group4.base.enums.BookStatus;
import org.group4.base.database.BookItemDatabase;


/**
 * Thong tin chi tiet cua mot cuon sach. Moi cuon sach se co ban sao va duoc quan ly boi ma vach cua no.
 */
public class BookItem extends Book {
  private final String barcode; // Ma vach cua cuon sach.
  private final boolean isReferenceOnly; // Chi dinh sach chi de tham khao.
  private final LocalDate borrowed; // Ngay muon sach.
  private final LocalDate dueDate; // Ngay tra sach.
  private final double price; // Gia cua cuon sach.
  private final BookFormat format; // Dinh dang cua cuon sach.
  private BookStatus status; // Trang thai cua cuon sach.
  private final LocalDate dateOfPurchase; // Ngay mua cuon sach.
  private final LocalDate publicationDate; // Ngay xuat ban cua cuon sach.

  /**
   * Tao mot cuon sach moi.
   *
   * @param ISBN            Ma so quoc te cua cuon sach.
   * @param title           Tieu de cua cuon sach.
   * @param subject         Chu de cua cuon sach.
   * @param publisher       Nha xuat ban cua cuon sach.
   * @param language        Ngon ngu cua cuon sach.
   * @param numberOfPages   So trang cua cuon sach.
   * @param authors         Tac gia cua cuon sach.
   * @param barcode         Ma vach cua cuon sach.
   * @param isReferenceOnly Chi dinh sach chi de tham khao.
   * @param borrowed        Ngay muon sach.
   * @param dueDate         Ngay tra sach.
   * @param price           Gia cua cuon sach.
   * @param format          Dinh dang cua cuon sach.
   * @param status          Trang thai cua cuon sach.
   * @param dateOfPurchase  Ngay mua cuon sach.
   * @param publicationDate Ngay xuat ban cua cuon sach.
   */
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

  public boolean getReferenceOnly() {
    return isReferenceOnly;
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

  /**
   * Kiem tra xem cuon sach co the muon hay khong.
   *
   * @return true neu cuon sach co the muon, nguoc lai tra ve false.
   */
  public boolean checkOut() {
    return !(this.isReferenceOnly || this.status == BookStatus.LOANED);
  }

  /**
   * Lay thong tin chi tiet cua cuon sach dua tren ma vach.
   * @param barcode Ma vach cua cuon sach.
   * @return Thong tin chi tiet cua cuon sach.
   */
  public static BookItem fetchBookItemDetails(String barcode) {
    List<BookItem> bookItems = BookItemDatabase.getBookItems(); // Dang dung database gia lap
    for (BookItem bookItem : bookItems) {
      if (bookItem.getBarcode().equals(barcode)) {
        return bookItem;
      }
    }
    return null;
  }
}
