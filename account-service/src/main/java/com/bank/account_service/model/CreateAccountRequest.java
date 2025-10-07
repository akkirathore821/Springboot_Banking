package com.bank.account_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Random;

@Data
public class CreateAccountRequest {
    //Todo Validation
    private String accountNumber;
    private String accountHolder;

    public CreateAccountRequest() {
        this.accountNumber = generateAccountNumber();
    }

    public CreateAccountRequest(String accountHolder) {
        this.accountHolder = accountHolder;

    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    private String generateAccountNumber() {
        Random random = new Random();
        int number = 100000 + random.nextInt(900000);
        return "ACC" + number;
    }
}
