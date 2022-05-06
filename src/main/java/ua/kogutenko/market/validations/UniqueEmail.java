package ua.kogutenko.market.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * The annotation for check unique email.
 * <p>
 * @author Oleksandr Kogutenko
 * @version 0.0.1
 */
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {

    String message() default "Email is exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
