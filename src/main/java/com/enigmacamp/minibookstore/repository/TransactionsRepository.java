package com.enigmacamp.minibookstore.repository;

import model.entity.Books;
import model.entity.Customers;
import model.entity.Staffs;
import model.entity.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TransactionsRepository {

    private final JdbcTemplate jdbcTemplate;
    private final BooksRepository booksRepository;
    private final CustomersRepository customersRepository;
    private final StaffsRepository staffsRepository;

    @Autowired
    public TransactionsRepository(JdbcTemplate jdbcTemplate, BooksRepository booksRepository, CustomersRepository customersRepository, StaffsRepository staffsRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.booksRepository = booksRepository;
        this.customersRepository = customersRepository;
        this.staffsRepository = staffsRepository;
    }

    public Transactions insert(Transactions transaction) {
        String id = UUID.randomUUID().toString();

        Customers customer = customersRepository.findById(transaction.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Staffs staff = transaction.getStaffs() != null ? staffsRepository.findById(transaction.getStaffs().getId())
                .orElse(null) : null;

        jdbcTemplate.update("INSERT INTO transactions (id, customer_id, staff_id, totalamount, transactiondate) VALUES (?::uuid, ?::uuid, ?::uuid, ?, ?)",
                id, customer.getId(), staff != null ? staff.getId() : null, transaction.getTotalAmount(), transaction.getTransactionDate());

        for (Books book : transaction.getBooks()) {
            jdbcTemplate.update("INSERT INTO transaction_books (transaction_id, book_id) VALUES (?::uuid, ?::uuid)",
                    id, book.getId());
        }

        transaction.setId(id);
        return transaction;
    }

    public Optional<Transactions> findById(String id) {
        String sql = "SELECT t.*, c.name AS customer_name, c.email AS customer_email, c.address AS customer_address, " +
                "s.name AS staff_name, s.email AS staff_email, s.position AS staff_position " +
                "FROM transactions t " +
                "JOIN customers c ON t.customer_id = c.id " +
                "LEFT JOIN staff s ON t.staff_id = s.id " +
                "WHERE t.id = ?::uuid";

        return jdbcTemplate.query(sql, new Object[]{id}, this::mapTransactionRow).stream().findFirst();
    }

    public List<Transactions> findAll() {
        String sql = "SELECT t.*, c.name AS customer_name, c.email AS customer_email, c.address AS customer_address, " +
                "s.name AS staff_name, s.email AS staff_email, s.position AS staff_position " +
                "FROM transactions t " +
                "JOIN customers c ON t.customer_id = c.id " +
                "LEFT JOIN staff s ON t.staff_id = s.id";

        return jdbcTemplate.query(sql, this::mapTransactionRow);
    }

    public void deleteById(String id) {

        jdbcTemplate.update("DELETE FROM transaction_books WHERE transaction_id = ?::uuid", id);

        jdbcTemplate.update("DELETE FROM transactions WHERE id = ?::uuid", id);
    }


    private Transactions mapTransactionRow(ResultSet rs, int rowNum) throws SQLException {
        Transactions transaction = new Transactions();
        transaction.setId(rs.getString("id"));
        transaction.setTotalAmount(rs.getLong("totalamount"));
        transaction.setTransactionDate(rs.getLong("transactiondate"));


        Customers customer = new Customers();
        customer.setId(rs.getString("customer_id"));
        customer.setName(rs.getString("customer_name"));
        customer.setEmail(rs.getString("customer_email"));
        customer.setAddress(rs.getString("customer_address"));
        transaction.setCustomer(customer);


        String staffId = rs.getString("staff_id");
        if (staffId != null) {
            Staffs staff = new Staffs();
            staff.setId(staffId);
            staff.setName(rs.getString("staff_name"));
            staff.setEmail(rs.getString("staff_email"));
            staff.setPosition(Staffs.StaffPosition.valueOf(rs.getString("staff_position")));
            transaction.setStaffs(staff);
        }

        List<Books> books = jdbcTemplate.query(
                "SELECT b.* FROM books b JOIN transaction_books tb ON b.id = tb.book_id WHERE tb.transaction_id = ?::uuid",
                new Object[]{transaction.getId()},
                new BeanPropertyRowMapper<>(Books.class)
        );
        transaction.setBooks(books);

        return transaction;
    }
}