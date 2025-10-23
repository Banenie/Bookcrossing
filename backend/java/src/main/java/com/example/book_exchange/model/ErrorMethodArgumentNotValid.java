package com.example.book_exchange.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorMethodArgumentNotValid {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Field {

        private String field;
        private String error;
    }

    private String message;
    private List<Field> details;
}
