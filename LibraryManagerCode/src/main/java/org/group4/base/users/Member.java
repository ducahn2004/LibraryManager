package org.group4.base.users;

import java.util.List;
import java.time.LocalDate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.group4.base.books.BookItem;
import org.group4.base.books.BookReservation;
import org.group4.base.database.BookReservationDatabase;
import org.group4.base.entities.Person;
import org.group4.base.transactions.Fine;
import org.group4.base.enums.BookStatus;
import org.group4.base.enums.RevervationStatus;
import org.group4.base.notifications.Notification;
import org.group4.base.transactions.FineTransaction;
import org.group4.base.database.MemberDatabase;


public class Member extends Account {

  private final LocalDate dateOfMembership;
  private int totalBooksCheckedOut;
  private static final int MAX_BOOKS_ISSUED_TO_A_USER = 5;

  // Constructor
  public Member(String id, String password, Person person) {
    super(id, password, person);
    this.dateOfMembership = LocalDate.now();
    this.totalBooksCheckedOut = 0;
  }

  // Getter
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

  // Setter
  public void setTotalBooksCheckedOut (int totalBooksCheckedOut) {
    this.totalBooksCheckedOut = totalBooksCheckedOut;
  }

  @Nullable
  public static Member fetchMemberDetails(String id) {
    List<Member> members = MemberDatabase.getMembers();
    for (Member member : members) {
      if (member.getId().equals(id)) {
        return member;
      }
    }
    return null;
  }

  public void receiveNotification(@NotNull Notification notification) {
    System.out.println("Notification: " + notification.getContent());
  }

  public void reserveBookItem(@NotNull BookItem bookItem) {
    String notificationMessage;

    if (bookItem.checkOut()) {
      BookReservation bookReservation = new BookReservation(bookItem, this);
      bookReservation.setStatus(RevervationStatus.COMPLETED);
      bookItem.setStatus(BookStatus.RESERVED);
      BookReservationDatabase.getBookReservations().add(bookReservation);
      notificationMessage = "Successfully reserved! You can check out the book";
    } else {
      if (bookItem.getStatus() == BookStatus.LOST) {
        notificationMessage = "Book is lost!";
      } else if (bookItem.getStatus() == BookStatus.LOANED) {
        BookReservation bookReservation = new BookReservation(bookItem, this);
        bookReservation.setStatus(RevervationStatus.WAITING);
        BookReservationDatabase.getBookReservations().add(bookReservation);
        notificationMessage = "Book is currently loaned. Reservation is waiting. Due date: " + bookItem.getDueDate();
      } else if (bookItem.getStatus() == BookStatus.RESERVED) {
        notificationMessage = "Book is reserved! ";
      }
      else {
        notificationMessage = "Book is reference only!";
      }
    }

    Notification.sendNotification(this, notificationMessage);
  }

  public void renewBookItem(@NotNull BookItem bookItem) {
    String notificationMessage;

    if (bookItem.getStatus() == BookStatus.LOANED) {
      BookReservation reservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
      if (reservation == null || reservation.getStatus() != RevervationStatus.WAITING) {
        bookItem.setDueDate(bookItem.getDueDate().plusWeeks(2));
        notificationMessage = "Book renewal successful. New due date: " + bookItem.getDueDate();
      } else {
        notificationMessage = "Book is reserved by another member. Cannot renew.";
      }
    } else {
      notificationMessage = "Book is not currently loaned. Cannot renew.";
    }

    Notification.sendNotification(this, notificationMessage);
  }

  public void cancelReservation(@NotNull BookItem bookItem) {
    String notificationMessage;

    BookReservation reservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
    if (reservation != null && reservation.getStatus() == RevervationStatus.WAITING) {
      reservation.setStatus(RevervationStatus.CANCELED);
      this.totalBooksCheckedOut--;
      BookReservationDatabase.getBookReservations().remove(reservation);
      notificationMessage = "Reservation canceled successfully.";
    } else {
      notificationMessage = "No active reservation found for this book.";
    }

    Notification.sendNotification(this, notificationMessage);
  }

  public void returnBookItem(@NotNull BookItem bookItem) {
    String notificationMessage;

    if (bookItem.getStatus() == BookStatus.LOANED) {
        Fine fine = checkForFine(bookItem.getBarcode());
        if (fine != null) {
          FineTransaction fineTransaction = new FineTransaction(fine);
          fine.payFine(fineTransaction);
          if (!fineTransaction.processFinePayment()) {
            notificationMessage = "Fine payment failed. Cannot return the book.";
            Notification.sendNotification(this, notificationMessage);
            return;
          }

        }

        bookItem.setStatus(BookStatus.AVAILABLE);
        bookItem.setDueDate(null);
        this.totalBooksCheckedOut--;

        notificationMessage = "Book returned successfully.";
    } else {
        notificationMessage = "Book is not currently loaned! Cannot return.";
    }

    Notification.sendNotification(this, notificationMessage);
  }

  public Fine checkForFine(String bookItemBarcode) {
    BookItem bookItem = BookItem.fetchBookItemDetails(bookItemBarcode);
    if (bookItem != null && bookItem.getStatus() == BookStatus.LOANED && bookItem.getDueDate().isBefore(LocalDate.now())) {
        Fine fine = new Fine();
        fine.calculateFine(bookItem);
        Notification.sendNotification(this, "Fine amount: " + fine.getAmount());
        return fine;
    }

    Notification.sendNotification(this, "You have no fine.");
    return null;
  }

  public void lendBookItem(@NotNull BookItem bookItem) {
    String notificationMessage;

    BookReservation reservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
    if (reservation != null && reservation.getStatus() == RevervationStatus.COMPLETED) {
      if (reservation.getBookItem().getBarcode().equals(bookItem.getBarcode()) && reservation.getMember().equals(this)) {
        if (this.totalBooksCheckedOut >= MAX_BOOKS_ISSUED_TO_A_USER) {
          notificationMessage = "Exceeded maximum books issue limit!";
        } else {
        bookItem.setStatus(BookStatus.LOANED);
        bookItem.setDueDate(LocalDate.now().plusWeeks(2));
        this.totalBooksCheckedOut++;
        notificationMessage = "Book lending successful. Due date: " + bookItem.getDueDate();
        }
        } else {
          notificationMessage = "Book is reserved by another member.";
        }
    } else if (bookItem.checkOut()) {
        if (this.totalBooksCheckedOut >= MAX_BOOKS_ISSUED_TO_A_USER) {
          notificationMessage = "Exceeded maximum books issue limit!";
        } else {
          bookItem.setStatus(BookStatus.LOANED);
          bookItem.setDueDate(LocalDate.now().plusWeeks(2));
          this.totalBooksCheckedOut++;
          notificationMessage = "Book lending successful. Due date: " + bookItem.getDueDate();
        }
    } else {
        notificationMessage = "Book is not available for lending.";
    }

    Notification.sendNotification(this, notificationMessage);
  }

  public void viewLendingHistory() {

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


