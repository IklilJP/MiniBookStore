package com.enigmacamp.minibookstore.repository;

import model.entity.Books;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BooksRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BooksRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Books insert(Books book) {
        String id = UUID.randomUUID().toString();
        jdbcTemplate.update("INSERT INTO books (id, title, author, isbn, price, stock) VALUES (?::uuid, ?, ?, ?, ?, ?)",
                id, book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPrice(), book.getStock());
        book.setId(id);
        return book;
    }

    public Books update(Books book) {
        jdbcTemplate.update("UPDATE books SET title = ?, author = ?, isbn = ?, price = ?, stock = ? WHERE id = ?::uuid",
                book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPrice(), book.getStock(), book.getId());
        return book;
    }

    public Optional<Books> findById(String id) {
        return jdbcTemplate.query("SELECT * FROM books WHERE id = ?::uuid", new Object[]{id},
                new BeanPropertyRowMapper<>(Books.class)).stream().findFirst();
    }

    public List<Books> findAll() {
        return jdbcTemplate.query("SELECT * FROM books", new BeanPropertyRowMapper<>(Books.class));
    }

    public void deleteById(String id) {
        jdbcTemplate.update("DELETE FROM books WHERE id = ?::uuid", id);
    }
}
