package org.group4.base.users;

import java.time.LocalDate;
import java.util.List;
import org.group4.base.books.BookLending;
import org.group4.base.books.BookReservation;
import org.group4.base.entities.Person;
import org.group4.base.enums.AccountStatus;
import org.group4.base.books.BookItem;
import org.group4.base.enums.BookStatus;
import org.group4.base.enums.RevervationStatus;
import org.group4.base.notifications.Notification;
import org.group4.base.transactions.FineTransaction;

public class Member extends Account {
  private LocalDate dateOfMembership;
  private int totalBooksCheckedOut = 0;
  private static final int MAX_BOOKS_ISSUED_TO_A_USER = 5;

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

//  public boolean checkoutBookItem(BookItem bookItem) {
//    if (this.getTotalBooksCheckedOut() >= MAX_BOOKS_ISSUED_TO_A_USER) {
//      return false;
//    }
//    BookReservation reservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
//    if (reservation != null && !reservation.getMemberId().equals(this.getId())){
//      totalBooksCheckedOut--;
//      bookItem.setStatus(BookStatus.RESERVED);
//
//    }
//
////  }
//
//  public void returnBookItem(BookItem bookItem) {
//
//  }
//
//  public boolean renewBookItem(BookItem bookItem) {
//    return true;
//  }
//
//  public void reserveBookItem(BookItem bookItem) {
//    bookItem.setStatus(BookStatus.NONE);
//  }
//
//  public void cancelReservation() {
//
//  }
//
//  public void checkForFine(String bookItemBarcode) {
//
//  }
//
//  public int getTotalCheckedoutBooks() {
//    return totalBooksCheckedOut;
//  }
//
//  public void receiveNotification(Notification notification) {
//      notification.getMessage();
//  }
//
//  public List<BookLending> viewBorrowingHistory() {
//      return BookLending.fetchLendingHistory(this.getId());
//  }
//
//  public void payFine(FineTransaction fineTransaction) {
//    fineTransaction.processPayment();
//  }
//
}
