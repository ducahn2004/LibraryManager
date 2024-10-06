package org.group4.base.books;

import java.time.LocalDate;
import java.util.List;
import org.group4.base.entities.Author;
import org.group4.base.entities.Book;
import org.group4.base.enums.BookFormat;
import org.group4.base.enums.BookStatus;

/**
 * Thong tin chi tiet cua mot cuon sach. Moi cuon sach se co ban sao va duoc quan ly boi ma vach cua no.
 */
public class BookItem extends Book {
  private String barcode; // Ma vach cua cuon sach.
  private final boolean isReferenceOnly; // Chi dinh sach chi de tham khao.
  private LocalDate borrowed; // Ngay muon sach.
  private LocalDate dueDate; // Ngay tra sach.
  private double price; // Gia cua cuon sach.
  private BookFormat format; // Dinh dang cua cuon sach.
  private BookStatus status; // Trang thai cua cuon sach.
  private LocalDate dateOfPurchase; // Ngay mua cuon sach.
  private LocalDate publicationDate; // Ngay xuat ban cua cuon sach.

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

  public String getBarcode() {
    return barcode;
  }

  public void setBarcode(String barcode) {
    this.barcode = barcode;
  }

  public boolean isReferenceOnly() {
    return isReferenceOnly;
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

  /**
   * Kiem tra xem cuon sach co the muon hay khong.
   *
   * @return true neu cuon sach co the muon, nguoc lai tra ve false.
   */
  public boolean checkOut(String id) {
    if (this.isReferenceOnly) {
      return false;
    }
    else if (this.status != BookStatus.AVAILABLE) {
      return false;
    }
    // TODO: Implement this method
    return false;
  }

  /**
   * Lay thong tin chi tiet cua mot cuon sach.
   *
   * @param barcode Ma vach cua cuon sach.
   * @return Thong tin chi tiet cua cuon sach.
   */
  public static List<BookItem> fetchBookItemDetails(String barcode) {
    // TODO: Implement this method
    return null;
  }
}
