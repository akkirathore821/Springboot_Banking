package com.bank.transaction_service.feign;

import com.bank.transaction_service.model.AccountResponse;
import com.bank.transaction_service.model.BalanceUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "account-service")
public interface FeignAccountClient {


    @GetMapping("/api/accounts/accounts_details/getAccount")
    AccountResponse getAccountByAccountNumber(@RequestHeader("accountNumber") String accountNumberFromHeader);

    @PostMapping(value = "/api/accounts/accounts/update_balance", consumes = "application/json")
    AccountResponse updateBalance(@RequestBody BalanceUpdateRequest request);

}
