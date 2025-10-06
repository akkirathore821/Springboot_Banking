package com.bank.transaction_service.service;

import com.bank.transaction_service.feign.FeignAccountClient;
import com.bank.transaction_service.kafka.TransactionProducer;
import com.bank.transaction_service.model.*;
import com.bank.transaction_service.repo.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.bank.transaction_service.constants.Constants.Transaction_Topic_Name;

@Service
public class TransactionService {

    private final TransactionProducer transactionProducer;
    private final TransactionRepository transactionRepository;
    private final FeignAccountClient feignAccountClient;

    public TransactionService(TransactionProducer transactionProducer, TransactionRepository transactionRepository, FeignAccountClient feignAccountClient) {
        this.transactionProducer = transactionProducer;
        this.transactionRepository = transactionRepository;
        this.feignAccountClient = feignAccountClient;
    }

    public TransactionDto deposit(DepositRequest request) {
        // Fetch accounts
        AccountDto accountById = feignAccountClient.getAccountById(request.getAccountId());

        // Update balances
        AccountDto resultAccountDto = feignAccountClient.updateBalance(
                new BalanceUpdateRequest(accountById.getAccountId(),request.getAmount(),"CREDIT"));

        Transaction transaction = new Transaction();
        transaction.setAccountId(request.getAccountId());
        transaction.setAmount(request.getAmount());
        transaction.setType("CREDIT");
        Transaction resultantTransaction = transactionRepository.save(transaction);

        return new TransactionDto(resultantTransaction.getAccountId(),
                resultAccountDto.getAccountHolder(),resultantTransaction.getAmount(),resultantTransaction.getType(),resultAccountDto.getBalance());
    }
    public TransactionDto withdraw(WithdrawRequest request) {

        // Fetch accounts
        AccountDto accountById = feignAccountClient.getAccountById(request.getAccountId());

        if (accountById.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance!");
        }

        // Update balances
        AccountDto resultAccountDto = feignAccountClient.updateBalance(
                new BalanceUpdateRequest(accountById.getAccountId(),request.getAmount(), "DEBIT"));

        Transaction transaction = new Transaction();
        transaction.setAccountId(request.getAccountId());
        transaction.setAmount(request.getAmount());
        transaction.setType("DEBIT");
        Transaction resultantTransaction = transactionRepository.save(transaction);

        return new TransactionDto(resultantTransaction.getAccountId(),
                resultAccountDto.getAccountHolder(),resultantTransaction.getAmount(),resultantTransaction.getType(),resultAccountDto.getBalance());
    }
    public List<TransactionDto> transfer(TransferRequest request) {

        // Fetch accounts
        AccountDto fromAccount = feignAccountClient.getAccountById(request.getFromAccountId());
        AccountDto toAccount = feignAccountClient.getAccountById(request.getToAccountId());

        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance!");
        }

        // Update balances
        AccountDto fromAccountDto = feignAccountClient.updateBalance(
                new BalanceUpdateRequest(fromAccount.getAccountId(),request.getAmount(), "DEBIT"));

        AccountDto toAccountDto = feignAccountClient.updateBalance(
                new BalanceUpdateRequest(toAccount.getAccountId(),request.getAmount(),"CREDIT"));

        Transaction transaction = new Transaction();
        transaction.setAccountId(request.getFromAccountId());
        transaction.setTargetAccountId(request.getToAccountId());
        transaction.setAmount(request.getAmount());
        transaction.setType("TRANSFER");
        Transaction resultantTransaction = transactionRepository.save(transaction);

        List<TransactionDto> resultantList = new ArrayList<>();
        resultantList.add(new TransactionDto(resultantTransaction.getAccountId(),
                fromAccountDto.getAccountHolder(),resultantTransaction.getAmount(),
                resultantTransaction.getType(),fromAccountDto.getBalance()));
        resultantList.add(new TransactionDto(resultantTransaction.getTargetAccountId(),
                toAccountDto.getAccountHolder(),resultantTransaction.getAmount(),
                resultantTransaction.getType(),toAccountDto.getBalance()));

        return resultantList;
    }

    public void publish(Map<String, Object> evt) {
        transactionProducer.publish(Transaction_Topic_Name, evt);
    }

}
