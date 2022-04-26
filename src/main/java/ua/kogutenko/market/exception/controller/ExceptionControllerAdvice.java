package ua.kogutenko.market.exception.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.kogutenko.market.exception.CustomerNotFoundException;
import ua.kogutenko.market.exception.DeletedException;
import ua.kogutenko.market.exception.EmailShouldNotBeChangedException;
import ua.kogutenko.market.exception.model.ErrorValidResponse;
import ua.kogutenko.market.exception.model.ErrorExceptionResponse;

import javax.persistence.EntityNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailShouldNotBeChangedException.class)
    public ResponseEntity<ErrorExceptionResponse> handleEntityNotFoundException(EmailShouldNotBeChangedException e) {
        ErrorExceptionResponse response = new ErrorExceptionResponse(
                "Email exception",
                e.getMessage(),
                new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime()),
                HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorExceptionResponse> handleEntityNotFoundException(Exception e) {
        ErrorExceptionResponse response = new ErrorExceptionResponse(
                "Global exception",
                e.getMessage(),
                new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime()),
                HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorExceptionResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        ErrorExceptionResponse response = new ErrorExceptionResponse(
                "Entity not found",
                e.getMessage(),
                new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime()),
                HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorExceptionResponse> handleCustomerNotFoundException(CustomerNotFoundException e) {
        ErrorExceptionResponse response = new ErrorExceptionResponse(
                "Customer not found",
                e.getMessage(),
                new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime()),
                HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DeletedException.class)
    public ResponseEntity<ErrorExceptionResponse> handleDeletedCustomerException(DeletedException e) {
        ErrorExceptionResponse response = new ErrorExceptionResponse(
                "Deleted customer",
                e.getMessage(),
                new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime()),
                HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorValidResponse error = new ErrorValidResponse("Validation Failed", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorExceptionResponse> handleUniqueEmailException(DataIntegrityViolationException e) {
        ErrorExceptionResponse response = new ErrorExceptionResponse(
                "Unique email",
                e.getMessage(),
                new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime()),
                HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
