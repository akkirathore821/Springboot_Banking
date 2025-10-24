package com.bank.transaction_service.controller;

import com.bank.transaction_service.model.*;

import com.bank.transaction_service.service.TransactionService;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/transactions")
@EnableFeignClients(basePackages = "com.bank.transaction_service.feign")
public class TransactionController {

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(@RequestBody DepositRequest request,
                                                       @RequestHeader("accountNumber") String accountNumberFromHeader) {

        request.setAccountNumber(accountNumberFromHeader);

        TransactionResponse result = transactionService.deposit(request);

//        Todo Kafka
//        log.info("TransactionController:deposit:Publishing to the kafka...");
//        Map<String,Object> evt = new HashMap<>();
//        evt.put("event", "CREDIT");
//        evt.put("accountNumber", request.getAccountNumber());
//        evt.put("amount", request.getAmount());
//        transactionService.publish(evt);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@RequestBody WithdrawRequest request,
                                                        @RequestHeader("accountNumber") String accountNumberFromHeader) {

        request.setAccountNumber(accountNumberFromHeader);

        TransactionResponse result = transactionService.withdraw(request);

//        Todo Kafka
//        log.info("TransactionController:deposit:Publishing to the kafka...");
//        Map<String,Object> evt = new HashMap<>();
//        evt.put("event", "DEBIT");
//        evt.put("accountNumber", request.getAccountNumber());
//        evt.put("amount", request.getAmount());
//        transactionService.publish(evt);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse>  transfer(@RequestBody TransferRequest request,
                                                         @RequestHeader("accountNumber") String accountNumberFromHeader) {

        request.setSenderAccountNumber(accountNumberFromHeader);

        TransactionResponse result = transactionService.transfer(request);

//        Todo Kafka
//        log.info("TransactionController:deposit:Publishing to the kafka...");
//        Map<String,Object> evt = new HashMap<>();
//        evt.put("event", "TRANSFER");
//        evt.put("fromAccountId", request.getFromAccountId());
//        evt.put("toAccountId", request.getToAccountId());
//        evt.put("amount", request.getAmount());
//        transactionService.publish(evt);

        return ResponseEntity.ok(result);
    }
}

