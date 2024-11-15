package org.group4.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

/**
 * GenericDAO is a generic interface that defines standard CRUD operations for DAO classes.
 * This interface provides a consistent structure for adding, updating, deleting,
 * and retrieving entities from a data source, allowing for flexibility in the types of
 * entities managed.
 *
 * @param <T> the type of the entity managed by this DAO
 */
public interface GenericDAO<T, ID> {

    /**
     * Adds a new entity to the data source.
     *
     * @param entity the entity to be added
     * @return true if the entity was successfully added, false otherwise
     */
    boolean add(T entity);

    /**
     * Updates an existing entity in the data source.
     *
     * @param entity the entity with updated information
     * @return true if the entity was successfully updated, false otherwise
     */
    boolean update(T entity);

    /**
     * Deletes an entity from the data source.
     *
     * @param id the unique identifier of the entity to be deleted
     * @return true if the entity was successfully deleted, false otherwise
     */
    boolean delete(ID id) throws SQLException;

    Optional<T> getById(ID id) throws SQLException;

    /**
     * Retrieves all entities from the data source.
     *
     * @return a Collection of all entities
     */
    Collection<T> getAll();

}
