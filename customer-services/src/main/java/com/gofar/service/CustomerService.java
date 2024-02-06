package com.gofar.service;

import com.gofar.dto.CustomerDto;
import com.gofar.entity.Customer;
import com.gofar.exception.CustomerException;
import com.gofar.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public Customer getById(Long id) throws CustomerException {
        Optional<Customer> optionalCustomer = this.customerRepository.findById(id);
        return optionalCustomer.orElseThrow(() -> new CustomerException("Customer not found"));
    }

    public Customer getByEmail(String email) throws CustomerException {
        Optional<Customer> optionalCustomer = this.customerRepository.findByEmail(email);
        return optionalCustomer.orElseThrow(() -> new CustomerException("Customer not found"));
    }

    public List<Customer> getAll() {
        return this.customerRepository.findAll();
    }

    public Page<Customer> getAll(Pageable pageable) {
        return this.customerRepository.findAll(pageable);
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean deleteById(Long id) throws CustomerException{
        if (this.customerRepository.existsById(id)) {
            this.customerRepository.deleteById(id);
            return true;
        }
        throw new CustomerException("Customer not found");
    }

    public void disable(Long id) throws CustomerException {
        Customer customer = this.customerRepository.findById(id).orElseThrow(()-> new CustomerException("Customer not found"));
        customer.disable();
    }

    public void enable(Long id) throws CustomerException {
        Customer customer = this.customerRepository.findById(id).orElseThrow(()-> new CustomerException("Customer not found"));
        customer.enable();
    }

    public Customer create(CustomerDto customerDto) throws CustomerException{
        if (this.customerRepository.existsByEmail(customerDto.getEmail())) {
            throw new CustomerException("Customer with same email already exists");
        }
        return this.customerRepository.save(customerDto.getCustomer());
    }
}
