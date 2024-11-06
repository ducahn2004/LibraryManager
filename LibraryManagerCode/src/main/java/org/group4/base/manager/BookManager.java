package org.group4.base.manager;

import org.group4.base.books.BookItem;
import org.group4.database.BookItemDatabase;

public class BookManager implements Manager<BookItem> {
  @Override
  public boolean add(BookItem bookItem) {
    if (BookItemDatabase.getInstance().getItems().stream()
        .noneMatch(existingMember -> existingMember.getISBN().equals(bookItem.getISBN()))) {
      BookItemDatabase.getInstance().addItem(bookItem);
      return true;
    }
    return false;
  }

  @Override
  public boolean remove(BookItem bookItem) {
    BookItemDatabase.getInstance().removeItem(bookItem);
    return true;
  }

  @Override
  public boolean update(BookItem bookItem) {
    BookItemDatabase.getInstance().updateItem(bookItem);
    return true;
  }

}
