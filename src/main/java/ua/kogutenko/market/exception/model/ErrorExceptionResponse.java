package ua.kogutenko.market.exception.model;

import org.springframework.http.HttpStatus;

public class ErrorExceptionResponse {

    private String message;
    private String date;
    private HttpStatus httpStatus;

    public ErrorExceptionResponse() {
    }

    public ErrorExceptionResponse(String message, String date, HttpStatus httpStatus) {
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

}
