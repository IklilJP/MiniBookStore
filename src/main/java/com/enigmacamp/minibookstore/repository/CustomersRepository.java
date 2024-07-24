package com.enigmacamp.minibookstore.repository;

import model.entity.Customers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomersRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomersRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Customers insert(Customers customer) {
        String id = UUID.randomUUID().toString();
        jdbcTemplate.update("INSERT INTO customers (id, name, email, address) VALUES (?::uuid, ?, ?, ?)",
                id, customer.getName(), customer.getEmail(), customer.getAddress());
        customer.setId(id);
        return customer;
    }

    public Customers update(Customers customer) {
        jdbcTemplate.update("UPDATE customers SET name = ?, email = ?, address = ? WHERE id = ?::uuid",
                customer.getName(), customer.getEmail(), customer.getAddress(), customer.getId());
        return customer;
    }

    public Optional<Customers> findById(String id) {
        return jdbcTemplate.query("SELECT * FROM customers WHERE id = ?::uuid", new Object[]{id},
                new BeanPropertyRowMapper<>(Customers.class)).stream().findFirst();
    }

    public List<Customers> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers", new BeanPropertyRowMapper<>(Customers.class));
    }

    public void deleteById(String id) {
        jdbcTemplate.update("DELETE FROM customers WHERE id = ?::uuid", id);
    }
}

