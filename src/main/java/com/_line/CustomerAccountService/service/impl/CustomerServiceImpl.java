package com._line.CustomerAccountService.service.impl;

import com._line.CustomerAccountService.exceptions.BadRequestException;
import com._line.CustomerAccountService.models.Account;
import com._line.CustomerAccountService.models.Customer;
import com._line.CustomerAccountService.models.Transaction;
import com._line.CustomerAccountService.repository.CustomerRepository;
import com._line.CustomerAccountService.response.AccountInfoResponse;
import com._line.CustomerAccountService.response.CustomerInfoResponse;
import com._line.CustomerAccountService.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Fetches the information of a customer, including their accounts and the total balance
     * across all accounts by summing the transaction amounts.
     *
     * @param id The ID of the customer whose information is being fetched.
     * @return A {@link CustomerInfoResponse} containing customer details and the total balance.
     * @throws BadRequestException if the customer does not exist.
     */
    @Override
    public CustomerInfoResponse getCustomerInfo(Long id) throws BadRequestException {
        // Fetch customer by ID
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isEmpty()) {
            throw new BadRequestException("Customer with ID " + id + " does not exist");
        }

        Customer foundCustomer = existingCustomer.get();
        List<Account> customerAccounts = foundCustomer.getAccounts();

        // Sum up the balance of all accounts
        double summedBalance = customerAccounts.stream()
                .flatMap(account -> account.getTransactions().stream()) // Stream all transactions for each account
                .mapToDouble(Transaction::getAmount) // Extract the amount from each transaction
                .sum();

        // Map each Account to an AccountInfoResponse
        List<AccountInfoResponse> accountInfoResponses = customerAccounts.stream()
                .map(CustomerInfoResponseMapper::toAccountInfoResponse) // Convert each Account to AccountInfoResponse
                .collect(Collectors.toList());

        // Build CustomerInfoResponse with account info and summed balance
        return CustomerInfoResponse.builder()
                .firstName(foundCustomer.getName())
                .surname(foundCustomer.getSurname())
                .customerId(foundCustomer.getCustomerId())
                .balance(summedBalance)
                .customerInfo(accountInfoResponses) // Set account info list
                .build();
    }
}