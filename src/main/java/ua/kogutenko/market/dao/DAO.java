package ua.kogutenko.market.dao;

import ua.kogutenko.market.exception.CustomerNotFoundException;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

/**
 * The interface DAO.
 * <p>
 *
 * @param <T> the type witch will implement
 * @author Oleksandr Kogutenko
 * @version 0.0.1
 */
public interface DAO<T> extends Serializable {

    /**
     * Gets by id.
     *
     * @param id the id
     * @throws CustomerNotFoundException when we catch EntityNotFoundException
     * @return the optional object
     *
     */
    Optional<T> getById(Long id) throws CustomerNotFoundException;

    /**
     * Gets all.
     *
     * @return the collection of objects
     */
    Collection<T> getAll();

    /**
     * Save object.
     *
     * @param t the saved entity
     * @return the saved entity
     */
    T save(T t);

    /**
     * Update entity.
     *
     * @param t the updated entity
     * @return the updated entity
     */
    T update(T t);

    /**
     * Delete entity.
     *
     * @param t the entity to be deleted
     */
    void delete(T t);
}
