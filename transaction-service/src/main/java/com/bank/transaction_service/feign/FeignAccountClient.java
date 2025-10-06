package com.bank.transaction_service.feign;

import com.bank.transaction_service.model.AccountDto;
import com.bank.transaction_service.model.BalanceUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service", path = "/api/accounts")
public interface FeignAccountClient {

    @GetMapping("/{id}")
    AccountDto getAccountById(@PathVariable("id") Long accountId);

    @PostMapping(value = "/update_balance", consumes = "application/json")
    AccountDto updateBalance(@RequestBody BalanceUpdateRequest request);
}
