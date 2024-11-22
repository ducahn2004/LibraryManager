package org.group4.service.transaction;

import java.sql.SQLException;
import java.time.LocalDate;
import org.group4.dao.base.FactoryDAO;
import org.group4.model.book.BookItem;
import org.group4.model.enums.BookStatus;
import org.group4.model.transaction.BookLending;
import org.group4.model.transaction.Fine;

/**
 * Service for calculating fines for overdue or lost books.
 */
public class FineCalculationService {

  /**
   * Calculates the fine for a book lending based on overdue days or if the book is lost.
   *
   * @param fine The fine object containing book lending information.
   * @return The calculated fine amount.
   * @throws SQLException If there is an issue accessing the book data.
   */
  public static double calculateFine(Fine fine) throws SQLException {
    BookLending bookLending = fine.getBookLending();
    BookItem bookItem = FactoryDAO.getBookItemDAO().getById(bookLending.getBookItem().getBarcode())
        .orElseThrow(() -> new SQLException("Book item not found."));
    double bookPrice = bookItem.getPrice();
    int numberOfPages = bookItem.getNumberOfPages();

    double amount;
    if (bookItem.getStatus() == BookStatus.LOST) {
      amount = calculateLostBookFine(bookPrice, numberOfPages);
    } else {
      amount = calculateOverdueFine(bookLending, bookItem);
    }

    fine.setAmount(amount); // Update the fine object
    return amount;
  }

  /**
   * Calculates the fine for a lost book based on its price or page count.
   */
  private static double calculateLostBookFine(double bookPrice, int numberOfPages) {
    if (bookPrice > 0) {
      return Math.min(bookPrice * 3, 300000);
    } else {
      return numberOfPages * 500;
    }
  }

  /**
   * Calculates the fine for overdue books based on the number of days overdue.
   */
  private static double calculateOverdueFine(BookLending bookLending, BookItem bookItem)
      throws SQLException {
    LocalDate dueDate = bookLending.getDueDate();
    LocalDate returnDate = bookLending.getReturnDate().orElseThrow(
        () -> new SQLException("Return date is missing."));

    double fineAmount = 0;
    if (returnDate.isAfter(dueDate)) {
      long daysOverdue = returnDate.toEpochDay() - dueDate.toEpochDay();
      fineAmount = determineFineAmount(daysOverdue, bookItem);
    }

    return fineAmount;
  }

  /**
   * Determines the fine amount based on the days overdue.
   */
  private static double determineFineAmount(long daysOverdue, BookItem bookItem) {
    if (daysOverdue < 7) {
      return 5000;
    } else if (daysOverdue < 30) {
      return 15000;
    } else if (daysOverdue < 180) {
      return 25000;
    } else if (daysOverdue < 365) {
      return 50000;
    } else {
      return calculateLostBookFine(bookItem.getPrice(), bookItem.getNumberOfPages());
    }
  }
}
