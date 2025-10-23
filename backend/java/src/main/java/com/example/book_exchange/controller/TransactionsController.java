package com.example.book_exchange.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.example.book_exchange.model.TransactionDto;
import com.example.book_exchange.service.TransactionService;

@RestController
@Tag(name = "Transactions", description = "Transactions API")
@CrossOrigin("${REACT_URL:http://localhost:6767}")
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionsController {

    private final TransactionService transactionService;

    @Operation(summary = "Получить все обмены")
    @GetMapping
    public List<TransactionDto> getTransactions(@RequestHeader("Authorization") String token) {
        return transactionService.getTransactions(token);
    }

    @Operation(summary = "Совершить обмен")
    @PostMapping
    public void uploadTransaction(@RequestBody @Valid TransactionDto transaction,
            @RequestHeader("Authorization") String token) {
        transactionService.uploadTransaction(transaction, token);
    }
}
