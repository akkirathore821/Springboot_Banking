package com.bank.authentication_service.feign;

import com.bank.authentication_service.model.AccountResponse;
import com.bank.authentication_service.model.CreateAccountRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "account-service", path = "/api/accounts")
public interface FeignAccountClient {

    @PostMapping(value = "/create", consumes = "application/json")
    AccountResponse createAccount(@RequestBody CreateAccountRequest request);

}