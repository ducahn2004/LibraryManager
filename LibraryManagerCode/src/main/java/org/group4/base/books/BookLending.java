package org.group4.base.books;

import java.time.LocalDate;
import java.util.Map;

public class BookLending {

  /**
   * Thong tin ve ngay tao, ngay den han va ngay tra sach cua mot phieu muon sach.
   *
   * @param creationDate Ngay tao phieu muon sach
   *                     Khi mot phieu muon sach duoc tao, ngay tao se duoc cap nhat
   *                     Biet ngay muon de xac dinh thoi han tra sach, tinh tien phat neu tra tre
   * @param dueDate      Ngay den han tra sach
   *                     Duoc tinh dua tren so ngay muon toi da cho mot cuon sach
   *                     So sanh voi ngay tra sach de xac dinh qua han chua
   *                     Gui thong bao den han hoac qua han
   * @param returnDate   Ngay tra sach thuc te
   *                     Kiem tra xem sach da duoc dung han hay khong: so sanh voi dueDate
   *                     Tinh tien phat neu tra tre
   */
  private LocalDate creationDate;
  private LocalDate dueDate;
  private LocalDate returnDate;


  /**
   * Tao va luu thong tin cua mot phieu muon sach.
   *
   * @param lendingPeriodDays So ngay toi da cho mot cuon sach
   */
  public BookLending(int lendingPeriodDays) {
    this.creationDate = LocalDate.now();
    this.dueDate = this.creationDate.plusDays(lendingPeriodDays);
    this.returnDate = null;
  }

  public LocalDate getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(LocalDate returnDate) {
    this.returnDate = returnDate;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  /**
   * Phuong thuc muon sach.
   * @param barcode
   * @param id
   * @return
   */
  public boolean lendBookItem(String barcode, String id) {
    // TODO: Kiem tra xem sach co duoc muon khong
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
