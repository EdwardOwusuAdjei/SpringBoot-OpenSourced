package com.cointest.service.customer;

import com.cointest.model.customer.Customer;
import com.cointest.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer findById(String id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        Customer save;
        try {
           save = customerRepository.save(customer);
        }
        catch(Exception ex)
        {
            //log..
            System.out.println(ex.getMessage());
            return null;
        }
        return save;
    }

    @Override
    public Customer findByUsername(String username) {
        Customer byUsername;
        try {
           byUsername = customerRepository.findByUsername(username);
           return byUsername;
        }
        catch (Exception ex)
        {
            //when more than one entry is found.shouldnt happen
            return null;
        }
    }


    @Override
    public boolean isCustomerExist(Customer customer) {
        //TODO::is email check okay?
       return customerRepository.findByEmail(customer.getEmail()) != null;
    }
    @Override
    public boolean isUsernameExist(Customer customer) {
        //TODO::is email check okay?
        return customerRepository.findByUsername(customer.getUsername()) != null;
    }


    @Override
    public List<Customer> findAllCustomers() {
        return null;
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
