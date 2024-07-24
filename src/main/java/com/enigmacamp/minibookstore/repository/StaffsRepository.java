package com.enigmacamp.minibookstore.repository;

import model.entity.Staffs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class StaffsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StaffsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Staffs insert(Staffs staff) {
        String id = UUID.randomUUID().toString();
        jdbcTemplate.update("INSERT INTO staff (id, name, email, password, position) VALUES (?::uuid, ?, ?, ?, ?)",
                id, staff.getName(), staff.getEmail(), staff.getPassword(), staff.getPosition().name());
        staff.setId(id);
        return staff;
    }

    public Staffs update(Staffs staff) {
        jdbcTemplate.update("UPDATE staff SET name = ?, email = ?, password = ?, position = ? WHERE id = ?::uuid",
                staff.getName(), staff.getEmail(), staff.getPassword(), staff.getPosition().name(), staff.getId());
        return staff;
    }

    public Optional<Staffs> findById(String id) {
        return jdbcTemplate.query("SELECT * FROM staff WHERE id = ?::uuid", new Object[]{id},
                new BeanPropertyRowMapper<>(Staffs.class)).stream().findFirst();
    }

    public List<Staffs> findAll() {
        return jdbcTemplate.query("SELECT * FROM staff", new BeanPropertyRowMapper<>(Staffs.class));
    }

    public void deleteById(String id) {
        jdbcTemplate.update("DELETE FROM staff WHERE id = ?::uuid", id);
    }
}

