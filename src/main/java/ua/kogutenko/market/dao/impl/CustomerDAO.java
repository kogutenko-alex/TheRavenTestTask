package ua.kogutenko.market.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.kogutenko.market.dao.DAO;
import ua.kogutenko.market.dto.CustomerDTO;
import ua.kogutenko.market.exception.CustomerNotFoundException;
import ua.kogutenko.market.repository.CustomerRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Optional;

/**
 * DAO implements {@link DAO} for {@link CustomerDTO}
 * <p>
 * @author Oleksandr Kogutenko
 * @version 0.0.1
 */
@Component
@AllArgsConstructor
public class CustomerDAO implements DAO<CustomerDTO> {

    private final CustomerRepository customerRepository;

    @Override
    public Optional<CustomerDTO> getById(Long id) throws CustomerNotFoundException {
        try {
            return Optional.ofNullable(customerRepository.findById(id)
                                               .orElseThrow(EntityNotFoundException::new));
        } catch (EntityNotFoundException e) {
            throw new CustomerNotFoundException(id);
        }
    }

    @Override
    public Collection<CustomerDTO> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public CustomerDTO save(CustomerDTO customer) {
        return customerRepository.save(customer);
    }

    @Override
    public CustomerDTO update(CustomerDTO customer) {
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public void delete(CustomerDTO customer) {
        customerRepository.delete(customer);
    }
}
