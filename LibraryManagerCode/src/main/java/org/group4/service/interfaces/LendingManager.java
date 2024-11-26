package org.group4.service.interfaces;

import java.sql.SQLException;
import java.util.List;
import org.group4.model.book.BookItem;
import org.group4.model.enums.BookStatus;
import org.group4.model.transaction.BookLending;
import org.group4.model.user.Member;

public interface LendingManager {

  /**
   * Borrow a book item.
   *
   * @param bookItem the book item to borrow
   * @param member the member borrowing the book item
   * @return {@code true} if the book item was borrowed successfully, {@code false} otherwise
   */
  boolean borrowBookItem(BookItem bookItem, Member member) throws SQLException;

  /**
   * Return a book item.
   *
   * @param bookItem the book item to return
   * @param member the member returning the book item
   * @param status the status of the book item
   * @return {@code true} if the book item was returned successfully, {@code false} otherwise
   */
  boolean returnBookItem(BookItem bookItem, Member member, BookStatus status) throws SQLException;

  /**
   * Get all book lendings.
   *
   * @return a list of all book lendings
   */
  List<BookLending> getAll() throws SQLException;

  /**
   * Get all book lendings by member ID.
   *
   * @param memberId the member ID
   * @return a list of all book lendings by the member
   */
  List<BookLending> getByMemberId(String memberId);
}
