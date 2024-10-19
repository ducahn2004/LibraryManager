package org.group4.base.books;

import java.time.LocalDate;
import org.group4.base.enums.RevervationStatus;


/**
 * Quan ly viec dat sach.
 */
public class BookReservation {
  private final LocalDate creationDate; // Ngay tao phieu dat sach: Xac dinh thu tu uu tien, theo doi thoi gian cho.
  private RevervationStatus status; // Trang thai phieu dat sach: Khi phieu dat sach duoc tao, trang thai se cap nhat.

  // Constructor
  public BookReservation() {
    this.status = RevervationStatus.WAITING;
    this.creationDate = LocalDate.now();
  }

  // Getter
  public LocalDate getCreationDate() {
    return creationDate;
  }

  public RevervationStatus getStatus() {
    return status;
  }

  // Setter
  public void setStatus(RevervationStatus status) {
    this.status = status;
  }

  /**
   * Lay thong tin dat truoc cua sach dua tren barcode.
   * Xac minh trang thai dat truoc: Kiem tra xem sach co dang duoc dat truoc boi thanh vien nao khong
   *
   * @param barcode Ma vach cua sach.
   * @return Thong tin cua phieu dat sach.
   */
  public static BookReservation fetchReservationDetails(String barcode) {
    // TODO: Implement this method
    return null;
  }

}
