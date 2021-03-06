package ua.kogutenko.market.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.kogutenko.market.dto.CustomerDTO;
import ua.kogutenko.market.repository.CustomerRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired CustomerRepository customerRepository;

    @Override
    public boolean isValid(final String email, ConstraintValidatorContext context) {
        return !customerRepository.existsByEmail(email);
    }
}
