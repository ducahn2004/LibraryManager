package org.group4.module.manager;

import org.group4.dao.BookDAO;
import org.group4.dao.FactoryDAO;
import org.group4.module.books.Book;

public class BookManager implements GenericManager<Book> {

  private static final BookDAO bookDAO = FactoryDAO.getBookDAO();

  @Override
  public boolean add(Book book) {
    return bookDAO.add(book);
  }

  @Override
  public boolean update(Book book) {
    return bookDAO.update(book);
  }

  @Override
  public boolean delete(String id) {
    return bookDAO.delete(id);
  }

}
