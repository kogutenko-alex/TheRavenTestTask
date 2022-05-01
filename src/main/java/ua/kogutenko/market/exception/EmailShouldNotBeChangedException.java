package ua.kogutenko.market.exception;

/**
 * The email should not be changed exception.
 *
 * <p>
 * @author Oleksandr Kogutenko
 * @version 0.0.1
 */
public class EmailShouldNotBeChangedException extends Exception {
    /**
     * Instantiates a new Email should not be changed exception.
     *
     * @param message the message
     */
    public EmailShouldNotBeChangedException(String message) {
        super(message);
    }
}
