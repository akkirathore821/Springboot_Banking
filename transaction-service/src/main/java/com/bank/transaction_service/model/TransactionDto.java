package com.bank.transaction_service.model;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransactionDto {
    private Long accountId;
    private String accountHolder;
    private BigDecimal amount;
    private String type;
    private BigDecimal balance;

    public TransactionDto(Long accountId, String accountHolder, BigDecimal amount, String type, BigDecimal balance) {
        this.accountId = accountId;
        this.accountHolder = accountHolder;
        this.amount = amount;
        this.type = type;
        this.balance = balance;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
