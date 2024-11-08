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

    /**
     * Retrieves information for a specific customer by their ID.
     *
     * @param id The ID of the customer whose information is being retrieved.
     * @return A {@link ResponseEntity} containing a {@link JSENDResponse} with the customer details
     *         and a success message.
     * @throws BadRequestException if the provided customer ID is invalid or if an error occurs while fetching the customer information.
     */
    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getCustomerInfo(@PathVariable Long id) throws BadRequestException {
        return ResponseEntity.ok(JSENDResponse.success(customerService.getCustomerInfo(id), "Customer Info fetched successfully"));
    }
}
