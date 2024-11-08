package com._line.CustomerAccountService.service;

import com._line.CustomerAccountService.exceptions.BadRequestException;
import com._line.CustomerAccountService.models.Customer;

public interface CustomerService {

    Customer getCustomerInfo(Long id) throws BadRequestException;

}
