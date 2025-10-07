package com.bank.transaction_service.model;

import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public class BalanceUpdateRequest {
    private String accountNumber;
    private BigDecimal amount;
    private String operation; // "DEBIT" or "CREDIT"

    public BalanceUpdateRequest(){}

    public BalanceUpdateRequest(String accountNumber, BigDecimal amount, String operation) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.operation = operation;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}