package ua.kogutenko.market.exception;

/**
 * The deleted customer exception.
 *
 * <p>
 * @author Oleksandr Kogutenko
 * @version 0.0.1
 */
public class DeletedException extends Exception {
    /**
     * Instantiates a new Deleted exception.
     *
     * @param s the s
     */
    public DeletedException(String s) {
        super(s);
    }

    public DeletedException(final Long id) {
        super("Customer " + id + " deleted");
    }
}
