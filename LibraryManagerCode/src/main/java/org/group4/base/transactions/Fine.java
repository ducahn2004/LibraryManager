package org.group4.base.transactions;

import java.time.LocalDate;
import org.group4.base.books.BookLending;
import org.group4.base.enums.BookStatus;

public class Fine {
  private double amount;

  public Fine() {
    this.amount = 0;
  }

  public double getAmount() {
    return amount;
  }

  private double calculateLostBookFine(double bookPrice, int numberOfPages) {
    return bookPrice > 0 ? Math.min(bookPrice * 3, 300000) : numberOfPages * 500;
  }

  /**
   * Calculate fine amount based on the number of days overdue or if the book is lost.
   * Overdue less than 1 week: fine 5,000 VND/1 book/1 student.
   * Overdue from 1 week to 1 month: fine 15,000 VND/1 book/1 student.
   * Overdue from 1 month to 6 months: fine 25,000 VND/1 book/1 student.
   * Overdue from 6 months to 1 year: fine 50,000 VND/1 book/1 student.
   * Overdue more than 1 year: treat as lost book.
   * Lost book:
   * - Pay 300% of the book price, up to a maximum of 300,000 VND/1 book/1 student.
   * - If the book price is not listed, the fine is 500 VND per page.
   *
   * @param bookLending The book lending of member
   * @return The fine amount
   */
  public double calculateFine(BookLending bookLending) {
    double bookPrice = bookLending.getBookItem().getPrice();
    int numberOfPages = bookLending.getBookItem().getNumberOfPages();
    if (bookLending.getBookItem().getStatus() == BookStatus.LOST) {
      amount = calculateLostBookFine(bookPrice, numberOfPages);
    } else {
      LocalDate dueDate = bookLending.getDueDate();
      LocalDate returnDate = bookLending.getReturnDate();
      if (returnDate.isAfter(dueDate)) {
        long daysOverdue = returnDate.toEpochDay() - dueDate.toEpochDay();
        if (daysOverdue < 7) {
          amount = 5000;
        } else if (daysOverdue < 30) {
          amount = 15000;
        } else if (daysOverdue < 180) {
          amount = 25000;
        } else if (daysOverdue < 365) {
          amount = 50000;
        } else {
          amount = calculateLostBookFine(bookPrice, numberOfPages);
        }
      }
    }
    return amount;
  }

  public void payFine(FineTransaction transaction) {
    if (transaction.processFinePayment()) {
      System.out.println("Fine paid successfully.");
    } else {
      System.out.println("Fine payment failed.");
    }
  }
}