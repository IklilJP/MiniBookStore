package model.entity;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "transactions")
public class Transactions {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customers customer;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staffs staffs;

    @ManyToMany
    @JoinTable(
            name = "transaction_books",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Books> books;

    @Column(nullable = false)
    private Long totalAmount;

    @Column(nullable = false)
    private Long transactionDate;

    public Transactions() {

    }

    public Transactions(String id, Customers customer, Staffs staffs, List<Books> books, Long totalAmount, Long transactionDate) {
        this.id = id;
        this.customer = customer;
        this.staffs = staffs;
        this.books = books;
        this.totalAmount = totalAmount;
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "id='" + id + '\'' +
                ", customer=" + customer +
                ", staffs=" + staffs +
                ", books=" + books +
                ", totalAmount=" + totalAmount +
                ", transactionDate=" + transactionDate +
                '}';
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

    public Staffs getStaffs() {
        return staffs;
    }

    public void setStaffs(Staffs staffs) {
        this.staffs = staffs;
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
}
