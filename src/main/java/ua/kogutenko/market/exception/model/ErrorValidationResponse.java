package ua.kogutenko.market.exception.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The error validation response.
 * <p>
 * Gives the subject and full validation details.
 * <p>
 * @author Oleksandr Kogutenko
 * @version 0.0.1
 */
@XmlRootElement(name = "error")
@Setter
@Getter
public class ErrorValidationResponse {
    //General error message about nature of error
    private String message;

    //Specific errors in API request processing
    private List<String> details;

    /**
     * Instantiates a new Error validation response.
     *
     * @param message the message
     * @param details the details
     */
    public ErrorValidationResponse(String message, List<String> details) {
        super();
        this.message = message;
        this.details = details;
    }

}
