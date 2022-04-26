package ua.kogutenko.market.service.impl;

import org.springframework.stereotype.Service;
import ua.kogutenko.market.exception.DeletedException;
import ua.kogutenko.market.model.Customer;
import ua.kogutenko.market.repository.CustomerRepository;
import ua.kogutenko.market.service.CustomerService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public Customer save(Customer customer) {
        customer.setUpdated(null);
        customer.setCreated(new Date().getTime());
        customer.setIs_active(true);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository
                .findAll()
                .stream()
                .filter(Customer::isIs_active)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> findById(Long id) throws DeletedException {
        Customer customer = customerRepository.getById(id);
        if (customer.isIs_active()) {
            return Optional.of(customer);
        }
        throw new DeletedException("Customer " + id + " was deleted");
    }

    @Override
    public Customer update(Customer customer, Long id) throws DeletedException {
        Customer customerById = customerRepository.getById(id);
        System.out.println(customerById);
        if (customerById.isIs_active()) {
            customer.setUpdated(new Date().getTime());
            customer.setCreated(customerById.getCreated());
            customer.setFull_name(customer.getFull_name() != null ? customer.getFull_name() : customerById.getFull_name());
            customer.setPhone(customer.getPhone() != null ? customer.getPhone() : customerById.getPhone());
            customer.setEmail(customer.getEmail() != null ? customer.getEmail() : customerById.getEmail());
            customer.setIs_active(true);
            customer.setId(customerById.getId());
            //System.out.println(customer);
            return customerRepository.save(customer);
        }
        throw new DeletedException("Customer " + id + " was deleted");
    }

    @Override
    public void delete(Long id) {
        Customer customer = customerRepository.getById(id);
        customer.setIs_active(false);
        customerRepository.save(customer);
    }
}
