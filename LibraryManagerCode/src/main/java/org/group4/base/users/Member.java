package org.group4.base.users;

import java.util.List;
import java.time.LocalDate;

import org.group4.base.database.NotificationDatabase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.group4.base.database.BookLendingDatabase;
import org.group4.base.database.BookReservationDatabase;
import org.group4.base.database.MemberDatabase;

import org.group4.base.enums.BookStatus;
import org.group4.base.enums.RevervationStatus;
import org.group4.base.books.BookItem;
import org.group4.base.books.BookReservation;
import org.group4.base.entities.Person;
import org.group4.base.transactions.Fine;
import org.group4.base.notifications.Notification;
import org.group4.base.transactions.FineTransaction;
import org.group4.base.books.BookLending;

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
  public void setTotalBooksCheckedOut(int totalBooksCheckedOut) {
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
    notification.printNotification();
  }

  public void reserveBookItem(@NotNull BookItem bookItem) {
    String notificationMessage;

    if (bookItem.checkOut()) {
      BookReservation bookReservation = new BookReservation(bookItem, this);
      notificationMessage = bookReservation.processReservation();
    } else {
      if (bookItem.getStatus() == BookStatus.LOST) {
        notificationMessage = "Book is lost!";
      } else if (bookItem.getStatus() == BookStatus.LOANED) {
        BookReservation bookReservation = new BookReservation(bookItem, this);
        notificationMessage = bookReservation.processReservation();
      } else if (bookItem.getStatus() == BookStatus.RESERVED) {
        notificationMessage = "Book is reserved! ";
      } else {
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
    if (reservation != null && (reservation.getStatus() == RevervationStatus.WAITING ||
        reservation.getStatus() == RevervationStatus.COMPLETED)) {
      reservation.setStatus(RevervationStatus.CANCELED);
      this.totalBooksCheckedOut--;
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
    if (bookItem != null && bookItem.getStatus() == BookStatus.LOANED && bookItem.getDueDate()
        .isBefore(LocalDate.now())) {
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

    if (this.totalBooksCheckedOut >= MAX_BOOKS_ISSUED_TO_A_USER) {
      notificationMessage = "Exceeded maximum books issue limit!";
    } else {
      BookReservation reservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
      if (reservation != null && reservation.getStatus() == RevervationStatus.COMPLETED) {
        if (reservation.getBookItem().getBarcode().equals(bookItem.getBarcode()) && reservation.getMember()
            .equals(this)) {
          BookLending bookLending = new BookLending(bookItem, this);
          notificationMessage = bookLending.processLending();
        } else {
          notificationMessage = "Book is reserved by another member.";
        }
      } else if (bookItem.checkOut()) {
        BookLending bookLending = new BookLending(bookItem, this);
        notificationMessage = bookLending.processLending();
      } else {
        notificationMessage = "Book is not available for lending.";
      }
    }

    Notification.sendNotification(this, notificationMessage);
  }

  public void viewLendingHistory() {
    List<BookLending> memberLendings = BookLendingDatabase.getBookLendings().stream()
        .filter(lending -> lending.getMember().equals(this))
        .toList();

    if (memberLendings.isEmpty()) {
      System.out.println("No lending history found for member: " + this.getId());
    } else {
      System.out.println("Lending history for member: " + this.getId());
      for (BookLending lending : memberLendings) {
        System.out.println("Book Barcode: " + lending.getBookItem().getBarcode() +
            ", Creation Date: " + lending.getCreationDate() +
            ", Due Date: " + lending.getDueDate() +
            ", Return Date: " + (lending.getReturnDate() != null ? lending.getReturnDate() : "Not returned yet"));
      }
    }
  }

  public void printDetails() {
    System.out.println("ID: " + this.getId());
    System.out.println("Name: " + this.getPerson().getName());
    System.out.println("Email: " + this.getPerson().getEmail());
    System.out.println("Date Of Membership: " + this.getDateOfMembership());
  }

  public void viewReservationsHistory() {
    List<BookReservation> memberReservations = BookReservationDatabase.getBookReservations().stream()
        .filter(reservation -> reservation.getMember().equals(this))
        .toList();

    if (memberReservations.isEmpty()) {
      System.out.println("No reservation history found for member: " + this.getId());
    } else {
      System.out.println("Reservation history for member: " + this.getId());
      for (BookReservation reservation : memberReservations) {
        reservation.printDetails();
      }
    }
  }

  public void viewNotifications() {
    List<Notification> memberNotifications = NotificationDatabase.getNotifications(this);

    if (memberNotifications.isEmpty()) {
      System.out.println("No notifications found for member: " + this.getId());
    } else {
      System.out.println("Notifications for member: " + this.getId());
      for (Notification notification : memberNotifications) {
        notification.printNotification();
      }
    }
  }

  public void updateInformation(Person person) {
    this.setPerson(person);
    String notificationMessage = "Account updated successfully. "
        + "Name: " + person.getName() + ", Email: " + person.getEmail();
    Notification.sendNotification(this, notificationMessage);
  }

}