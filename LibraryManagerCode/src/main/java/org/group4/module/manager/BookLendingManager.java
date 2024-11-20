package org.group4.module.manager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import org.group4.dao.FactoryDAO;
import org.group4.module.books.BookItem;
import org.group4.module.enums.BookStatus;
import org.group4.module.enums.NotificationType;
import org.group4.module.notifications.EmailNotification;
import org.group4.module.notifications.SystemNotification;
import org.group4.module.transactions.BookLending;
import org.group4.module.transactions.Fine;
import org.group4.module.users.Member;

/**
 * The {@code BookLendingManager} class provides methods to manage the borrowing and returning of
 * book items by library members. It includes operations like borrowing a book, returning a book,
 * and calculating fines for late returns.
 */
public class BookLendingManager {

  private static final int MAX_BOOKS_BORROWED = 5;

  /**
   * Processes the borrowing of a book item by a member, updating the book's and member's status.
   *
   * @param bookItem The book item being borrowed
   * @param member   The member borrowing the book
   * @return true if the book was successfully borrowed
   * @throws Exception If there is a database error while updating the records
   *                   If email notification fails to send
   */
  public static boolean borrowBookItem(BookItem bookItem, Member member) throws Exception {
    if (member.getTotalBooksCheckedOut() >= MAX_BOOKS_BORROWED || !bookItem.checkOut()) {
      return false; // Member cannot borrow more than the allowed limit.
    }

    // Create a new BookLending transaction
    BookLending bookLending = new BookLending(bookItem, member);

    // Update book item status and due date
    bookItem.setStatus(BookStatus.LOANED);
    bookItem.setBorrowed(bookLending.getLendingDate());
    bookItem.setDueDate(bookLending.getDueDate());

    // Update member's total books checked out
    member.setTotalBooksCheckedOut(member.getTotalBooksCheckedOut() + 1);

    // Persist changes to the database
    FactoryDAO.getBookItemDAO().update(bookItem);
    FactoryDAO.getMemberDAO().update(member);
    FactoryDAO.getBookLendingDAO().add(bookLending);

    // Send notifications
    SystemNotification.sendNotification(NotificationType.BOOK_BORROW_SUCCESS,
        member.getMemberId() + " borrowed " + bookItem.getBarcode());
    EmailNotification.sendNotification(NotificationType.BOOK_BORROW_SUCCESS, member.getEmail(),
        bookItem.toString());
    return true;
  }

  /**
   * Processes the return of a book item by a member, updating the book's and member's status.
   *
   * @param bookItem The book item being returned
   * @param member   The member returning the book
   * @param status   The new status of the book item
   * @return true if the book was successfully returned
   * @throws Exception If there is a database error while updating the records
   *                   If email notification fails to send
   */
  public static boolean returnBookItem(BookItem bookItem, Member member, BookStatus status)
      throws Exception {
    // Update book item status
    bookItem.setStatus(status);
    bookItem.setBorrowed(null);
    bookItem.setDueDate(null);

    // Update member's total books checked out
    member.setTotalBooksCheckedOut(member.getTotalBooksCheckedOut() - 1);

    // Retrieve the active BookLending record for this book and member
    Optional<BookLending> bookLendingOptional =
        FactoryDAO.getBookLendingDAO().getById(bookItem.getBarcode(), member.getMemberId());

    if (bookLendingOptional.isEmpty()) {
      throw new SQLException(
          "Book lending record not found for book item: " + bookItem.getBarcode());
    }

    BookLending bookLending = bookLendingOptional.get();

    // Set return date for the book lending record
    bookLending.setReturnDate(LocalDate.now());

    // Persist changes to the database
    FactoryDAO.getBookItemDAO().update(bookItem);
    FactoryDAO.getMemberDAO().update(member);
    FactoryDAO.getBookLendingDAO().update(bookLending);

    // Send notifications
    SystemNotification.sendNotification(NotificationType.BOOK_RETURN_SUCCESS,
        member.getMemberId() + " returned " + bookItem.getBarcode());
    EmailNotification.sendNotification(NotificationType.BOOK_RETURN_SUCCESS, member.getEmail(),
        bookItem.toString());

    // Check if the book was returned late and calculate fine if necessary
    if (bookLending.getDueDate().isBefore(LocalDate.now()) || status == BookStatus.LOST) {
      Fine fine = new Fine(bookLending);
      fine.calculateFine();
      FactoryDAO.getFineDAO().add(fine);
    }

    return true;
  }

}
