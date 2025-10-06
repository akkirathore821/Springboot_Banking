package com.bank.account_service.controller;

import com.bank.account_service.model.Account;
import com.bank.account_service.model.AccountDto;
import com.bank.account_service.model.BalanceUpdateRequest;
import com.bank.account_service.repo.AccountRepository;
import com.bank.account_service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.management.Notification;
import java.util.List;

import static com.bank.account_service.constants.Constants.Redis_Account_Topic_Name;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final StringRedisTemplate redisTemplate;

    public AccountController(AccountService accountService, StringRedisTemplate redisTemplate) {
        this.accountService = accountService;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<AccountDto> create(@RequestBody AccountDto request) {
        AccountDto result = accountService.create(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        AccountDto result = accountService.getAccount(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update_balance")
    @Transactional
    public ResponseEntity<AccountDto> updateBalance(@RequestBody BalanceUpdateRequest request) {
        AccountDto result = accountService.updateBalance(request);

//        Todo sending the notification to the Notification Service using redis
//        redisTemplate.convertAndSend(Redis_Account_Topic_Name,result.toString());
        return ResponseEntity.ok(result);
    }

//    @GetMapping
//    public List<Account> all() {
//        return repo.findAll();
//    }
}
