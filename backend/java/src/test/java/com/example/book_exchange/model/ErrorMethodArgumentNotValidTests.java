package com.example.book_exchange.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;

public class ErrorMethodArgumentNotValidTests {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private ErrorMethodArgumentNotValid error;

    @BeforeEach
    public void createErrorlessError() {
        error = new ErrorMethodArgumentNotValid("message", new ArrayList<>());
        assertEquals(0, validator.validate(error).size());
    }

    @Test
    public void testConstructor() {
        ErrorMethodArgumentNotValid[] errors = new ErrorMethodArgumentNotValid[] {
                new ErrorMethodArgumentNotValid("Problem 0", new ArrayList<>()),
                new ErrorMethodArgumentNotValid(null, new ArrayList<>()),
        };

        for (int i = 0; i < errors.length; i++) {
            assertEquals(0, validator.validate(errors[i]).size());
        }
    }

    @Test
    public void testGetMessage() {
        String[] messages = new String[] { "Problem 0", "", null };

        ErrorMethodArgumentNotValid[] errors = new ErrorMethodArgumentNotValid[] {
                new ErrorMethodArgumentNotValid(messages[0], new ArrayList<>()),
                new ErrorMethodArgumentNotValid(messages[1], new ArrayList<>()),
                new ErrorMethodArgumentNotValid(messages[2], new ArrayList<>()),
        };

        for (int i = 0; i < errors.length; i++) {
            assertEquals(messages[i], errors[i].getMessage());
        }
    }

    @Test
    public void testSetMessage() {
        String[] messages = new String[] { "Problem 0", "", null };

        for (int i = 0; i < messages.length; i++) {
            error.setMessage(messages[i]);

            assertEquals(0, validator.validate(error).size());
            assertEquals(messages[i], error.getMessage());
        }
    }

    @Test
    public void testGetDetails() {
        List<ErrorMethodArgumentNotValid.Field>[] details = new List[] {
                null,
                new ArrayList<ErrorMethodArgumentNotValid.Field>(),
        };

        ErrorMethodArgumentNotValid[] errors = new ErrorMethodArgumentNotValid[] {
                new ErrorMethodArgumentNotValid("Problem 0", details[0]),
                new ErrorMethodArgumentNotValid("Problem 0", details[1]),
        };

        for (int i = 0; i < errors.length; i++) {
            assertEquals(details[i], errors[i].getDetails());
        }
    }

    @Test
    public void testSetDetails() {
        List<ErrorMethodArgumentNotValid.Field>[] details = new List[] {
                null,
                new ArrayList<ErrorMethodArgumentNotValid.Field>(),
        };

        for (int i = 0; i < details.length; i++) {
            error.setDetails(details[i]);

            assertEquals(0, validator.validate(error).size());
            assertEquals(details[i], error.getDetails());
        }
    }
}
