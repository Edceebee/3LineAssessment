package com._line.CustomerAccountService.service.impl;

import com._line.CustomerAccountService.models.Account;
import com._line.CustomerAccountService.models.Transaction;
import com._line.CustomerAccountService.response.AccountInfoResponse;
import com._line.CustomerAccountService.response.TransactionInfo;

import java.util.List;
import java.util.stream.Collectors;

public class AccountMapper {

    // Method to convert Transaction to TransactionInfo
    public static TransactionInfo toTransactionInfo(Transaction transaction) {
        return TransactionInfo.builder()
                .narration(transaction.getNarration())
                .amount(transaction.getAmount())
                .transactionDate(transaction.getTimestamp())
                .build();
    }

    // Method to convert Account to AccountInfoResponse
    public static AccountInfoResponse toAccountInfoResponse(Account account) {
        // Map transactions to TransactionInfo objects
        List<TransactionInfo> transactionInfoList = account.getTransactions()
                .stream()
                .map(AccountMapper::toTransactionInfo)
                .collect(Collectors.toList());

        // Build and return AccountInfoResponse
        return AccountInfoResponse.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType().name())
                .transactions(transactionInfoList)
                .build();
    }
}

