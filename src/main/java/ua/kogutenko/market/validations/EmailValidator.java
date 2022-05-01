package ua.kogutenko.market.validations;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The email validator.
 *
 * <p>
 * @author Oleksandr Kogutenko
 * @version 0.0.1
 */
@Component
class EmailValidator implements ConstraintValidator<EmailChecker, String> {
    @Override
    public void initialize(EmailChecker constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("[a-z0-9._]+@[a-z0-9.-]+\\.[a-z]{2,3}") && value != null;
    }
}
