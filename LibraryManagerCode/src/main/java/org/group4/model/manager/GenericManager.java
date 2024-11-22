package org.group4.model.manager;

import java.io.IOException;

public interface GenericManager<T> {

  /**
   * Adds an entity to the library.
   *
   * @param entity the entity to add
   * @return {@code true} if the entity was added successfully, {@code false} otherwise
   */
  boolean add(T entity) throws IOException;

  /**
   * Updates an entity in the library.
   *
   * @param entity the entity to update
   * @return {@code true} if the entity was updated successfully, {@code false} otherwise
   */
  boolean update(T entity);

  /**
   * Deletes an entity from the library.
   *
   * @param id the identifier of the entity to delete
   * @return {@code true} if the entity was deleted successfully, {@code false} otherwise
   */
  boolean delete(String id);
}
