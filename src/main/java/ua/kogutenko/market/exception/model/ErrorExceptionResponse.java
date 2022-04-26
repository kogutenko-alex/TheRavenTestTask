package ua.kogutenko.market.exception.model;

import org.springframework.http.HttpStatus;

public class ErrorExceptionResponse {

    private String subject;
    private String message;
    private String date;
    private HttpStatus httpStatus;

    public ErrorExceptionResponse() {
    }

    public ErrorExceptionResponse(String subject, String message, String date, HttpStatus httpStatus) {
        this.subject = subject;
        this.message = message;
        this.date = date;
        this.httpStatus = httpStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
