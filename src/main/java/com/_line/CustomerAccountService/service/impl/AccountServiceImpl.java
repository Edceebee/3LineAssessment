package com._line.CustomerAccountService.service.impl;

import com._line.CustomerAccountService.exceptions.BadRequestException;
import com._line.CustomerAccountService.models.Account;
import com._line.CustomerAccountService.models.Customer;
import com._line.CustomerAccountService.models.enums.AccountType;
import com._line.CustomerAccountService.repository.AccountRepository;
import com._line.CustomerAccountService.repository.CustomerRepository;
import com._line.CustomerAccountService.service.AccountService;
import com._line.CustomerAccountService.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    @Override
    public Account createAccount(String customerId, double initialCredit) throws BadRequestException {
        Optional<Customer> existingCustomer = customerRepository.findByCustomerId(customerId);
        if (existingCustomer.isEmpty()) {
            throw new BadRequestException("Customer with ID "+customerId +" does not exist");
        }

        List<Account> customerAccounts = existingCustomer.get().getAccounts();

        boolean hasCurrentAccount = customerAccounts.stream()
                .anyMatch(account -> account.getAccountType() == AccountType.CURRENT);

        if (hasCurrentAccount) {
            throw new BadRequestException("Customer already has a CURRENT account type");
        }

        Account newCurrentAccount = Account.builder()
                .accountId("ACC_" + existingCustomer.get().getCustomerId())
                .accountType(AccountType.CURRENT)
                .balance(0)
                .customer(existingCustomer.get())
                .build();
        accountRepository.save(newCurrentAccount);

        if (initialCredit > 0) {
            transactionService.createAndSaveTransaction(initialCredit, newCurrentAccount);
        }

        return newCurrentAccount;
    }
}
