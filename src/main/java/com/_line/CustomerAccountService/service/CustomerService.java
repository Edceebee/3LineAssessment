package com._line.CustomerAccountService.service;

import com._line.CustomerAccountService.exceptions.BadRequestException;
import com._line.CustomerAccountService.response.CustomerInfoResponse;

public interface CustomerService {

    CustomerInfoResponse getCustomerInfo(Long id) throws BadRequestException;

}
