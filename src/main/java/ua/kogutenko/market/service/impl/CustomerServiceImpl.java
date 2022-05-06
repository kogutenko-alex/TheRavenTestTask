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
 *
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
    public CustomerDTO save(CustomerDTO customer) {
        log.info(String.format("*********** %-20s ***********", "SERVICE SAVE"));
        if (customer == null)
            throw new NullPointerException("Customer is null when saving");
        customer.setCreated(new Date().getTime());
        customer.set_active(true);
        return customerDAO.save(customer);
    }

    @Override
    public List<CustomerDTO> findAll() {
        log.info(String.format("*********** %-20s ***********", "SERVICE FIND ALL"));
        return customerDAO
                .getAll()
                .stream()
                .filter(CustomerDTO::is_active)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> findById(Long id) throws DeletedException, CustomerNotFoundException, InvalidRequestException {
        log.info(String.format("*********** %-20s ***********", "SERVICE FIND BY ID " + id));
        if (id == null) {
            throw new InvalidRequestException("ID should not be null!!!");
        }
        Optional<CustomerDTO> optionalCustomer = customerDAO.getById(id);
        if (optionalCustomer.isEmpty()) {
            throw new CustomerNotFoundException(id);
        }
        if (optionalCustomer.get().is_active()) {
            log.debug("customer found: " + optionalCustomer.get());
            return optionalCustomer;
        }
        log.error("CustomerDTO " + id + " was deleted");
        throw new DeletedException("CustomerDTO " + id + " was deleted");
    }

    @Override
    public CustomerDTO update(CustomerDTO updated, Long id)
            throws DeletedException, CustomerNotFoundException,
            InvalidRequestException {
        log.info(String.format("*********** %-20s ***********", "SERVICE UPDATE ID " + id));
        if (updated == null || id == null) {
            throw new InvalidRequestException("PatientRecord or ID must not be null!");
        }
        Optional<CustomerDTO> optionalRecord = customerDAO.getById(id);
        if (optionalRecord.isEmpty()) {
            throw new CustomerNotFoundException(id);
        }
        CustomerDTO existing = optionalRecord.get();
        log.debug("existing: " + existing);
        if (existing.is_active()) {
            CustomerDTO updatedNew = updateCustomer(updated, existing);
            log.debug("updated:  " + updatedNew);
            return customerDAO.update(updatedNew);
        }
        log.error("CustomerDTO " + id + " was deleted");
        throw new DeletedException("Customer " + id + "deleted");
    }

    private CustomerDTO updateCustomer(CustomerDTO updated, CustomerDTO existing) {
        updated.setUpdated(new Date().getTime());
        updated.setCreated(existing.getCreated());
        updated.setFull_name(updated.getFull_name() != null ? updated.getFull_name() : existing.getFull_name());
        updated.setPhone(updated.getPhone() != null ? updated.getPhone() : existing.getPhone());
        updated.setEmail(existing.getEmail());
        updated.set_active(true);
        updated.setUpdated(new Date().getTime());
        updated.setCreated(existing.getCreated());
//        updated.setId(existing.getId());
        return updated;
    }

    @Override
    public void delete(Long id) throws CustomerNotFoundException {
        log.info(String.format("*********** %-20s ***********", "SERVICE DELETE BY ID"));
        CustomerDTO customer = customerDAO.getById(id).get();
        log.info("deleted: " + customer);
        customer.set_active(false);
        customerDAO.update(customer);
    }

    @Override
    public boolean existsById(final Long id) {
        return customerDAO.existsById(id);
    }
}
