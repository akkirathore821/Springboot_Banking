package com.bank.account_service.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.math.BigDecimal;

@Data
public class AccountResponse {
    private String accountNumber;
    private String accountHolder;
    private BigDecimal balance;

    public AccountResponse() {
    }

    public AccountResponse(String accountNumber, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public AccountResponse(String accountNumber, String accountHolder, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountId(String accountNumber) {
        this.accountNumber = accountNumber;
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
                "accountNumber=" + accountNumber +
                ", accountHolder='" + accountHolder + '\'' +
                ", balance=" + balance +
                '}';
    }
}
