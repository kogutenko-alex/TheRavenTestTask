package ua.kogutenko.market.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.kogutenko.market.dao.impl.CustomerDAO;
import ua.kogutenko.market.dto.CustomerDTO;
import ua.kogutenko.market.exception.CustomerNotFoundException;
import ua.kogutenko.market.exception.DeletedException;
import ua.kogutenko.market.exception.InvalidRequestException;
import ua.kogutenko.market.service.CustomerService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Customer service.
 * <p>
 * <p>
 * @author Oleksandr Kogutenko
 * @version 0.0.1
 */
@Service
@Log4j2
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;

    /**
     * Autowired a customer dao.
     *
     * @param customerDAO the customer dao
     */
    public CustomerServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }


    @Override
    public CustomerDTO save(CustomerDTO customer) throws InvalidRequestException {
        if (customer == null)
            throw new InvalidRequestException("Customer is null when saving");
        customer.setCreated(new Date().getTime());
        customer.set_active(true);
        return customerDAO.save(customer);
    }

    @Override
    public List<CustomerDTO> findAll() {
        return customerDAO
                .getAll()
                .stream()
                .filter(CustomerDTO::is_active)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO findById(Long id) throws DeletedException, CustomerNotFoundException, InvalidRequestException {
        if (id == null) {
            throw new InvalidRequestException("ID should not be null!!!");
        }
        Optional<CustomerDTO> optionalCustomer = customerDAO.getById(id);
        if (optionalCustomer.isEmpty()) {
            throw new CustomerNotFoundException(id);
        }
        if (optionalCustomer.get().is_active()) {
            return optionalCustomer.get();
        }
        throw new DeletedException("CustomerDTO " + id + " was deleted");
    }

    @Override
    public CustomerDTO update(CustomerDTO updated, Long id)
            throws DeletedException, CustomerNotFoundException,
            InvalidRequestException {
        if (updated == null || id == null) {
            throw new InvalidRequestException("Customer or ID must not be null!");
        }
        Optional<CustomerDTO> optionalRecord = customerDAO.getById(id);
        if (optionalRecord.isEmpty()) {
            throw new CustomerNotFoundException(id);
        }
        CustomerDTO existing = optionalRecord.get();
        if (existing.is_active()) {
            CustomerDTO updatedNew = updateCustomer(updated, existing);
            return customerDAO.update(updatedNew);
        }
        throw new DeletedException(id);
    }

    private CustomerDTO updateCustomer(CustomerDTO updated, CustomerDTO existing) {
        existing.setFull_name(updated.getFull_name() != null ? updated.getFull_name() : existing.getFull_name());
        existing.setPhone(updated.getPhone() != null ? updated.getPhone() : existing.getPhone());
        existing.setUpdated(new Date().getTime());
        return existing;
    }

    @Override
    public void delete(Long id) throws CustomerNotFoundException, InvalidRequestException, DeletedException {
        if (id == null) {
            throw new InvalidRequestException("ID should not be null!!!");
        }
        Optional<CustomerDTO> customer = customerDAO.getById(id);
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException(id);
        }
        CustomerDTO deletedCustomer = customer.get();
        if (!deletedCustomer.is_active()) {
            throw new DeletedException(id);
        }
        deletedCustomer.set_active(false);
        customerDAO.update(deletedCustomer);
    }
}
