package org.group4.service.book;

import java.sql.SQLException;
import java.util.List;
import org.group4.dao.book.BookDAO;
import org.group4.dao.base.FactoryDAO;
import org.group4.model.book.Book;
import org.group4.model.book.BookItem;
import org.group4.model.enums.NotificationType;
import org.group4.service.interfaces.BookManagerService;
import org.group4.service.notification.SystemNotificationService;

public class BookManagerServiceImpl implements BookManagerService {

  private static final BookDAO bookDAO = FactoryDAO.getBookDAO();
  private final SystemNotificationService systemNotificationService =
      SystemNotificationService.getInstance();


  @Override
  public boolean add(Book book) throws SQLException {
    if (bookDAO.add(book)) {
      systemNotificationService.sendNotification(NotificationType.ADD_BOOK_SUCCESS, book.toString());
      return true;
    }
    return false;
  }

  @Override
  public boolean update(Book book) throws SQLException {
    if (bookDAO.update(book)) {
      systemNotificationService.sendNotification(NotificationType.UPDATE_BOOK_SUCCESS, book.toString());
      return true;
    }
    return false;
  }

  @Override
  public boolean delete(String id) throws SQLException {
    if (bookDAO.delete(id)) {
      systemNotificationService.sendNotification(NotificationType.DELETE_BOOK_SUCCESS, id);
      return true;
    }
    return false;
  }

  @Override
  public List<Book> getAll() throws SQLException {
    return bookDAO.getAll();
  }

  /**
   * Retrieves all book items of a book with the specified ISBN.
   *
   * @param isbn The ISBN of the book.
   * @return A list of book items of the book.
   */
  public List<BookItem> getAllBookItems(String isbn) {
    return bookDAO.getAllBookItems(isbn);
  }
}
