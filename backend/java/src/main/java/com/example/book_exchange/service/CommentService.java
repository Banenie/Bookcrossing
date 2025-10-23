package com.example.book_exchange.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

import com.example.book_exchange.exception.HttpStatusException;
import com.example.book_exchange.model.CommentDto;
import com.example.book_exchange.model.UserDto;
import com.example.book_exchange.repository.BookRepository;
import com.example.book_exchange.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final AuthService authService;

    public void uploadComment(@Valid @NotNull CommentDto comment, String token) {
        UserDto user = authService.getUserByToken(token);
        comment.setId(null);
        comment.setUserId(user.getId());

        if (!bookRepository.existsById(comment.getBookId())) {
            throw new HttpStatusException(HttpStatus.NOT_FOUND, "Книга не найдена");
        }

        commentRepository.save(comment);
    }

    public List<CommentDto> getCommentsByBookId(long bookId, String token) {
        authService.checkToken(token);
        return commentRepository.findByBookId(bookId);
    }
}
