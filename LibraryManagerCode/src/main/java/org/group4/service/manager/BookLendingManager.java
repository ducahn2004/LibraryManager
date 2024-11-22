package org.group4.service.manager;

import org.group4.model.books.BookItem;
import org.group4.model.enums.BookStatus;
import org.group4.model.users.Member;

public interface BookLendingManager {

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
