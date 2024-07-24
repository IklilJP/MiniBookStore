package com.enigmacamp.minibookstore.service;

import model.dto.request.BookRequest;
import model.dto.response.BookResponse;
import model.entity.Books;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksService {
    BookResponse createBook(BookRequest bookRequest);
    BookResponse getBookById(String id);
    List<BookResponse> getAllBooks();
    BookResponse updateBook(String id, BookRequest bookRequest);

    void deleteBookById(String id);
}

