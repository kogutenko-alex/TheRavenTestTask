package ua.kogutenko.market.exception;

public class InvalidRequestException extends Exception {
    public InvalidRequestException(final String message) {
        super(message);
    }
}
