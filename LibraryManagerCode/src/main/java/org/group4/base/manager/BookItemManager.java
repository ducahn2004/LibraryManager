package org.group4.base.manager;

import org.group4.base.books.BookItem;
import org.group4.database.BookItemDatabase;

/**
 * Manages operations related to BookItem entities. Implements the Manager interface
 * to perform CRUD operations on the BookItem database.
 */
public class BookItemManager implements Manager<BookItem> {

  /**
   * Adds a new BookItem to the database if it doesn't already exist.
   *
   * @param bookItem The book item to be added.
   * @return true if the book item was successfully added, false if it already exists.
   */
  @Override
  public boolean add(BookItem bookItem) {
    // Check if the book item with the same ISBN already exists in the database.
    boolean exists = BookItemDatabase.getInstance().getItems().stream()
        .anyMatch(existingItem -> existingItem.getISBN().equals(bookItem.getISBN()));

    if (!exists) {
      BookItemDatabase.getInstance().addItem(bookItem);
      return true;
    }
    return false;
  }

  /**
   * Removes a BookItem from the database.
   *
   * @param bookItem The book item to be removed.
   * @return true if the book item was successfully removed.
   */
  @Override
  public boolean remove(BookItem bookItem) {
    // Remove the book item from the database.
    return BookItemDatabase.getInstance().removeItem(bookItem);
  }

  /**
   * Updates an existing BookItem in the database.
   *
   * @param bookItem The book item with updated information.
   * @return true if the book item was successfully updated.
   */
  @Override
  public boolean update(BookItem bookItem) {
    // Update the book item in the database.
    return BookItemDatabase.getInstance().updateItem(bookItem);
  }
}
