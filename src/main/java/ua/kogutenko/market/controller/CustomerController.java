package ua.kogutenko.market.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.kogutenko.market.exception.CustomerNotFoundException;
import ua.kogutenko.market.exception.DeletedException;
import ua.kogutenko.market.exception.EmailShouldNotBeChangedException;
import ua.kogutenko.market.model.Customer;
import ua.kogutenko.market.model.marked.OnCreated;
import ua.kogutenko.market.model.marked.OnUpdated;
import ua.kogutenko.market.service.impl.CustomerServiceImpl;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerController {
    private final CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public List<Customer> findAllCustomers() {
        return customerService.findAll();
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@Validated(OnCreated.class) @RequestBody Customer customer) {
        Customer savedStudent = customerService.save(customer, false);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @GetMapping("/customers/{id}")
    public Customer findCustomer(@PathVariable Long id) throws CustomerNotFoundException, DeletedException {
        Optional<Customer> student = customerService.findById(id);
        if (!student.isPresent())
            throw new CustomerNotFoundException("id-" + id);
        return student.get();
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@Validated(OnUpdated.class) @Valid @RequestBody Customer customer, @PathVariable Long id) throws DeletedException, EmailShouldNotBeChangedException {
        ControllerUtil util  = new ControllerUtil(customerService);
        customer = util.checker(customer, id);
        return new ResponseEntity<>(
                customerService.update(
                        customer, id),
                HttpStatus.OK);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
    }
}
