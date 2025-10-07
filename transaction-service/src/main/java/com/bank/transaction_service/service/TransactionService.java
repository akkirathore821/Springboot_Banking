package com.bank.transaction_service.service;

import com.bank.transaction_service.feign.FeignAccountClient;
import com.bank.transaction_service.kafka.TransactionProducer;
import com.bank.transaction_service.model.*;
import com.bank.transaction_service.repo.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.bank.transaction_service.constants.Constants.Transaction_Topic_Name;

@Service
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionProducer transactionProducer;
    private final TransactionRepository transactionRepository;
    private final FeignAccountClient feignAccountClient;

    public TransactionService(TransactionProducer transactionProducer, TransactionRepository transactionRepository, FeignAccountClient feignAccountClient) {
        this.transactionProducer = transactionProducer;
        this.transactionRepository = transactionRepository;
        this.feignAccountClient = feignAccountClient;
    }

    public TransactionResponse deposit(DepositRequest request) {
        log.info("TransactionService:deposit:Init...");

        // Fetch accounts
        log.info("TransactionService:deposit:fetching account by accountNumber...");
        AccountResponse accountByAccountNumber = feignAccountClient.getAccountByAccountNumber(request.getAccountNumber());

        // Update balances
        log.info("TransactionService:deposit:Updating balance...");
        AccountResponse resultAccountDto = feignAccountClient.updateBalance(
                new BalanceUpdateRequest(accountByAccountNumber.getAccountNumber(),request.getAmount(),"CREDIT"));

//        log.info("TransactionService:deposit:update account : " + resultAccountDto.toString());

        Transaction transaction = new Transaction();
        transaction.setAccountNumber(request.getAccountNumber());
        transaction.setAmount(request.getAmount());
        transaction.setType("CREDIT");
        Transaction resultantTransaction = transactionRepository.save(transaction);


        log.info("TransactionService:deposit:End...");
        return new TransactionResponse(resultantTransaction.getAccountNumber(),
                resultantTransaction.getType(),
                resultantTransaction.getAmount(),
                resultantTransaction.getTargetAccountNumber(),
                resultAccountDto.getBalance());
    }


    public TransactionResponse withdraw(WithdrawRequest request) {
        log.info("TransactionService:withdraw:Init...");

        // Fetch accounts
        log.info("TransactionService:withdraw:fetching account by accountNumber...");
        AccountResponse accountByAccountNumber = feignAccountClient.getAccountByAccountNumber(request.getAccountNumber());

        if (accountByAccountNumber.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance!");
        }

        // Update balances
        log.info("TransactionService:withdraw:Updating balance...");
        AccountResponse resultAccountDto = feignAccountClient.updateBalance(
                new BalanceUpdateRequest(accountByAccountNumber.getAccountNumber(),request.getAmount(), "DEBIT"));

        log.info("TransactionService:withdraw:Saving the transaction...");
        Transaction transaction = new Transaction();
        transaction.setAccountNumber(request.getAccountNumber());
        transaction.setAmount(request.getAmount());
        transaction.setType("DEBIT");
        Transaction resultantTransaction = transactionRepository.save(transaction);

        log.info("TransactionService:withdraw:End...");
        return new TransactionResponse(resultantTransaction.getAccountNumber(),
                resultantTransaction.getType(),
                resultantTransaction.getAmount(),
                resultantTransaction.getTargetAccountNumber(),
                resultAccountDto.getBalance());
    }
    public TransactionResponse transfer(TransferRequest request) {
        log.info("TransactionService:transfer:Init...");

        // Fetch accounts
        log.info("TransactionService:transfer:fetching account by accountNumber...");
        AccountResponse fromAccount = feignAccountClient.getAccountByAccountNumber(request.getSenderAccountNumber());
        AccountResponse toAccount = feignAccountClient.getAccountByAccountNumber(request.getReceiverAccountNumber());

        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance!");
        }

        // Update balances
        log.info("TransactionService:transfer:Updating balance...");
        AccountResponse fromAccountDto = feignAccountClient.updateBalance(
                new BalanceUpdateRequest(fromAccount.getAccountNumber(),request.getAmount(), "DEBIT"));

        AccountResponse toAccountDto = feignAccountClient.updateBalance(
                new BalanceUpdateRequest(toAccount.getAccountNumber(),request.getAmount(),"CREDIT"));

        log.info("TransactionService:transfer:Saving the transaction...");
        Transaction transaction = new Transaction();
        transaction.setAccountNumber(request.getSenderAccountNumber());
        transaction.setTargetAccountNumber(request.getReceiverAccountNumber());
        transaction.setAmount(request.getAmount());
        transaction.setType("TRANSFER");
        Transaction resultantTransaction = transactionRepository.save(transaction);

        log.info("TransactionService:transfer:End...");
        return new TransactionResponse(resultantTransaction.getAccountNumber(),
                resultantTransaction.getType(),
                resultantTransaction.getAmount(),
                resultantTransaction.getTargetAccountNumber(),
                fromAccountDto.getBalance());
    }

    public void publish(Map<String, Object> evt) {
        transactionProducer.publish(Transaction_Topic_Name, evt);
    }

}
