package ua.kogutenko.market.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UpdateBanValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UpdateBan {
    String message() default "Email update ban";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
