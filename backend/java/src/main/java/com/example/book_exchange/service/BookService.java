package com.example.book_exchange.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

import com.example.book_exchange.exception.HttpStatusException;
import com.example.book_exchange.model.BookDto;
import com.example.book_exchange.model.UserDto;
import com.example.book_exchange.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthService authService;

    public List<BookDto> getBooks(String location, Boolean isAvailable, String token) {
        authService.checkToken(token);
        return bookRepository.findByFilter(location, isAvailable);
    }

    public void uploadBook(@Valid @NotNull BookDto book, String token) {
        authService.checkToken(token);

        if (book.getLocation() == null || book.getLocation() == "") {
            throw new HttpStatusException(HttpStatus.FORBIDDEN, "Невозможно добавить книгу без локации");
        }

        book.setId(null);
        book.setBorrowBy(null);
        book.setAvailable(true);
        bookRepository.save(book);
    }

    public BookDto getBookById(long id, String token) {
        authService.checkToken(token);
        return bookRepository.findById(id)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "Книга с таким id не найдена"));
    }

    public List<BookDto> getBooksByBorrowBy(long id, String token) {
        UserDto user = authService.getUserByToken(token);
        if (user.getId() != id) {
            throw new HttpStatusException(HttpStatus.UNAUTHORIZED, "Произошла ошибка");
        }
        return bookRepository.findByBorrowBy(id);
    }
}
