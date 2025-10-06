package com.bank.account_service.model;

import java.math.BigDecimal;

public class BalanceUpdateRequest {
    private Long accountId;
    private BigDecimal amount;
    private String operation; // "DEBIT" or "CREDIT"

    public BalanceUpdateRequest(Long accountId, BigDecimal amount, String operation) {
        this.accountId = accountId;
        this.amount = amount;
        this.operation = operation;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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