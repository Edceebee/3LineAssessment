package com._line.CustomerAccountService.controller;

import com._line.CustomerAccountService.response.JSENDResponse;
import com._line.CustomerAccountService.exceptions.BadRequestException;
import com._line.CustomerAccountService.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestParam String customerId, @RequestParam double initialCredit) throws BadRequestException {
        return ResponseEntity.ok(JSENDResponse.success(accountService.createAccount(customerId, initialCredit), "Current Account Created Successfully"));
    }

}
