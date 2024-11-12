package org.group4.base.transactions;

import java.time.LocalDate;
import org.group4.base.books.BookLending;
import org.group4.base.enums.BookStatus;

/**
 * The {@code Fine} class is responsible for calculating and storing the fine amount
 * for overdue or lost books.
 */
public class Fine {
  private double amount;

  public Fine() {
    this.amount = 0;
  }

  public double getAmount() {
    return amount;
  }

  /**
   * Calculates the fine for a book lending based on overdue days or if the book is lost.
   * @param bookLending The book lending information of a member.
   * @return The calculated fine amount.
   */
  public double calculateFine(BookLending bookLending) {
    double bookPrice = bookLending.getBookItem().getPrice();
    int numberOfPages = bookLending.getBookItem().getNumberOfPages();

    if (bookLending.getBookItem().getStatus() == BookStatus.LOST) {
      amount = calculateLostBookFine(bookPrice, numberOfPages);
    } else {
      amount = calculateOverdueFine(bookLending);
    }

    return amount;
  }

  /**
   * Calculates the fine for a lost book based on its price or page count.
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
   * @param bookLending The book lending information.
   * @return The calculated overdue fine.
   */
  private double calculateOverdueFine(BookLending bookLending) {
    LocalDate dueDate = bookLending.getDueDate();
    LocalDate returnDate = bookLending.getReturnDate();
    double fineAmount = 0;

    if (returnDate.isAfter(dueDate)) {
      long daysOverdue = returnDate.toEpochDay() - dueDate.toEpochDay();
      if (daysOverdue < 7) {
        fineAmount = 5000;
      } else if (daysOverdue < 30) {
        fineAmount = 15000;
      } else if (daysOverdue < 180) {
        fineAmount = 25000;
      } else if (daysOverdue < 365) {
        fineAmount = 50000;
      } else {
        fineAmount = calculateLostBookFine(bookLending.getBookItem().getPrice(), bookLending.getBookItem().getNumberOfPages());
      }
    }

    return fineAmount;
  }

  /**
   * Processes the fine payment for a transaction.
   * @param transaction The fine transaction to process.
   */
  public void payFine(FineTransaction transaction) {
    if (transaction.processFinePayment()) {
      System.out.println("Fine paid successfully.");
    } else {
      System.out.println("Fine payment failed.");
    }
  }

}
