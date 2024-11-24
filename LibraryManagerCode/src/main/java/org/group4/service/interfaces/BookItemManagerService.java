package org.group4.service.interfaces;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.group4.model.book.BookItem;

/**
 * Interface for managing {@link BookItem} entities in the library.
 */
public interface BookItemManagerService {

  /**
   * Adds a {@link BookItem} to the library.
   *
   * @param bookItem the {@link BookItem} to add
   * @return {@code true} if the {@link BookItem} was added successfully, {@code false} otherwise
   */
  boolean add(BookItem bookItem) throws IOException, SQLException;

  /**
   * Updates a {@link BookItem} in the library.
   *
   * @param bookItem the {@link BookItem} to update
   * @return {@code true} if the {@link BookItem} was updated successfully, {@code false} otherwise
   */
  boolean update(BookItem bookItem) throws SQLException;

  /**
   * Deletes a {@link BookItem} from the library.
   *
   * @param barcode the barcode of the {@link BookItem} to delete
   * @return {@code true} if the {@link BookItem} was deleted successfully, {@code false} otherwise
   */
  boolean delete(String barcode) throws SQLException;

  /**
   * Retrieves all {@link BookItem} entities in the library.
   *
   * @return a list of all {@link BookItem} entities in the library
   */
  List<BookItem> getAll() throws SQLException;
}
