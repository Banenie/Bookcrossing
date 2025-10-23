package com.example.book_exchange.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import com.example.book_exchange.model.CommentDto;
import com.example.book_exchange.service.CommentService;

@RestController
@Tag(name = "Comments", description = "Comments API")
@CrossOrigin("${REACT_URL:http://localhost:6767}")
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Оставить отзыв")
    @PostMapping
    public void uploadComment(@RequestBody @Valid CommentDto comment,
            @RequestHeader("Authorization") String token) {
        commentService.uploadComment(comment, token);
    }

    @Operation(summary = "Получить отзывы о книге")
    @GetMapping("/book/{bookId}")
    public List<CommentDto> getCommentsByBookId(@PathVariable long bookId,
            @RequestHeader("Authorization") String token) {
        return commentService.getCommentsByBookId(bookId, token);
    }
}
