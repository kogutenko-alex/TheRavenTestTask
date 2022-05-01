package ua.kogutenko.market.exception.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * The simple error exception response.
 * <p>
 * Gives the subject, message, date and http status of exception.
 * <p>
 * @author Oleksandr Kogutenko
 * @version 0.0.1
 */
@Setter
@Getter
public class SimpleErrorExceptionResponse {

    private String subject;
    private String message;
    private String date;
    private HttpStatus httpStatus;

    /**
     * Instantiates a new Simple error exception response.
     *
     * @param subject    the subject
     * @param message    the message
     * @param date       the date
     * @param httpStatus the http status
     */
    public SimpleErrorExceptionResponse(String subject, String message, String date, HttpStatus httpStatus) {
        this.subject = subject;
        this.message = message;
        this.date = date;
        this.httpStatus = httpStatus;
    }
}
