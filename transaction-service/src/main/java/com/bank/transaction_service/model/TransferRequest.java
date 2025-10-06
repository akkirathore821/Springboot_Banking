package com.bank.transaction_service.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransferRequest {

    @NotNull(message = "From Account ID cannot be null")
    private Long fromAccountId;

    @NotNull(message = "To Account ID cannot be null")
    private Long toAccountId;

    @NotNull(message = "Amount cannot be null")
    @Min(value = 1, message = "Amount must be greater than 0")
    private BigDecimal amount;

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public @Min(1) BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(@Min(1) BigDecimal amount) {
        this.amount = amount;
    }
}
