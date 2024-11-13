package org.group4.dao;

import java.util.List;
import org.group4.base.books.BookItem;

public class BookItemDAO implements GenericDAO<BookItem> {

  private BookItemDAO() {
  }

  @Override
  public void add(BookItem item) {
    // TODO: Implement this method
  }

  @Override
  public void remove(BookItem item) {
    // TODO: Implement this method
  }

  @Override
  public void update(BookItem item) {
    // TODO: Implement this method
  }

  @Override
  public BookItem get(BookItem item) {
    // TODO: Implement this method
    return null;
  }

  @Override
  public List<BookItem> getAll() {
    // TODO: Implement this method
    return List.of();
  }
}
