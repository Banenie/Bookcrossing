package com.example.book_exchange.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.book_exchange.exception.HttpStatusException;
import com.example.book_exchange.model.ErrorDto;
import com.example.book_exchange.model.ErrorMethodArgumentNotValid;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(HttpStatusException.class)
    public ResponseEntity<ErrorDto> handleHttpStatusEx(HttpStatusException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(new ErrorDto(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMethodArgumentNotValid> handleHttpStatusEx(MethodArgumentNotValidException e) {
        ArrayList<ErrorMethodArgumentNotValid.Field> fields = new ArrayList<ErrorMethodArgumentNotValid.Field>();
        for (FieldError field : e.getFieldErrors()) {
            fields.add(new ErrorMethodArgumentNotValid.Field(field.getField(), field.getDefaultMessage()));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMethodArgumentNotValid("Validation error", fields));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto(e.getMessage()));
    }
}
