package org.group4.module.manager;

import org.group4.dao.BookItemDAO;
import org.group4.dao.FactoryDAO;
import org.group4.module.books.BookItem;
import org.group4.module.enums.NotificationType;
import org.group4.module.notifications.SystemNotification;

public class BookItemManager implements GenericManager<BookItem> {

  private static final BookItemDAO bookItemDAO = FactoryDAO.getBookItemDAO();

  @Override
  public boolean add(BookItem bookItem) {
    if (bookItemDAO.add(bookItem)) {
      SystemNotification.sendNotification(NotificationType.ADD_BOOK_ITEM_SUCCESS,
          bookItem.toString());
      return true;
    }
    return false;
  }

  @Override
  public boolean update(BookItem bookItem) {
    if (bookItemDAO.update(bookItem)) {
      SystemNotification.sendNotification(NotificationType.UPDATE_BOOK_ITEM_SUCCESS,
          bookItem.toString());
      return true;
    }
    return false;
  }

  @Override
  public boolean delete(String barcode) {
    if (bookItemDAO.delete(barcode)) {
      SystemNotification.sendNotification(NotificationType.DELETE_BOOK_ITEM_SUCCESS,
          barcode);
      return true;
    }
    return false;
  }

}
