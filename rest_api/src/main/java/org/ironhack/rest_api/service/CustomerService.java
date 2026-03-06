package org.ironhack.rest_api.service;

import org.ironhack.rest_api.exception.CustomerNotFound;
import org.ironhack.rest_api.model.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CustomerService {

    private final HashMap<String, Customer> customers = new HashMap<>();

    public List<Customer> getCustomers() {
        return new ArrayList<>(customers.values());
    }

    public Customer addCustomer(String name, String email, int age, String address) {
        Customer customer = new Customer(name, email, age, address);
        customers.put(email, customer);
        return customer;
    }

    public Customer getCustomerByEmail(String email) {
        if (customers.containsKey(email)) {
            return customers.get(email);
        }
        throw new CustomerNotFound("Customer with email " + email + " not found");
    }


}
