package org.ironhack.rest_api.controller;

import jakarta.validation.Valid;
import org.ironhack.rest_api.model.Customer;
import org.ironhack.rest_api.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/{email}")
    public Customer getCustomer(@PathVariable String email) {
        return customerService.getCustomerByEmail(email);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        Customer created = customerService.addCustomer(customer.getName(), customer.getEmail(), customer.getAge(), customer.getAddress());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

}
