package ua.kogutenko.market.exception.controller;


import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.kogutenko.market.exception.CustomerNotFoundException;
import ua.kogutenko.market.exception.DeletedException;
import ua.kogutenko.market.exception.model.ErrorValidationResponse;
import ua.kogutenko.market.exception.model.SimpleErrorExceptionResponse;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * The exception controller.
 * <p>
 * Gives json witch has exception info.
 *
 * <p>
 * @author Oleksandr Kogutenko
 * @version 0.0.1
 */
@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleValidEx(ValidationException ex, WebRequest request) {
        SimpleErrorExceptionResponse response = new SimpleErrorExceptionResponse(
                sentenceCase(ex.getClass().getCanonicalName()),
                ex.getLocalizedMessage(),
                new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime()),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DeletedException.class)
    protected ResponseEntity<Object> handleDeletedEx(DeletedException ex, WebRequest request) {
        SimpleErrorExceptionResponse response = new SimpleErrorExceptionResponse(
                sentenceCase(ex.getClass().getCanonicalName()),
                ex.getLocalizedMessage(),
                new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime()),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CustomerNotFoundException.class, EntityNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFoundEx(CustomerNotFoundException ex, WebRequest request) {
        SimpleErrorExceptionResponse response = new SimpleErrorExceptionResponse(
                sentenceCase(ex.getClass().getCanonicalName()),
                ex.getLocalizedMessage(),
                new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime()),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        SimpleErrorExceptionResponse response = new SimpleErrorExceptionResponse(
                sentenceCase(ex.getClass().getCanonicalName()),
                String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                              ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName()
                ),
                new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime()),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(
                new SimpleErrorExceptionResponse(
                        "No Handler Found",
                        ex.getMessage(),
                        new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime()),
                        status
                ), status);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorValidationResponse error = new ErrorValidationResponse("Validation Failed", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable mostSpecificCause = ex.getMostSpecificCause();
        SimpleErrorExceptionResponse errorMessage;
        String exceptionName = mostSpecificCause.getClass().getName();
        String message = mostSpecificCause.getMessage();
        errorMessage = new SimpleErrorExceptionResponse(
                sentenceCase(exceptionName),
                message,
                new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime()),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(errorMessage, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";

        SimpleErrorExceptionResponse apiError =
                new SimpleErrorExceptionResponse(
                        sentenceCase(ex.getClass().getName()),
                        error,
                        new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime()),
                        HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getHttpStatus());
    }

    private String sentenceCase(String text) {
        if (!text.equals("")) {
            text = text.substring(text.lastIndexOf('.') + 1);
            String result = text.replaceAll("([A-Z])", " $1");
            return result.substring(1, 2).toUpperCase() + result.substring(2).toLowerCase();
        }
        return null;
    }


}
