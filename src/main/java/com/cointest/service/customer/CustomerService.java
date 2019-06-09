package com.cointest.service.customer;

import com.cointest.model.customer.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CustomerService {
    Customer findById(String id);
    Customer save(Customer customer);
    Customer findByUsername(String email);
    boolean isCustomerExist(Customer customer);
    boolean isUsernameExist(Customer customer);
    List<Customer> findAllCustomers();
    Customer findByEmail(String email);
    Customer updateCustomer(Customer customer);
}
