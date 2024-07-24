package com.enigmacamp.minibookstore.service.imp;

import com.enigmacamp.minibookstore.repository.BooksRepository;
import com.enigmacamp.minibookstore.service.BooksService;
import model.dto.request.BookRequest;
import model.dto.response.BookResponse;
import model.entity.Books;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BookServiceImpl(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public BookResponse createBook(BookRequest bookRequest) {
        Books book = new Books();
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setIsbn(bookRequest.getIsbn());
        book.setPrice(bookRequest.getPrice());
        book.setStock(bookRequest.getStock());

        Books savedBook = booksRepository.insert(book);
        return BookResponse.fromEntity(savedBook);
    }

    @Override
    public BookResponse getBookById(String id) {
        return booksRepository.findById(id)
                .map(BookResponse::fromEntity)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + id));
    }

    @Override
    public List<BookResponse> getAllBooks() {
        return booksRepository.findAll().stream()
                .map(BookResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponse updateBook(String id, BookRequest bookRequest) {
        Books existingBook = booksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + id));


        existingBook.setTitle(bookRequest.getTitle());
        existingBook.setAuthor(bookRequest.getAuthor());
        existingBook.setIsbn(bookRequest.getIsbn());
        existingBook.setPrice(bookRequest.getPrice());
        existingBook.setStock(bookRequest.getStock());

        Books updatedBook = booksRepository.update(existingBook);
        return BookResponse.fromEntity(updatedBook);
    }

    @Override
    public void deleteBookById(String id) {
        booksRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with ID: " + id));
        booksRepository.deleteById(id);
    }
}

