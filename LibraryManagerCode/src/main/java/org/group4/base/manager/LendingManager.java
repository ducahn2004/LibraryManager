package org.group4.base.manager;

import java.time.LocalDate;
import org.group4.base.books.BookItem;
import org.group4.base.books.BookLending;
import org.group4.base.enums.BookStatus;
import org.group4.base.users.Member;
import org.group4.database.MemberDatabase;

public class LendingManager {
  public BookLending borrowBook(BookItem bookItem, Member member) {
    if (bookItem.checkOut() && member.getBookLendings().size() < 5) {
      bookItem.setStatus(BookStatus.LOANED);
      bookItem.setBorrowed(LocalDate.now());
      bookItem.setDueDate(LocalDate.now().plusDays(14));
      MemberDatabase.getInstance().updateItem(member);
      return new BookLending(bookItem, member);
    }
    return null;
  }

  public BookLending returnBook(BookItem bookItem, Member member) {
    BookLending bookLending = member.getBookLendings().stream()
        .filter(lending -> lending.getBookItem().getBarcode().equals(bookItem.getBarcode()))
        .findFirst()
        .orElse(null);
    if (bookLending != null) {
      bookItem.setStatus(BookStatus.AVAILABLE);
      bookItem.setBorrowed(null);
      bookItem.setDueDate(null);
      bookLending.setReturnDate(LocalDate.now());
      MemberDatabase.getInstance().updateItem(member);
      return bookLending;
    }
    return null;
  }

  public BookLending renewBook(BookItem bookItem, Member member) {
    BookLending bookLending = member.getBookLendings().stream()
        .filter(lending -> lending.getBookItem().getBarcode().equals(bookItem.getBarcode()))
        .findFirst()
        .orElse(null);
    if (bookLending != null) {
      LocalDate dueDate = LocalDate.now().plusDays(14);
      bookItem.setDueDate(dueDate);
      bookLending.setDueDate(dueDate);
      MemberDatabase.getInstance().updateItem(member);
      return bookLending;
    }
    return null;
  }

}
