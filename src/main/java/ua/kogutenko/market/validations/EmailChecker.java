package ua.kogutenko.market.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotation for email check.
 *
 * <p>
 * @author Oleksandr Kogutenko
 * @version 0.0.1
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
@UniqueEmail
@Constraint(validatedBy = EmailValidator.class)
public @interface EmailChecker {

    String message() default "Pleas write email correctly (some@some.some)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
