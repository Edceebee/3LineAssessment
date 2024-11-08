package com._line.CustomerAccountService.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerInfoResponse {
    private String firstName;
    private String surname;
    private double balance;
    private String customerId;
    private List<AccountInfoResponse> customerInfo;

}