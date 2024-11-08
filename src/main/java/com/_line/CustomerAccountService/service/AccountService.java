package com._line.CustomerAccountService.service;

import com._line.CustomerAccountService.exceptions.BadRequestException;
import com._line.CustomerAccountService.models.Account;

public interface AccountService {

    Account createAccount(String customerId, double initialCredit) throws BadRequestException;
}
