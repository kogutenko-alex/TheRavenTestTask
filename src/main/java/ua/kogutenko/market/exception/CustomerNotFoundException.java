package ua.kogutenko.market.exception;

import javax.persistence.EntityNotFoundException;

/**
 * The customer not found exception.
 *
 * <p>
 * @author Oleksandr Kogutenko
 * @version 0.0.1
 */
public class CustomerNotFoundException extends EntityNotFoundException {
    /**
     * Instantiates a new Customer not found exception.
     *
     * @param message the message
     */
    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(Long id) {
        super("Customer not found with this id = " + id);
    }
}
