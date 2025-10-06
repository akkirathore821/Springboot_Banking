package com.bank.account_service.model;

import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;

public class AccountDto {
    private Long accountId;
    @NotNull(message = "Amount cannot be null")
    private String accountHolder;
    private BigDecimal balance;

    public AccountDto() {
    }

    public AccountDto(Long accountId,BigDecimal balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public AccountDto(Long accountId, String accountHolder, BigDecimal balance) {
        this.accountId = accountId;
        this.accountHolder = accountHolder;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "accountId=" + accountId +
                ", accountHolder='" + accountHolder + '\'' +
                ", balance=" + balance +
                '}';
    }
}
