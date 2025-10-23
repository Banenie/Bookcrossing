package com.example.book_exchange.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import com.example.book_exchange.model.BookDto;
import com.example.book_exchange.service.BookService;

@RestController
@Tag(name = "Books", description = "Books API")
@CrossOrigin("${REACT_URL:http://localhost:6767}")
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Получить киниги")
    @GetMapping
    public List<BookDto> getBooks(@RequestParam(required = false) String location,
            @RequestParam(required = false) Boolean isAvailable,
            @RequestHeader("Authorization") String token) {

        return bookService.getBooks(location, isAvailable, token);
    }

    @Operation(summary = "Загрузить книгу")
    @PostMapping
    public void uploadBook(@RequestBody @Valid BookDto book,
            @RequestHeader("Authorization") String token) {
        bookService.uploadBook(book, token);
    }

    @Operation(summary = "Получение книги по id")
    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable long id,
            @RequestHeader("Authorization") String token) {

        return bookService.getBookById(id, token);
    }

    @Operation(summary = "Получение книги по id")
    @GetMapping("/borrowBy/{id}")
    public List<BookDto> getBooksByBorrowBy(@PathVariable long id,
            @RequestHeader("Authorization") String token) {

        return bookService.getBooksByBorrowBy(id, token);
    }
}
