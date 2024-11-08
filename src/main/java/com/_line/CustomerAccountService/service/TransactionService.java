package com._line.CustomerAccountService.service;

import com._line.CustomerAccountService.models.Account;

public interface TransactionService {

    void createAndSaveTransaction(double initialCredit, Account account);
}
