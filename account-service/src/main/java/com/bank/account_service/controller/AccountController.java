package com.bank.account_service.controller;

import com.bank.account_service.model.AccountResponse;
import com.bank.account_service.model.BalanceUpdateRequest;
import com.bank.account_service.model.CreateAccountRequest;
import com.bank.account_service.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AccountController.class);
    private final AccountService accountService;
    private final StringRedisTemplate redisTemplate;

    public AccountController(AccountService accountService, StringRedisTemplate redisTemplate) {
        this.accountService = accountService;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<AccountResponse> createAccount(@RequestBody CreateAccountRequest request) {
        log.info("AccountController:createAccount:Init...");

        AccountResponse result = accountService.create(request);

        log.info("AccountController:createAccount:End...");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{accountNumber}")
    @Transactional
    public ResponseEntity<AccountResponse> getAccountByNumber(@PathVariable String accountNumber) {
        log.info("AccountController:getAccountById:Init...");

        if (!accountNumber.matches("^[A-Z\\d\\s]+$")) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "AccountNumber should only contain alphanumeric characters");
        }

        AccountResponse result = accountService.getAccount(accountNumber);

        log.info("AccountController:getAccountById:End...");
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update_balance")
    @Transactional
    public ResponseEntity<AccountResponse> updateBalance(@RequestBody BalanceUpdateRequest request) {
        log.info("AccountController:updateBalance:Init...");

        AccountResponse result = accountService.updateBalance(request);

//        Todo sending the notification to the Notification Service using redis
//        log.info("AccountController:updateBalance:Publishing to redis through redisTemplate...");
//        redisTemplate.convertAndSend(Redis_Account_Topic_Name,result.toString());

        log.info("AccountController:updateBalance:End...");
        return ResponseEntity.ok(result);
    }

//    @GetMapping
//    public List<Account> all() {
//        return repo.findAll();
//    }
}
