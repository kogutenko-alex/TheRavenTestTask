package ua.kogutenko.market.service;

import ua.kogutenko.market.exception.CustomerNotFoundException;
import ua.kogutenko.market.exception.DeletedException;
import ua.kogutenko.market.dto.CustomerDTO;

import java.util.List;

/**
 * The interface of customer service.
 *
 * <p>
 * @author Oleksandr Kogutenko
 * @version 0.0.1
 */
public interface CustomerService {
    /**
     * Save customer.
     *
     * @param customer the customer
     * @return the customer
     */
    CustomerDTO save(CustomerDTO customer);

    /**
     * Find all customers.
     *
     * @return the list of customers
     */
    List<CustomerDTO> findAll();

    /**
     * Find by id customer.
     *
     * @param id the id
     * @return the customer
     * @throws DeletedException          the deleted exception
     * @throws CustomerNotFoundException the customer not found exception
     */
    CustomerDTO findById(Long id) throws DeletedException, CustomerNotFoundException;

    /**
     * Update customer.
     *
     * @param client the client
     * @param id     the id
     * @return the updated customer
     * @throws DeletedException          the deleted exception
     * @throws CustomerNotFoundException the customer not found exception
     */
    CustomerDTO update(CustomerDTO client, Long id) throws DeletedException, CustomerNotFoundException;

    /**
     * Delete by id.
     *
     * @param id the id
     * @throws CustomerNotFoundException the customer not found exception
     */
    void delete(Long id) throws CustomerNotFoundException;
}
