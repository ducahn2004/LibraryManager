package org.group4.base.transactions;

import java.sql.SQLException;
import java.time.LocalDate;
import org.group4.base.books.BookItem;
import org.group4.base.enums.BookStatus;
import org.group4.dao.FactoryDAO;

/**
 * The {@code Fine} class is responsible for calculating and storing the fine amount
 * for overdue or lost books.
 */
public class Fine {
  private double amount;

  /**
   * Constructs a new {@code Fine} object with a default fine amount of 0.
   */
  public Fine() {
    this.amount = 0;
  }

  /**
   * Returns the amount of the fine.
   *
   * @return the fine amount.
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Calculates the fine for a book lending based on overdue days or if the book is lost.
   *
   * @param bookLending The book lending information of a member.
   * @return The calculated fine amount.
   * @throws SQLException If there is an issue accessing the book data.
   */
  public double calculateFine(BookLending bookLending) throws SQLException {
    BookItem bookItem = FactoryDAO.getBookItemDAO().getById(bookLending.getBarcode())
        .orElseThrow(() -> new SQLException("Book item not found."));
    double bookPrice = bookItem.getPrice();
    int numberOfPages = bookItem.getNumberOfPages();

    if (bookItem.getStatus() == BookStatus.LOST) {
      amount = calculateLostBookFine(bookPrice, numberOfPages);
    } else {
      amount = calculateOverdueFine(bookLending, bookItem);
    }

    return amount;
  }

  /**
   * Calculates the fine for a lost book based on its price or page count.
   *
   * @param bookPrice The price of the book.
   * @param numberOfPages The number of pages of the book.
   * @return The calculated fine for the lost book.
   */
  private double calculateLostBookFine(double bookPrice, int numberOfPages) {
    if (bookPrice > 0) {
      return Math.min(bookPrice * 3, 300000);
    } else {
      return numberOfPages * 500;
    }
  }

  /**
   * Calculates the fine for overdue books based on the number of days overdue.
   *
   * @param bookLending The book lending information.
   * @param bookItem The book item information.
   * @return The calculated overdue fine.
   * @throws SQLException If there is an issue accessing the book data.
   */
  private double calculateOverdueFine(BookLending bookLending, BookItem bookItem)
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
   *
   * @param daysOverdue The number of days the book is overdue.
   * @param bookItem The book item information.
   * @return The fine amount based on overdue days.
   */
  private double determineFineAmount(long daysOverdue, BookItem bookItem) {
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
