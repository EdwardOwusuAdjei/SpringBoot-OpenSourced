package com.cointest.repo;

import com.cointest.model.customer.Customer;
import org.springframework.data.repository.CrudRepository;


public interface CustomerRepository extends CrudRepository<Customer,Long> {
    Customer findByEmail(String email);
    Customer findByUsername(String username);
    Customer findById(String id);
}
