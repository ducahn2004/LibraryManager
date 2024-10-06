package org.group4.base.users;

import java.time.LocalDate;
import org.group4.base.entities.Person;
import org.group4.base.enums.AccountStatus;
import org.group4.base.notifications.Notification;
/**
 * Thanh vien cua thu vien.
 * Quan ly cac hoat dong nhu muon sach, tra sach, gia han sach, dat sach, huy dat sach.
 * Co the xem thong tin cua sach, nguoi dung.
 * Co the xem thong tin cua cac giao dich.
 * Co the xem thong tin cua cac thong bao.
 * Co the xem thong tin cua cac phieu muon, dat, huy sach.
 * Co the xem tong so sach da muon.
 * Co the xem so tien phat.
 * Co the thanh toan tien phat.
 */
public class Member extends Account {
  private LocalDate dateOfMembership; // Ngay thanh vien
  private final int totalBooksCheckedOut = 0; // Tong so sach da muon.
  private static final int MAX_BOOKS_ISSUED_TO_A_USER = 5; // So sach toi da duoc muon.

  /**
   * Tao mot thanh vien moi.
   * @param id Ma so cua tai khoan.
   * @param password Mat khau cua tai khoan.
   * @param person Nguoi dung cua tai khoan.
   */
  public Member(String id, String password, Person person) {
    super(null, null, null, AccountStatus.ACTIVE);
    this.dateOfMembership = LocalDate.now();
  }

  public LocalDate getDateOfMembership() {
    return dateOfMembership;
  }

  public void setDateOfMembership(LocalDate dateOfMembership) {
    this.dateOfMembership = dateOfMembership;
  }

  public int getTotalBooksCheckedOut() {
    return totalBooksCheckedOut;
  }

  public void receiveNotification(Notification notification) {
    System.out.println("Notification: " + notification.getContent());
  }

}

//  public boolean checkoutBookItem(BookItem bookItem) {
//    if (this.getTotalBooksCheckedOut() >= MAX_BOOKS_ISSUED_TO_A_USER) {
//      return false;
//    }
//    BookReservation reservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
//    if (reservation != null && !reservation.getId().equals(this.getId())) {
//      return false;
//    } else if (reservation != null) {
//      reservation.setStatus(RevervationStatus.COMPLETED);
//    }
//
//    bookItem.setStatus(BookStatus.LOANED);
//    totalBooksCheckedOut++;
//    return true;
//  }
//
//  public void returnBookItem(BookItem bookItem) {
//
//  }
//
//  public void checkForFine(String bookItemBarcode) {
//
//  }
//
//  public boolean renewBookItem(BookItem bookItem) {
//
//  }
//
//  public void reserveBookItem(BookItem bookItem) {
//  }
//
//  public void cancelReservation() {
//
//  }
//
//  public int getTotalCheckedoutBooks() {
//
//  }
//
//  public void receiveNotification(Notification notification) {
//
//
//  public List<BookLending> viewBorrowingHistory() {
//
//  }
//
//  public void payFine(FineTransaction fineTransaction) {
//
//  }
//
//}
