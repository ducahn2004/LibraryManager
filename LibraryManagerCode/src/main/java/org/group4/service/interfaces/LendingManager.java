package org.group4.service.interfaces;

import org.group4.model.book.BookItem;
import org.group4.model.enums.BookStatus;
import org.group4.model.user.Member;

public interface LendingManager {

  /**
   * Borrow a book item.
   *
   * @param bookItem the book item to borrow
   * @param member the member borrowing the book item
   * @return {@code true} if the book item was borrowed successfully, {@code false} otherwise
   */
  boolean borrowBookItem(BookItem bookItem, Member member);

  /**
   * Return a book item.
   *
   * @param bookItem the book item to return
   * @param member the member returning the book item
   * @param status the status of the book item
   * @return {@code true} if the book item was returned successfully, {@code false} otherwise
   */
  boolean returnBookItem(BookItem bookItem, Member member, BookStatus status);
}
