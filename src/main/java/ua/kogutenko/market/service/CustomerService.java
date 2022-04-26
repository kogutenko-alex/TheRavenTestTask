package ua.kogutenko.market.service;

import ua.kogutenko.market.exception.DeletedException;
import ua.kogutenko.market.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer save(Customer customer);
    List<Customer> findAll();
    Optional<Customer> findById(Long id) throws DeletedException;
    Customer update(Customer client, Long id) throws DeletedException;
    void delete(Long id);
}
