package ua.kogutenko.market.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.kogutenko.market.dao.impl.CustomerDAO;
import ua.kogutenko.market.dto.CustomerDTO;
import ua.kogutenko.market.exception.CustomerNotFoundException;
import ua.kogutenko.market.exception.DeletedException;
import ua.kogutenko.market.service.CustomerService;

import java.util.Date;
import java.util.List;
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
    public CustomerDTO save(CustomerDTO customer) {
        log.info(String.format("*********** %-20s ***********", "SERVICE SAVE"));
        if(customer == null)
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
    public CustomerDTO findById(Long id) throws DeletedException, CustomerNotFoundException {
        log.info(String.format("*********** %-20s ***********", "SERVICE FIND BY ID"));
        if (customerDAO.getById(id).isPresent()) {
            CustomerDTO customer = customerDAO.getById(id).get();
            log.debug(customer);
            if (customer.is_active()) {
                log.info("customer found: " + customer);
                return customer;
            }
            log.error("CustomerDTO " + id + " was deleted");
            throw new DeletedException("CustomerDTO " + id + " was deleted");
        }
        throw new CustomerNotFoundException("Customer not found with this id = " + id);
    }

    @Override
    public CustomerDTO update(CustomerDTO updated, Long id) throws DeletedException, CustomerNotFoundException {
        log.info(String.format("*********** %-20s ***********", "SERVICE UPDATE ID " + id));
        if(customerDAO.getById(id).isPresent()) {
            CustomerDTO existing = customerDAO.getById(id).get();
            log.info("existing: " + existing);
            if (existing.is_active()) {
                CustomerDTO updatedNew = updateCustomer(updated, existing);
                log.info("updated:  " + updatedNew);
                return customerDAO.update(updatedNew);
            }
            log.error("CustomerDTO " + id + " was deleted");
            throw new DeletedException("Customer " + id + "deleted");
        } throw new CustomerNotFoundException("Customer not found with this id = " + id);
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
        return updated;
    }

    @Override
    public void delete(Long id) throws CustomerNotFoundException {
        log.info(String.format("*********** %-20s ***********", "SERVICE DELETE BY ID"));
        if(customerDAO.getById(id).isPresent()) {
            CustomerDTO customer = customerDAO.getById(id).get();
            log.info("deleted: " + customer);
            customer.set_active(false);
            customerDAO.update(customer);
            return;
        }
        throw new CustomerNotFoundException("Customer not found with this id = " + id);

    }
}
