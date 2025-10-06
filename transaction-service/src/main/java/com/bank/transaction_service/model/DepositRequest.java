package com.bank.transaction_service.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class DepositRequest {

    @NotNull(message = "Account ID cannot be null")
    private Long accountId;

    @NotNull(message = "Amount cannot be null")
    @Min(value = 1, message = "Amount must be greater than 0")
    private BigDecimal amount;

    public @NotNull(message = "Account ID cannot be null") Long getAccountId() {
        return accountId;
    }

    public void setAccountId(@NotNull(message = "Account ID cannot be null") Long accountId) {
        this.accountId = accountId;
    }

    public @NotNull(message = "Amount cannot be null") @Min(value = 1, message = "Amount must be greater than 0") BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(@NotNull(message = "Amount cannot be null") @Min(value = 1, message = "Amount must be greater than 0") BigDecimal amount) {
        this.amount = amount;
    }
}
