package com._line.CustomerAccountService.controller;

import com._line.CustomerAccountService.response.JSENDResponse;
import com._line.CustomerAccountService.exceptions.BadRequestException;
import com._line.CustomerAccountService.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getCustomerInfo(@PathVariable Long id) throws BadRequestException {
        return ResponseEntity.ok(JSENDResponse.success(customerService.getCustomerInfo(id), "Customer Info fetched successfully"));
    }
}
