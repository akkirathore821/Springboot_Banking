package com.bank.transaction_service.service;

import com.bank.transaction_service.feign.FeignAccountClient;
import com.bank.transaction_service.kafka.TransactionProducer;
import com.bank.transaction_service.model.*;
import com.bank.transaction_service.repo.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.bank.transaction_service.constants.Constants.Transaction_Topic_Name;

@Slf4j
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

    @Transactional
    public TransactionResponse deposit(DepositRequest request) {
        // Fetch accounts
        AccountResponse accountByAccountNumber = feignAccountClient.getAccountByAccountNumber(request.getAccountNumber());

        // Update balances
        AccountResponse resultAccountDto = feignAccountClient.updateBalance(
                new BalanceUpdateRequest(accountByAccountNumber.getAccountNumber(),
                        request.getAmount(),"CREDIT"));

//        log.info("TransactionService:deposit:update account : " + resultAccountDto.toString());

        Transaction transaction = new Transaction();
        transaction.setAccountNumber(request.getAccountNumber());
        transaction.setAmount(request.getAmount());
        transaction.setType("CREDIT");
        Transaction resultantTransaction = transactionRepository.save(transaction);


        return new TransactionResponse(resultantTransaction.getAccountNumber(),
                resultantTransaction.getType(),
                resultantTransaction.getAmount(),
                resultantTransaction.getTargetAccountNumber(),
                resultAccountDto.getBalance());
    }

    @Transactional
    public TransactionResponse withdraw(WithdrawRequest request) {


        // Fetch accounts
        AccountResponse accountByAccountNumber = feignAccountClient.getAccountByAccountNumber(request.getAccountNumber());

        if (accountByAccountNumber.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance!");
        }

        // Update balances
        AccountResponse resultAccountDto = feignAccountClient.updateBalance(
                new BalanceUpdateRequest(accountByAccountNumber.getAccountNumber(),request.getAmount(), "DEBIT"));

        Transaction transaction = new Transaction();
        transaction.setAccountNumber(request.getAccountNumber());
        transaction.setAmount(request.getAmount());
        transaction.setType("DEBIT");
        Transaction resultantTransaction = transactionRepository.save(transaction);

        return new TransactionResponse(resultantTransaction.getAccountNumber(),
                resultantTransaction.getType(),
                resultantTransaction.getAmount(),
                resultantTransaction.getTargetAccountNumber(),
                resultAccountDto.getBalance());
    }

    @Transactional
    public TransactionResponse transfer(TransferRequest request) {

        // Fetch accounts
        AccountResponse fromAccount = feignAccountClient.getAccountByAccountNumber(request.getSenderAccountNumber());
        AccountResponse toAccount = feignAccountClient.getAccountByAccountNumber(request.getReceiverAccountNumber());

        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance!");
        }

        // Update balances
        AccountResponse fromAccountDto = feignAccountClient.updateBalance(
                new BalanceUpdateRequest(fromAccount.getAccountNumber(),request.getAmount(), "DEBIT"));

        AccountResponse toAccountDto = feignAccountClient.updateBalance(
                new BalanceUpdateRequest(toAccount.getAccountNumber(),request.getAmount(),"CREDIT"));

        Transaction transaction = new Transaction();
        transaction.setAccountNumber(request.getSenderAccountNumber());
        transaction.setTargetAccountNumber(request.getReceiverAccountNumber());
        transaction.setAmount(request.getAmount());
        transaction.setType("TRANSFER");
        Transaction resultantTransaction = transactionRepository.save(transaction);

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
