package ua.kogutenko.market.exception;

public class EmailShouldNotBeChangedException extends Exception {
    public EmailShouldNotBeChangedException() {
    }

    public EmailShouldNotBeChangedException(String message) {
        super(message);
    }
}
