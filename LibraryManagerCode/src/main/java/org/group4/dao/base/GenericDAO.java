package org.group4.dao.base;

import java.util.Collection;
import java.util.Optional;

/**
 * A generic DAO interface providing common CRUD operations.
 *
 * @param <T>  the type of the entity
 * @param <ID> the type of the entity's identifier
 */
public interface GenericDAO<T, ID> {

    /**
     * Adds a new entity to the database.
     *
     * @param entity the entity to add
     * @return true if the operation is successful, otherwise false
     */
    default boolean add(T entity) {
        throw new UnsupportedOperationException("Add operation is not supported");
    }

    /**
     * Updates an existing entity in the database.
     *
     * @param entity the entity to update
     * @return true if the operation is successful, otherwise false
     */
    default boolean update(T entity) {
        throw new UnsupportedOperationException("Update operation is not supported");
    }

    /**
     * Deletes an entity by its ID from the database.
     *
     * @param id the identifier of the entity to delete
     * @return true if the operation is successful, otherwise false
     */
    default boolean delete(ID id) {
        throw new UnsupportedOperationException("Delete operation is not supported");
    }

    /**
     * Retrieves an entity by its ID from the database.
     *
     * @param id the identifier of the entity to retrieve
     * @return an {@code Optional} containing the entity if found, or empty otherwise
     */
    default Optional<T> getById(ID id) {
        throw new UnsupportedOperationException("GetById operation is not supported");
    }

    /**
     * Retrieves all entities from the database.
     *
     * @return a collection of all entities
     */
    default Collection<T> getAll() {
        throw new UnsupportedOperationException("GetAll operation is not supported");
    }
}
