package com._line.CustomerAccountService.service.impl;

import com._line.CustomerAccountService.exceptions.BadRequestException;
import com._line.CustomerAccountService.models.Account;
import com._line.CustomerAccountService.models.Customer;
import com._line.CustomerAccountService.models.enums.AccountType;
import com._line.CustomerAccountService.repository.AccountRepository;
import com._line.CustomerAccountService.repository.CustomerRepository;
import com._line.CustomerAccountService.service.AccountService;
import com._line.CustomerAccountService.service.TransactionService;
import com._line.CustomerAccountService.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    AppUtils utils = new AppUtils();

    /**
     * Creates a new CURRENT account for an existing customer with an optional initial credit amount.
     *
     * @param customerId The ID of the customer for whom the account is being created.
     * @param initialCredit The initial credit amount to deposit into the new account, if any.
     * @return The newly created {@link Account}.
     * @throws BadRequestException if the customer does not exist or already has a CURRENT account.
     */
    @Override
    public Account createAccount(String customerId, double initialCredit) throws BadRequestException {

        // Fetch customer by customer ID
        Optional<Customer> existingCustomer = customerRepository.findByCustomerId(customerId);

        // Check if customer exists
        if (existingCustomer.isEmpty()) {
            throw new BadRequestException("Customer with ID " + customerId + " does not exist");
        }

        // Validate initial credit amount
        if (initialCredit < 0) {
            throw new BadRequestException("Initial credit cannot be less than 0");
        }

        // Check if customer already has a CURRENT account
        List<Account> customerAccounts = existingCustomer.get().getAccounts();
        boolean hasCurrentAccount = customerAccounts.stream()
                .anyMatch(account -> account.getAccountType() == AccountType.CURRENT);

        // If a CURRENT account exists, throw an exception
        if (hasCurrentAccount) {
            throw new BadRequestException("Customer already has a CURRENT account type");
        }

        // Create a new CURRENT account
        Account newCurrentAccount = Account.builder()
                .accountId("ACC_" + existingCustomer.get().getCustomerId())
                .accountType(AccountType.CURRENT)
                .accountNumber(utils.generateRandom10DigitNumber())
                .balance(0)
                .customer(existingCustomer.get())
                .build();

        // Save the new account to the repository
        accountRepository.save(newCurrentAccount);

        // If initial credit is provided, create a transaction for it
        if (initialCredit > 0) {
            transactionService.createAndSaveTransaction(initialCredit, newCurrentAccount);
        }

        return newCurrentAccount;
    }

}
