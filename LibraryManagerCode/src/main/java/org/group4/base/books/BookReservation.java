package org.group4.base.books;

import java.time.LocalDate;
import org.group4.base.enums.RevervationStatus;
import org.group4.base.notifications.Notification;
import org.group4.base.users.Member;
import java.util.Random;

/**
 * Quan ly viec dat sach.
 */
public class BookReservation {
  private final LocalDate creationDate; // Ngay tao phieu dat sach: Xac dinh thu tu uu tien, theo doi thoi gian cho.
  private RevervationStatus status; // Trang thai phieu dat sach: Khi phieu dat sach duoc tao, trang thai se cap nhat.

  /**
   * Tao va luu thong tin cua mot phieu dat sach.
   */
  public BookReservation() {
    this.status = RevervationStatus.WAITING;
    this.creationDate = LocalDate.now();
  }

  /**
   * Lay ngay tao phieu dat sach.
   *
   * @return  Ngay tao phieu dat sach.
   */
  public LocalDate getCreationDate() {
    return creationDate;
  }

  /**
   * Lay trang thai cua phieu dat sach.
   *
   * @return Trang thai cua phieu dat sach.
   */
  public RevervationStatus getStatus() {
    return status;
  }

  /**
   * Cap nhat trang thai cua phieu dat sach.
   *
   * @param status Trang thai moi cua phieu dat sach.
   */
  public void setStatus(RevervationStatus status) {
    this.status = status;
  }

  /**
   * Lay thong tin dat truoc cua sach dua tren barcode.
   * Xac minh trang thai dat truoc: Kiem tra xem sach co dang duoc dat truoc boi thanh vien nao khong
   * @Class BookItem hoac Member co the duoc su dung de xac minh trang thai dat truoc
   *
   * @param barcode Ma vach cua sach.
   * @return Thong tin cua phieu dat sach.
   */
  public static BookReservation fetchReservationDetails(String barcode) {
    // TODO: Implement this method
    return null;
  }

  /**
   * Lay thong bao cho thanh vien khi sach duoc dat truoc.
   * @Class Notification duoc su dung de gui thong bao cho thanh vien
   *
   * @param member Thanh vien duoc gui thong bao.
   * @param bookItem Sach duoc dat truoc.
   */
  public void notificationMember(Member member, BookItem bookItem) {
    int notificationId = new Random().nextInt();
    LocalDate createdOn = LocalDate.now();
    String content = "Book " + bookItem.getTitle() + " has been reserved for you.";
    Notification notification = new Notification(notificationId, createdOn, content);
    member.receiveNotification(notification);
  }



}
