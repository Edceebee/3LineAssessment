package com._line.CustomerAccountService.service.impl;

import com._line.CustomerAccountService.exceptions.BadRequestException;
import com._line.CustomerAccountService.models.Customer;
import com._line.CustomerAccountService.repository.CustomerRepository;
import com._line.CustomerAccountService.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public Customer getCustomerInfo(Long id) throws BadRequestException {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isEmpty()) {
            throw new BadRequestException("Customer with ID "+id+" does not exist");
        }
        return existingCustomer.get();
    }
}
