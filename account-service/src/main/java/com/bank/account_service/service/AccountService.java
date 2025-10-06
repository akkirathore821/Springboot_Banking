package com.bank.account_service.service;

import com.bank.account_service.model.Account;
import com.bank.account_service.model.AccountDto;
import com.bank.account_service.model.BalanceUpdateRequest;
import com.bank.account_service.repo.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDto create(AccountDto request) {
        Account account = new Account(request.getAccountHolder(),null);
        account = accountRepository.save(account);
        return new AccountDto(account.getAccountId(), account.getAccountHolder(), account.getBalance());
    }

    public AccountDto getAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
        return new AccountDto(account.getAccountId(), account.getAccountHolder(), account.getBalance());
    }

    public AccountDto updateBalance(BalanceUpdateRequest request) {
        Long accountId = request.getAccountId();
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));

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
        return new AccountDto(account.getAccountId(), account.getAccountHolder(), account.getBalance());
    }


}

