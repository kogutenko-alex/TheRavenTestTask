package ua.kogutenko.market.exception;

/**
 * The customer not found exception.
 *
 * <p>
 * @author Oleksandr Kogutenko
 * @version 0.0.1
 */
public class CustomerNotFoundException extends Exception {
    /**
     * Instantiates a new Customer not found exception.
     *
     * @param message the message
     */
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
