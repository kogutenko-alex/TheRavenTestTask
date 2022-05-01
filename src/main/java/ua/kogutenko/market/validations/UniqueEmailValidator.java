package ua.kogutenko.market.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.kogutenko.market.repository.CustomerRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The unique email validator.
 * <p>
 * @author Oleksandr Kogutenko
 * @version 0.0.1
 */
@Component
class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private CustomerRepository cr;

    @Override
    public void initialize(UniqueEmail constraint) {
    }

    @Override
    public boolean isValid(String val, ConstraintValidatorContext context) {
        return !cr.existsByEmail(val);
    }
}
