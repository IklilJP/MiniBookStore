package model.dto.response;

import model.entity.Books;
import model.entity.Customers;
import model.entity.Staffs;
import model.entity.Transactions;

import java.util.List;

public class TransactionResponse {
    private String id;
    private Customers customer;
    private Staffs staff;
    private List<Books> books;
    private Long totalAmount;
    private Long transactionDate;

    public TransactionResponse(String id, Customers customer, Staffs staff, List<Books> books, Long totalAmount, Long transactionDate) {
        this.id = id;
        this.customer = customer;
        this.staff = staff;
        this.books = books;
        this.totalAmount = totalAmount;
        this.transactionDate = transactionDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    public Staffs getStaff() {
        return staff;
    }

    public void setStaff(Staffs staff) {
        this.staff = staff;
    }

    public List<Books> getBooks() {
        return books;
    }

    public void setBooks(List<Books> books) {
        this.books = books;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Long transactionDate) {
        this.transactionDate = transactionDate;
    }

    public static TransactionResponse fromEntity(Transactions transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getCustomer(),
                transaction.getStaffs(),
                transaction.getBooks(),
                transaction.getTotalAmount(),
                transaction.getTransactionDate()
        );
    }
}
