package com._line.CustomerAccountService.service.impl;

import com._line.CustomerAccountService.exceptions.BadRequestException;
import com._line.CustomerAccountService.models.Account;
import com._line.CustomerAccountService.models.Customer;
import com._line.CustomerAccountService.repository.CustomerRepository;
import com._line.CustomerAccountService.response.CustomerInfoResponse;
import com._line.CustomerAccountService.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerInfoResponse getCustomerInfo(Long id) throws BadRequestException {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isEmpty()) {
            throw new BadRequestException("Customer with ID "+id+" does not exist");
        }
        Customer foundCustomer = existingCustomer.get();
        List<Account> customerAccounts = foundCustomer.getAccounts();

        double summedBalance = customerAccounts.stream()
                .mapToDouble(Account::getBalance).sum();

        CustomerInfoResponse customerInfoResponse = CustomerInfoResponse.builder()
                .firstName(foundCustomer.getName()).surname(foundCustomer.getSurname())
                .balance(summedBalance).transactionResponseList(customerAccounts)
                .build();

        return customerInfoResponse;
    }
}
