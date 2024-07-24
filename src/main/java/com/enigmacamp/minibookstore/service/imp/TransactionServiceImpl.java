package com.enigmacamp.minibookstore.service.imp;

import com.enigmacamp.minibookstore.repository.BooksRepository;
import com.enigmacamp.minibookstore.repository.CustomersRepository;
import com.enigmacamp.minibookstore.repository.StaffsRepository;
import com.enigmacamp.minibookstore.repository.TransactionsRepository;
import com.enigmacamp.minibookstore.service.TransactionsService;
import model.dto.request.TransactionRequest;
import model.dto.response.TransactionResponse;
import model.entity.Books;
import model.entity.Customers;
import model.entity.Staffs;
import model.entity.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionsService {
    private final TransactionsRepository transactionsRepository;
    private final BooksRepository booksRepository;
    private final CustomersRepository customersRepository;
    private final StaffsRepository staffsRepository;

    @Autowired
    public TransactionServiceImpl(TransactionsRepository transactionsRepository, BooksRepository booksRepository, CustomersRepository customersRepository, StaffsRepository staffsRepository) {
        this.transactionsRepository = transactionsRepository;
        this.booksRepository = booksRepository;
        this.customersRepository = customersRepository;
        this.staffsRepository = staffsRepository;
    }

    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {

        if (transactionRequest.getBookIds() == null || transactionRequest.getBookIds().isEmpty()) {
            throw new IllegalArgumentException("Daftar buku tidak boleh kosong");
        }

        Customers customer = customersRepository.findById(transactionRequest.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Staffs staff = transactionRequest.getStaffId() != null ? staffsRepository.findById(transactionRequest.getStaffId())
                .orElse(null) : null;

        List<Books> books = transactionRequest.getBookIds().stream()
                .map(booksRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        long totalAmount = books.stream()
                .mapToLong(Books::getPrice)
                .sum();

        Transactions transaction = new Transactions();
        transaction.setCustomer(customer);
        transaction.setStaffs(staff);
        transaction.setBooks(books);
        transaction.setTotalAmount(totalAmount);
        transaction.setTransactionDate(System.currentTimeMillis());

        Transactions savedTransaction = transactionsRepository.insert(transaction);

        return TransactionResponse.fromEntity(savedTransaction);
    }

    @Override
    public TransactionResponse getTransactionById(String id) {
        Transactions transaction = transactionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return TransactionResponse.fromEntity(transaction);
    }

    @Override
    public List<TransactionResponse> getAllTransactions() {
        return transactionsRepository.findAll().stream()
                .map(TransactionResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTransactionById(String id) {
        transactionsRepository.deleteById(id);
    }
}
