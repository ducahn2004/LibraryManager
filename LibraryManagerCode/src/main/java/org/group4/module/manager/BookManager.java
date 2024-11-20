package org.group4.module.manager;

import org.group4.dao.BookDAO;
import org.group4.dao.FactoryDAO;
import org.group4.module.books.Book;
import org.group4.module.enums.NotificationType;
import org.group4.module.notifications.SystemNotification;

public class BookManager implements GenericManager<Book> {

  /** The Book Data Access Object (DAO). */
  private static final BookDAO bookDAO = FactoryDAO.getBookDAO();

  @Override
  public boolean add(Book book) {
    if (bookDAO.add(book)) {
      SystemNotification.sendNotification(NotificationType.ADD_BOOK_SUCCESS, book.toString());
      return true;
    }
    return false;
  }

  @Override
  public boolean update(Book book) {
    if (bookDAO.update(book)) {
      SystemNotification.sendNotification(NotificationType.UPDATE_BOOK_SUCCESS, book.toString());
      return true;
    }
    return false;
  }

  @Override
  public boolean delete(String id) {
    if (bookDAO.delete(id)) {
      SystemNotification.sendNotification(NotificationType.DELETE_BOOK_SUCCESS, id);
      return true;
    }
    return false;
  }
}
