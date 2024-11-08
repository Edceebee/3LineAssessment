package com._line.CustomerAccountService.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountInfoResponse {
    private String accountNumber;
    private String accountType;
    private List<TransactionInfo> transactions;

}
