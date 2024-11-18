package org.group4.module.manager;

import org.group4.dao.BookItemDAO;
import org.group4.dao.FactoryDAO;
import org.group4.module.books.BookItem;

public class BookItemManager implements GenericManager<BookItem> {

  private static final BookItemDAO bookItemDAO = FactoryDAO.getBookItemDAO();

  @Override
  public boolean add(BookItem bookItem) {
    return bookItemDAO.add(bookItem);
  }

  @Override
  public boolean update(BookItem bookItem) {
    return bookItemDAO.update(bookItem);
  }

  @Override
  public boolean delete(String barcode) {
    return bookItemDAO.delete(barcode);
  }

}
