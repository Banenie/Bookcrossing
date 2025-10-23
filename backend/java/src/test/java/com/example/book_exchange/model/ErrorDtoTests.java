package com.example.book_exchange.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;

public class ErrorDtoTests {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private ErrorDto error;

    @BeforeEach
    public void createErrorlessError() {
        error = new ErrorDto("message");
        assertEquals(0, validator.validate(error).size());
    }

    @Test
    public void testConstructor() {
        ErrorDto[] errors = new ErrorDto[] {
                new ErrorDto("Problem 0"),
                new ErrorDto(null),
        };

        for (int i = 0; i < errors.length; i++) {
            assertEquals(0, validator.validate(errors[i]).size());
        }
    }

    @Test
    public void testGetMessage() {
        String[] messages = new String[] { "Problem 0", "", null };

        ErrorDto[] errors = new ErrorDto[] {
                new ErrorDto(messages[0]),
                new ErrorDto(messages[1]),
                new ErrorDto(messages[2]),
        };

        for (int i = 0; i < errors.length; i++) {
            assertEquals(messages[i], errors[i].getText());
        }
    }

    @Test
    public void testSetMessage() {
        String[] messages = new String[] { "Problem 0", "", null };

        for (int i = 0; i < messages.length; i++) {
            error.setText(messages[i]);

            assertEquals(0, validator.validate(error).size());
            assertEquals(messages[i], error.getText());
        }
    }
}
