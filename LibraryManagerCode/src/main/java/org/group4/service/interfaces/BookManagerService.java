package org.group4.service.interfaces;

import java.sql.SQLException;
import java.util.List;
import org.group4.model.book.Book;
import org.group4.model.book.BookItem;

/**
 * Service interface for managing {@link Book} entities in the library.
 */
public interface BookManagerService {

  /**
   * Adds a book to the library.
   *
   * @param book the book to add
   * @return {@code true} if the book was added successfully, {@code false} otherwise
   */
  boolean add(Book book) throws SQLException;

  /**
   * Updates a book in the library.
   *
   * @param book the book to update
   * @return {@code true} if the book was updated successfully, {@code false} otherwise
   */
  boolean update(Book book) throws SQLException;

  /**
   * Deletes a book from the library.
   *
   * @param id the identifier of the book to delete
   * @return {@code true} if the book was deleted successfully, {@code false} otherwise
   */
  boolean delete(String id) throws SQLException;

  /**
   * Gets all books in the library.
   *
   * @return a collection of all books in the library
   */
  List<Book> getAll() throws SQLException;

  /**
   * Gets all book items for a book with the given ISBN.
   *
   * @param isbn the ISBN of the book
   * @return a collection of all book items for the book
   */
  List<BookItem> getAllBookItems(String isbn);
}
