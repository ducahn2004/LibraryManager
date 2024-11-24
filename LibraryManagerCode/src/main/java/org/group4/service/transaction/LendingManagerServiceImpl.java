package org.group4.service.transaction;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.group4.dao.base.FactoryDAO;
import org.group4.model.book.BookItem;
import org.group4.model.enums.BookStatus;
import org.group4.model.enums.NotificationType;
import org.group4.model.transaction.BookLending;
import org.group4.model.transaction.Fine;
import org.group4.model.user.Member;
import org.group4.service.interfaces.LendingManagerService;
import org.group4.service.notification.EmailNotificationService;
import org.group4.service.notification.SystemNotificationService;

/**
 * The {@code BookLendingManager} class provides methods to manage the borrowing and returning of
 * book items by library members. It includes operations like borrowing a book, returning a book,
 * and calculating fines for late returns.
 */
public class LendingManagerServiceImpl implements LendingManagerService {

  private static final int MAX_BOOKS_BORROWED = 5;
  private final SystemNotificationService systemNotificationService =
      SystemNotificationService.getInstance();
  private final EmailNotificationService emailNotificationService =
      EmailNotificationService.getInstance();

  @Override
  public boolean borrowBookItem(BookItem bookItem, Member member) throws SQLException {
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

    // Send notifications
    systemNotificationService.sendNotification(NotificationType.BOOK_BORROW_SUCCESS,
        member.getMemberId() + " borrowed " + bookItem.getBarcode());
    emailNotificationService.sendNotification(NotificationType.BOOK_BORROW_SUCCESS, member.getEmail(),
        bookItem.toString());

    // Persist changes to the database
    FactoryDAO.getBookItemDAO().update(bookItem);
    FactoryDAO.getMemberDAO().update(member);
    FactoryDAO.getBookLendingDAO().add(bookLending);
    return true;
  }

  @Override
  public boolean returnBookItem(BookItem bookItem, Member member, BookStatus status)
      throws SQLException {
    // Update book item status
    bookItem.setStatus(status);

    // Update member's total books checked out
    member.setTotalBooksCheckedOut(member.getTotalBooksCheckedOut() - 1);

    // Retrieve the active BookLending record for this book and member
    Optional<BookLending> bookLendingOptional =
        FactoryDAO.getBookLendingDAO().getById(bookItem.getBarcode(), member.getMemberId());

    // Check if the book lending record exists
    if (bookLendingOptional.isEmpty()) {
      throw new SQLException(
          "Book lending record not found for book item: " + bookItem.getBarcode());
    }

    BookLending bookLending = bookLendingOptional.get();

    // Set return date for the book lending record
    bookLending.setReturnDate(LocalDate.now());

    // Send notifications
    systemNotificationService.sendNotification(NotificationType.BOOK_RETURN_SUCCESS,
        bookLending.toString());
    emailNotificationService.sendNotification(NotificationType.BOOK_RETURN_SUCCESS,
        member.getEmail(), bookLending.toString());

    // Calculate and add fines if the book is returned late or lost
    if (bookLending.getDueDate().isBefore(LocalDate.now()) || status == BookStatus.LOST) {
      Fine fine = new Fine(bookLending);
      double fineAmount = FineCalculationService.calculateFine(fine);
      fine.setAmount(fineAmount);
      FactoryDAO.getFineDAO().add(fine);

      // Send fine notification
      emailNotificationService.sendNotification(NotificationType.FINE_TRANSACTION, member.getEmail(),
          fine.toString());
    }

    // Clear the book item's borrowed and due date
    bookItem.setBorrowed(null);
    bookItem.setDueDate(null);

    // Persist changes to the database
    FactoryDAO.getBookItemDAO().update(bookItem);
    FactoryDAO.getMemberDAO().update(member);
    FactoryDAO.getBookLendingDAO().update(bookLending);
    return true;
  }

  @Override
  public List<BookLending> getAll() throws SQLException {
    return FactoryDAO.getBookLendingDAO().getAll();
  }

  @Override
  public List<BookLending> getByMemberId(String memberId) {
    return FactoryDAO.getBookLendingDAO().getByMemberId(memberId);
  }

  @Override
  public List<BookLending> getByBarcode(String barcode) {
    return FactoryDAO.getBookLendingDAO().getByBarcode(barcode);
  }
}
