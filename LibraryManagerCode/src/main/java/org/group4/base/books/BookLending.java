package org.group4.base.books;

import java.time.LocalDate;

/**
 * Quan ly viec muon sach.
 */
public class BookLending {
  private final LocalDate creationDate; // Ngay tao phieu muon sach: De tinh thoi han tra sach va tien phat neu tre.
  private LocalDate dueDate; // Ngay den han tra sach: Duoc tinh dua tren so ngay muon toi da
  private LocalDate returnDate; // Ngay tra sach thuc te: So sanh voi voi dueDate de xem co tra dung han khong.


  public BookLending() {
    this.creationDate = LocalDate.now();
    this.dueDate = creationDate.plusDays(14);
    this.returnDate = null;
  }

  // Getter
  public LocalDate getReturnDate() {
    return returnDate;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  // Setter
  public void setReturnDate(LocalDate returnDate) {
    this.returnDate = returnDate;
  }

  /**
   * Phuong thuc muon sach.
   * @param barcode Ma vach cua sach
   * @param id Ma so cua nguoi dung
   * @return true neu muon sach thanh cong, false neu khong muon duoc
   */
  public boolean lendBookItem(String barcode, String id) {
    // TODO: Kiem tra xem sach co duoc muon khong
    // Neu khong return false
    // Neu co:
    // Cap nhat trang thai sach
    // Cap nhat trang thai nguoi dung
    // Cap nhat trang thai phieu muon
    // Gui thong bao muon sach thanh cong

    return true;
  }

  /**
   * Phuong thuc tra sach
   *
   * @param returnDate Ngay tra sach thuc te
   */
  public void returnBookItem(LocalDate returnDate) {
    this.setReturnDate(returnDate);
    // TODO: Kiem tra xem co tra dung han khong
    // Tinh tien phat neu tra tre
    // Gui thong bao tra sach thanh cong
    // Cap nhap trang thai phieu muon
    // Cap nhap trang thai sach
    // Cap nhap trang thai nguoi dung
  }

  public void renewBookItem(int lendingPeriodDays) {
    this.dueDate = dueDate.plusDays(lendingPeriodDays);
  }

  /**
   * Lay thong tin chi tiet cua mot phieu muon sach dua tren barcode.
   *
   * @param barcode Ma vach cua sach.
   * @return Thong tin chi tiet cua phieu muon sach.
   */
  public static BookLending fetchLendingDetails(String barcode) {
    // Truy xuat co so du lieu de lay thong tin chi tiet cua phieu muon sach
    // Map<String, BookLending> lendingDetails = getLendingDatabase();
    // Kiem tra xem ma vach co ton tai trong co so du lieu khong
    // Neu co, tra ve thong tin chi tiet cua phieu muon sach
    // return lendingDetails.get(barcode);
    // Neu khong, tra ve null

    return null;
  }

}
