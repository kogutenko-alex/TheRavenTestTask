package ua.kogutenko.market.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.kogutenko.market.dto.CustomerDTO;
import ua.kogutenko.market.dto.marked.OnCreated;
import ua.kogutenko.market.dto.marked.OnUpdated;
import ua.kogutenko.market.exception.CustomerNotFoundException;
import ua.kogutenko.market.exception.DeletedException;
import ua.kogutenko.market.exception.EmailShouldNotBeChangedException;
import ua.kogutenko.market.service.impl.CustomerServiceImpl;

import java.util.List;

/**
 * Rest сontroller that processes requests for customers.
 *  <p/>
 * Rest controller get request in JSON format and give some JSON response.
 * Has findAllCustomers, createCustomer, findCustomer, updateCustomer, deleteCustomer methods.
 * <p> 1. {@link CustomerController#findAllCustomers()}                gives all customers from db.
 * <p> 2. {@link CustomerController#createCustomer(CustomerDTO)}       creates new customer with "must have" full name and unique email.
 * <p> 3. {@link CustomerController#findCustomer(Long)}                gives one customer by id.
 * <p> 4. {@link CustomerController#updateCustomer(CustomerDTO, Long)} updates on fields like full name or phone. Email isn't changeable.
 * <p> 5. {@link CustomerController#deleteCustomer(Long)}              delete by id. Only changes the field is_active.
 *
 * @author Oleksandr Kogutenko
 * @version 0.0.1
 */
@Log4j2
@RestController
@RequestMapping("/api")
@Validated
public class CustomerController {
    private final CustomerServiceImpl customerService;

    /**
     * Autowired services.
     *
     * @param customerService the customer service
     */
    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    /**
     * Find all customers list.
     *
     * @return the list of customers.
     */
    @GetMapping("/customers")
    public List<CustomerDTO> findAllCustomers() {
        log.info(String.format("*********** %-20s ***********", "CONTROLLER FIND ALL"));
        return customerService.findAll();
    }

    /**
     * Create customer from valid response body.
     *
     * @param customer the customer
     * @return the response saved customer
     * @throws EmailShouldNotBeChangedException the email should not be changed exception
     * @throws CustomerNotFoundException        the customer not found exception
     */
    @PostMapping("/customers")
    public ResponseEntity<CustomerDTO> createCustomer(@Validated(OnCreated.class) @RequestBody CustomerDTO customer) throws EmailShouldNotBeChangedException, CustomerNotFoundException {
        log.info(String.format("*********** %-20s ***********", "CONTROLLER CREATE"));
        return new ResponseEntity<>(customerService.save(customer), HttpStatus.CREATED);
    }

    /**
     * Find customer by id.
     *
     * @param id the id
     * @return the response one customer
     * @throws CustomerNotFoundException the customer not found exception
     * @throws DeletedException          the deleted exception
     */
    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerDTO> findCustomer(@PathVariable Long id) throws CustomerNotFoundException, DeletedException {
        log.info(String.format("*********** %-20s ***********", "FIND BY ID"));
        CustomerDTO student = customerService.findById(id);
        log.debug(student);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    /**
     * Update customer by response entity.
     *
     * @param customer the customer
     * @param id       the id
     * @return the response updated customer
     * @throws DeletedException                 the deleted exception
     * @throws EmailShouldNotBeChangedException the email should not be changed exception
     * @throws CustomerNotFoundException        the customer not found exception
     */
    @PutMapping("/customers/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@Validated(OnUpdated.class) @RequestBody CustomerDTO customer, @PathVariable Long id) throws DeletedException, EmailShouldNotBeChangedException, CustomerNotFoundException {
        log.info(String.format("*********** %-20s ***********", "CONTROLLER UPDATE " + id));
        customer.setId(id);
        log.info("init customer" + customer);
        return new ResponseEntity<>(
                customerService.update(customer, id),
                HttpStatus.OK);
    }

    /**
     * Delete customer by id.
     *
     * Just mark customer as not active.
     *
     * @param id the id
     * @throws CustomerNotFoundException the customer not found exception
     */
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) throws CustomerNotFoundException {
        log.info(String.format("*********** %-20s ***********", "CONTROLLER DELETE " + id));
        customerService.delete(id);
        return ResponseEntity.ok("customer " + id + " removed successfully.");
    }
}
