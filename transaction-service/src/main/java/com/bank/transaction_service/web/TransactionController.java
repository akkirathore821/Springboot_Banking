package com.bank.transaction_service.web;

import com.bank.transaction_service.model.*;

import com.bank.transaction_service.service.TransactionService;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@EnableFeignClients(basePackages = "com.bank.transaction_service.feign")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping("/deposit")
    @Transactional
    public ResponseEntity<TransactionDto> deposit(@RequestBody DepositRequest request) {

        TransactionDto result = transactionService.deposit(request);

        Map<String,Object> evt = new HashMap<>();
        evt.put("event", "CREDIT");
        evt.put("accountId", request.getAccountId());
        evt.put("amount", request.getAmount());
//        transactionService.publish(evt);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/withdraw")
    @Transactional
    public ResponseEntity<TransactionDto> withdraw(@RequestBody WithdrawRequest request) {

        TransactionDto result = transactionService.withdraw(request);

        Map<String,Object> evt = new HashMap<>();
        evt.put("event", "DEBIT");
        evt.put("accountId", request.getAccountId());
        evt.put("amount", request.getAmount());
//        transactionService.publish(evt);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/transfer")
    @Transactional
    public ResponseEntity<List<TransactionDto>>  transfer(@RequestBody TransferRequest request) {

        List<TransactionDto> result = transactionService.transfer(request);

        Map<String,Object> evt = new HashMap<>();
        evt.put("event", "TRANSFER");
        evt.put("fromAccountId", request.getFromAccountId());
        evt.put("toAccountId", request.getToAccountId());
        evt.put("amount", request.getAmount());
//        transactionService.publish(evt);

        return ResponseEntity.ok(result);
    }
}

