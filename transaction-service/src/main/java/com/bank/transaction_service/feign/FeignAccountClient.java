package com.bank.transaction_service.feign;

import com.bank.transaction_service.model.AccountResponse;
import com.bank.transaction_service.model.BalanceUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service", path = "/api/accounts")
public interface FeignAccountClient {


    @GetMapping("/{accountNumber}")
    AccountResponse getAccountByAccountNumber(@PathVariable String accountNumber);

    @PostMapping(value = "/update_balance", consumes = "application/json")
    AccountResponse updateBalance(@RequestBody BalanceUpdateRequest request);

}
