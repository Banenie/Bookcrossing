package com.example.book_exchange.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.book_exchange.model.BookDto;

@Repository
public interface BookRepository extends JpaRepository<BookDto, Long> {
    public List<BookDto> findByBorrowBy(long borrowBy);

    @Query("SELECT b FROM BookDto b WHERE (:location IS NULL OR b.location ILIKE %:location%) AND (:isAvailable IS NULL OR b.isAvailable = :isAvailable)")
    public List<BookDto> findByFilter(String location, Boolean isAvailable);
}
