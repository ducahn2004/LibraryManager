package org.group4.base.users;

import java.util.List;
import java.time.LocalDate;
import org.group4.base.books.BookItem;
import org.group4.base.books.BookReservation;
import org.group4.base.database.BookReservationDatabase;
import org.group4.base.entities.Person;
import org.group4.base.enums.BookStatus;
import org.group4.base.enums.RevervationStatus;
import org.group4.base.notifications.Notification;
import org.group4.base.transactions.FineTransaction;
import org.group4.base.database.MemberDatabase;
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

  private final LocalDate dateOfMembership; // Ngay thanh vien
  private int totalBooksCheckedOut; // Tong so sach da muon.
  private static final int MAX_BOOKS_ISSUED_TO_A_USER = 5; // So sach toi da duoc muon.

  /**
   * Tao mot thanh vien moi.
   *
   * @param id       Ma so cua tai khoan.
   * @param password Mat khau cua tai khoan.
   * @param person   Nguoi dung cua tai khoan.
   */
  public Member(String id, String password, Person person) {
    super(id, password, person);
    this.dateOfMembership = LocalDate.now();
    this.totalBooksCheckedOut = 0;
  }

  public String getId() {
    return super.getId();
  }

  public Person getPerson() {
    return super.getPerson();
  }

  public LocalDate getDateOfMembership() {
    return dateOfMembership;
  }

  public int getTotalBooksCheckedOut() {
    return totalBooksCheckedOut;
  }

  public void setTotalBooksCheckedOut (int totalBooksCheckedOut) {
    this.totalBooksCheckedOut = totalBooksCheckedOut;
  }

  public static Member fetchMemberDetails(String id) {
    List<Member> members = MemberDatabase.getMembers(); // Dang dung database gia lap
    for (Member member : members) {
      if (member.getId().equals(id)) {
        return member;
      }
    }
    return null;
  }

  public void receiveNotification(Notification notification) {
    System.out.println("Notification: " + notification.getContent());
  }

  public boolean reserveBookItem(BookItem bookItem) {
    String notificationMessage;
    boolean reservationSuccess = false;

    if (bookItem.checkOut()) {
      BookReservation bookReservation = new BookReservation();
      if (this.totalBooksCheckedOut >= MAX_BOOKS_ISSUED_TO_A_USER) {
        bookReservation.setStatus(RevervationStatus.CANCELED);
        notificationMessage = "Exceeded maximum books issue limit!";
      } else {
        bookReservation.setStatus(RevervationStatus.COMPLETED);
        bookItem.setStatus(BookStatus.RESERVED);
        BookReservationDatabase.getBookReservations().add(bookReservation);
        notificationMessage = "Successfully reserved!";
        reservationSuccess = true;
      }
    } else {
      if (bookItem.getStatus() == BookStatus.LOANED || bookItem.getStatus() == BookStatus.RESERVED ||
          bookItem.getStatus() == BookStatus.LOST) {
        notificationMessage = "Book is not available!";
      } else {
        notificationMessage = "Book is reference only!";
      }
    }

    Notification.sendNotification(this, notificationMessage);
    return reservationSuccess;
  }

  public boolean renewBookItem(BookItem bookItem) {
    // TODO: implement
    return false;
  }

  public void cancelReservation() {
    // TODO: implement
  }

  public void returnBookItem(BookItem bookItem) {
    // TODO: implement
  }

  public void checkForFine(String bookItemBarcode) {
    // TODO: implement
  }

  public void payFine(FineTransaction fineTransaction) {
    // TODO: implement
  }

  public void viewBorrowingHistory() {
    // TODO: implement
  }

  public void viewReservations() {
    // TODO: implement
  }

  public void viewNotifications() {
    // TODO: implement
  }

  public Account updateAccount(Person person) {
    // TODO: implement
    return null;
  }



}


