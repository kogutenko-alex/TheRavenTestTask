package ua.kogutenko.market.exception.controller;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.kogutenko.market.exception.model.ErrorValidationResponse;
import ua.kogutenko.market.exception.model.SimpleErrorExceptionResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    /**
     * Handle entity runtime exception.
     *
     * @param e the {@link RuntimeException}
     * @return the response {@link SimpleErrorExceptionResponse}
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<SimpleErrorExceptionResponse> handleEntityRuntimeException(RuntimeException e) {
        SimpleErrorExceptionResponse response = new SimpleErrorExceptionResponse(
                sentenceCase(e.getClass().getCanonicalName()),
                e.getLocalizedMessage(),
                new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime()),
                HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle entity exception.
     *
     * @param e the {@link Exception}
     * @return the response {@link SimpleErrorExceptionResponse}
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<SimpleErrorExceptionResponse> handleEntityException(Exception e) {
        SimpleErrorExceptionResponse response = new SimpleErrorExceptionResponse(
                sentenceCase(e.getClass().getCanonicalName()),
                e.getLocalizedMessage(),
                new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime()),
                HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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

    private String sentenceCase(String text) {
        if (!text.equals("")) {
            text = text.substring(text.lastIndexOf('.') + 1);
            String result = text.replaceAll("([A-Z])", " $1");
            return result.substring(1, 2).toUpperCase() + result.substring(2).toLowerCase();
        }
        return null;
    }


}
