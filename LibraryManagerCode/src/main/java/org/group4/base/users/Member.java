// File: org/group4/base/users/Member.java
package org.group4.base.users;

import java.util.List;
import java.time.LocalDate;

import org.group4.database.NotificationDatabase;
import org.group4.base.notifications.EmailNotification;
import org.group4.base.notifications.PhoneNotification;
import org.group4.service.GoogleBooksService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.group4.database.BookLendingDatabase;
import org.group4.database.BookReservationDatabase;
import org.group4.database.MemberDatabase;

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
  //TODO: WRITE A CONSTRUCTOR with 2 parameter: id, and person
  //TODO: WRITE A CONSTRUCTOR with 2 parameter: id, and person -> Lam sao ma tao duoc constructor nay?
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
    List<Member> members = MemberDatabase.getInstance().getItems();
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

public void reserveBookItem(@NotNull BookItem bookItem) throws IllegalStateException {
    String notificationMessage;

    if (bookItem.checkOut()) {
        BookReservation bookReservation = new BookReservation(bookItem, this);
        notificationMessage = bookReservation.processReservation();
    } else {
        notificationMessage = switch (bookItem.getStatus()) {
            case LOST -> throw new IllegalStateException("Book is lost!");
            case LOANED -> {
                BookReservation bookReservation = new BookReservation(bookItem, this);
                yield bookReservation.processReservation();
            }
            case RESERVED -> throw new IllegalStateException("Book is reserved!");
            default -> throw new IllegalStateException("Book is reference only!");
        };
    }

    EmailNotification.sendEmailNotification(this, notificationMessage);
}

  public void renewBookItem(@NotNull BookItem bookItem) throws IllegalStateException {
    BookLending lending = BookLending.fetchLendingDetails(bookItem.getBarcode());
    if (lending != null ) {
      Notification.sendNotification(this, lending.processRenew());
    } else {
      throw new IllegalStateException("Book renewal failed. No active lending found.");
    }
  }

  public void cancelReservation(@NotNull BookItem bookItem) throws IllegalStateException {
    BookReservation reservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
    if (reservation != null) {
      Notification.sendNotification(this, reservation.processCancel());
    } else {
      throw new IllegalStateException("No active reservation found for this book.");
    }
  }

  public void returnBookItem(@NotNull BookItem bookItem) throws IllegalStateException {
    String notificationMessage;

    if (bookItem.getStatus() == BookStatus.LOANED) {
      Fine fine = checkForFine(bookItem.getBarcode());
      if (fine != null) {
        FineTransaction fineTransaction = new FineTransaction(fine);
        fine.payFine(fineTransaction);
        if (!fineTransaction.processFinePayment()) {
          throw new IllegalStateException("Fine payment failed. Cannot return the book.");
        }
      }

      bookItem.setStatus(BookStatus.AVAILABLE);
      bookItem.setDueDate(null);
      this.totalBooksCheckedOut--;

      notificationMessage = "Book returned successfully.";
    } else {
      throw new IllegalStateException("Book is not currently loaned! Cannot return.");
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

  public void lendBookItem(@NotNull BookItem bookItem) throws IllegalStateException {
    String notificationMessage;

    if (this.totalBooksCheckedOut >= MAX_BOOKS_ISSUED_TO_A_USER) {
      throw new IllegalStateException("Exceeded maximum books issue limit!");
    } else {
      BookReservation reservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
      if (reservation != null && reservation.getStatus() == RevervationStatus.COMPLETED) {
        if (reservation.getBookItem().getBarcode().equals(bookItem.getBarcode()) && reservation.getMember()
            .equals(this)) {
          BookLending bookLending = new BookLending(bookItem, this);
          notificationMessage = bookLending.processLending();
        } else {
          throw new IllegalStateException("Book is reserved by another member.");
        }
      } else if (bookItem.checkOut()) {
        BookLending bookLending = new BookLending(bookItem, this);
        notificationMessage = bookLending.processLending();
      } else {
        throw new IllegalStateException("Book is not available for lending.");
      }
    }

    PhoneNotification.sendPhoneNotification(this, notificationMessage);
  }

  public void viewLendingHistory() {
    List<BookLending> memberLendings = BookLendingDatabase.getInstance().getItems().stream()
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
    List<BookReservation> memberReservations = BookReservationDatabase.getInstance().getItems().stream()
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

  public void searchBook(String query) {
    GoogleBooksService googleBooksService = new GoogleBooksService();
    try {
      List<String> bookTitles = googleBooksService.searchBooks(query);
      for (String title : bookTitles) {
        System.out.println(title);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}