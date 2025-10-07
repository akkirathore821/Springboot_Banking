package com.bank.transaction_service.model;

import java.math.BigDecimal;

public class TransactionResponse {
    private String accountNumber;
    private String type; // DEPOSIT, WITHDRAW, TRANSFER
    private BigDecimal amount;
    private String targetAccountNumber; // for transfer
    private BigDecimal balance;

    public TransactionResponse(){
    }

    public TransactionResponse(String accountNumber, String type, BigDecimal amount, String targetAccountNumber, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.targetAccountNumber = targetAccountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTargetAccountNumber() {
        return targetAccountNumber;
    }

    public void setTargetAccountNumber(String targetAccountNumber) {
        this.targetAccountNumber = targetAccountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
