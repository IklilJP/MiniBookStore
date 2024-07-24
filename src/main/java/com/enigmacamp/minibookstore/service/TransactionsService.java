package com.enigmacamp.minibookstore.service;

import model.dto.request.TransactionRequest;
import model.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionsService {
    TransactionResponse createTransaction(TransactionRequest transactionRequest);
    TransactionResponse getTransactionById(String id);
    List<TransactionResponse> getAllTransactions();
    void deleteTransactionById(String id);
}
