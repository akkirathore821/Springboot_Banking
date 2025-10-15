package com.bank.account_service.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceUpdateRequest {
    //Todo Validation
    private String accountNumber;
    private BigDecimal amount;
    private String operation; // "DEBIT" or "CREDIT"

}