package ua.kogutenko.market.validations;

import org.springframework.beans.factory.annotation.Autowired;
import ua.kogutenko.market.repository.CustomerRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UpdateBanValidator implements ConstraintValidator<UpdateBan, String> {
    @Autowired CustomerRepository customerRepository;

    @Override
    public boolean isValid(final String email, final ConstraintValidatorContext context) {
        return customerRepository.existsByEmail(email);
    }
}
