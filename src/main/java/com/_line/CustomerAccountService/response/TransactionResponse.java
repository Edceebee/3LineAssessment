package com._line.CustomerAccountService.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionResponse {

    private String narration;

    private double amount;

    private LocalDateTime timestamp;
}
