package org.group4.model.transaction;

/**
 * Represents a fine that is incurred by a user for returning a book late.
 * <p>Each fine is associated with a book lending and has an amount that is owed by the user.</p>
 */
public class Fine {

  private final BookLending bookLending;
  private double amount;

  /**
   * Constructs a new {@code Fine} object with a default fine amount of 0.
   *
   * @param bookLending The book lending information.
   */
  public Fine(BookLending bookLending) {
    this.bookLending = bookLending;
    this.amount = 0;
  }

  /**
   * Constructs a new {@code Fine} object with a specified fine amount.
   *
   * @param bookLending The book lending information.
   * @param amount      The fine amount.
   */
  public Fine(BookLending bookLending, double amount) {
    this.bookLending = bookLending;
    this.amount = amount;
  }

  /**
   * Returns the amount of the fine.
   *
   * @return the fine amount
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Sets the amount of the fine.
   *
   * @param amount the new fine amount
   */
  public void setAmount(double amount) {
    this.amount = amount;
  }

  /**
   * Returns the book lending associated with the fine.
   *
   * @return the book lending
   */
  public BookLending getBookLending() {
    return bookLending;
  }

  @Override
  public String toString() {
    return bookLending.toString() + ", \n" + "fine = " + amount;
  }
}
