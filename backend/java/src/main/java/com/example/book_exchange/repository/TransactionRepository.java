package com.example.book_exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.book_exchange.model.TransactionDto;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionDto, Long> {
}
