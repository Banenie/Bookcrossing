package com.example.book_exchange.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.book_exchange.model.CommentDto;

@Repository
public interface CommentRepository extends JpaRepository<CommentDto, Long> {

    public List<CommentDto> findByBookId(long bookId);
}
