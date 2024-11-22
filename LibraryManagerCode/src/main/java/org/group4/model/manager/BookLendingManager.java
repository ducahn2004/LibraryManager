package org.group4.model.manager;

import org.group4.model.books.BookItem;
import org.group4.model.enums.BookStatus;
import org.group4.model.users.Member;

public interface BookLendingManager {

  boolean borrowBookItem(BookItem bookItem, Member member);

  boolean returnBookItem(BookItem bookItem, Member member, BookStatus status);

}
