package com.bank.account_service.service;

import com.bank.account_service.model.Account;
import com.bank.account_service.model.AccountResponse;
import com.bank.account_service.model.BalanceUpdateRequest;
import com.bank.account_service.model.CreateAccountRequest;
import com.bank.account_service.repo.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountResponse create(CreateAccountRequest request) {
        log.info("AccountService:create:Init...");

        Account account = new Account(request.getAccountHolder(), request.getAccountNumber());
        account = accountRepository.save(account);

        log.info("AccountService:create:End...");
        return new AccountResponse(account.getAccountNumber(), account.getAccountHolder(), account.getBalance());
    }

    public AccountResponse getAccount(String accountNumber) {
        log.info("AccountService:getAccount:Init...");

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found with account number: " + accountNumber));

        log.info("AccountService:getAccount:End...");
        return new AccountResponse(account.getAccountNumber(), account.getAccountHolder(), account.getBalance());
    }


    public AccountResponse updateBalance(BalanceUpdateRequest request) {
        log.info("AccountService:updateBalance:Init...");

        String accountNumber = request.getAccountNumber();
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found with accountNumber: " + accountNumber));

        log.info("AccountService:updateBalance:Updating Balance...");
        if ("DEBIT".equalsIgnoreCase(request.getOperation())) {
            if (account.getBalance().compareTo(request.getAmount()) < 0) {
                throw new RuntimeException("Insufficient balance in account " + account);
            }
            account.setBalance(account.getBalance().subtract(request.getAmount()));
        } else if ("CREDIT".equalsIgnoreCase(request.getOperation())) {
            account.setBalance(account.getBalance().add(request.getAmount()));
        } else {
            throw new IllegalArgumentException("Invalid operation: " + request.getOperation());
        }

        account = accountRepository.save(account);

        log.info("AccountService:updateBalance:End...");
        return new AccountResponse(account.getAccountNumber(), account.getAccountHolder(), account.getBalance());
    }


    private String generateAccountNumber() {
        Random random = new Random();
        int number = 100000 + random.nextInt(900000);
        return "ACC" + number;
    }
}

