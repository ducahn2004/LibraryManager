package org.group4.service.book;

import java.util.List;
import org.group4.dao.book.BookDAO;
import org.group4.dao.base.FactoryDAO;
import org.group4.model.book.Book;
import org.group4.model.enums.NotificationType;
import org.group4.service.interfaces.GenericManagerService;
import org.group4.service.notification.SystemNotificationService;

public class BookManagerService implements GenericManagerService<Book> {

  private static final BookDAO bookDAO = FactoryDAO.getBookDAO();
  private final SystemNotificationService systemNotificationService =
      SystemNotificationService.getInstance();


  @Override
  public boolean add(Book book) {
    if (bookDAO.add(book)) {
      systemNotificationService.sendNotification(NotificationType.ADD_BOOK_SUCCESS, book.toString());
      return true;
    }
    return false;
  }

  @Override
  public boolean update(Book book) {
    if (bookDAO.update(book)) {
      systemNotificationService.sendNotification(NotificationType.UPDATE_BOOK_SUCCESS, book.toString());
      return true;
    }
    return false;
  }

  @Override
  public boolean delete(String id) {
    if (bookDAO.delete(id)) {
      systemNotificationService.sendNotification(NotificationType.DELETE_BOOK_SUCCESS, id);
      return true;
    }
    return false;
  }

  @Override
  public List<Book> getAll() {
    return bookDAO.getAll();
  }
}
