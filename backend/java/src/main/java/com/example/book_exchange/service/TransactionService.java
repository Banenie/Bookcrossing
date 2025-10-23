package com.example.book_exchange.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.example.book_exchange.model.TransactionDto;
import com.example.book_exchange.model.UserDto;
import com.example.book_exchange.exception.HttpStatusException;
import com.example.book_exchange.model.BookDto;
import com.example.book_exchange.repository.BookRepository;
import com.example.book_exchange.repository.TransactionRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BookRepository bookRepository;
    private final AuthService authService;

    public List<TransactionDto> getTransactions(String token) {
        UserDto user = authService.getUserByToken(token);

        if (!user.isAdmin()) {
            throw new HttpStatusException(HttpStatus.FORBIDDEN, "Вы не являетесь админом");
        }
        return transactionRepository.findAll();
    }

    public void uploadTransaction(@Valid @NotNull TransactionDto transaction, String token) {
        UserDto user = authService.getUserByToken(token);
        transaction.setId(null);
        transaction.setUserId(user.getId());

        BookDto book = bookRepository.findById(transaction.getBookId())
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "Книга не найдена"));

        if (transaction.isBorrowed()) {
            if (!book.isAvailable()) {
                throw new HttpStatusException(HttpStatus.FORBIDDEN, "Книга уже взята");
            }
        } else {
            if (book.getBorrowBy() != transaction.getUserId()) {
                throw new HttpStatusException(HttpStatus.FORBIDDEN, "Книга взята другим человеком или не была взята");
            }
        }

        if (transaction.isBorrowed()) {
            transaction.setLocation(book.getLocation());

            book.setLocation(null);
            book.setAvailable(false);
            book.setBorrowBy(transaction.getUserId());
        } else {
            if (transaction.getLocation() == null || transaction.getLocation() == "") {
                throw new HttpStatusException(HttpStatus.FORBIDDEN, "Невозможно вернуть книгу без локации");
            }

            book.setLocation(transaction.getLocation());
            book.setAvailable(true);
            book.setBorrowBy(null);
        }

        bookRepository.save(book);
        transactionRepository.save(transaction);
    }
}
