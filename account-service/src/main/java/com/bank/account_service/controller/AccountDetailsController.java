package com.bank.account_service.controller;

import com.bank.account_service.model.AccountResponse;
import com.bank.account_service.model.BalanceUpdateRequest;
import com.bank.account_service.model.CreateAccountRequest;
import com.bank.account_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
        import org.springframework.web.client.HttpClientErrorException;

import static com.bank.account_service.constants.Constants.Redis_Account_Topic_Name;

@Slf4j
@RestController
@RequestMapping("/api/accounts/accounts_details")
public class AccountDetailsController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/getAccount")
    public ResponseEntity<AccountResponse> getAccountByAccountNumber(@RequestHeader("accountNumber") String accountNumberFromHeader) {
        if (!accountNumberFromHeader.matches("^[A-Z\\d\\s]+$")) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "AccountNumber should only contain alphanumeric characters");
        }
        AccountResponse result = accountService.getAccount(accountNumberFromHeader);
        return ResponseEntity.ok(result);
    }
}
