package com._line.CustomerAccountService.service.impl;

import com._line.CustomerAccountService.models.Account;
import com._line.CustomerAccountService.models.Transaction;
import com._line.CustomerAccountService.repository.AccountRepository;
import com._line.CustomerAccountService.repository.TransactionRepository;
import com._line.CustomerAccountService.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Creates a transaction for an account with the specified initial credit and updates the account's balance.
     *
     * @param initialCredit The initial credit amount to deposit into the account.
     * @param account The account to which the transaction will be applied.
     */
    @Override
    public void createAndSaveTransaction(double initialCredit, Account account) {
       Transaction newTransaction = Transaction.builder().account(account).amount(initialCredit)
                .timestamp(LocalDateTime.now()).transactionType("CREDIT")
                .narration("Account creation deposit").build();
        transactionRepository.save(newTransaction);

        Optional<Account> justCreatedAccount = accountRepository.findById(account.getId());
        if (justCreatedAccount.isPresent()) {
            Account gottenAccount = justCreatedAccount.get();
            double newBalance = gottenAccount.getBalance() + initialCredit;
            gottenAccount.setBalance(newBalance);
            accountRepository.save(gottenAccount);
        }
    }
}
