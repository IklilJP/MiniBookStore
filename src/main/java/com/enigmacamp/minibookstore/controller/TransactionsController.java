package com.enigmacamp.minibookstore.controller;

import com.enigmacamp.minibookstore.config.APIUrl;
import com.enigmacamp.minibookstore.service.TransactionsService;
import model.dto.request.TransactionRequest;
import model.dto.response.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIUrl.TRANSACTIONS_API_URL)
public class TransactionsController {
    private final TransactionsService transactionsService;

    @Autowired
    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        TransactionResponse transactionResponse = transactionsService.createTransaction(transactionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable String id) {
        TransactionResponse transactionResponse = transactionsService.getTransactionById(id);
        return ResponseEntity.ok(transactionResponse);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        List<TransactionResponse> transactionResponses = transactionsService.getAllTransactions();
        return ResponseEntity.ok(transactionResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable String id) {
        transactionsService.deleteTransactionById(id);
        return ResponseEntity.noContent().build();
    }
}
