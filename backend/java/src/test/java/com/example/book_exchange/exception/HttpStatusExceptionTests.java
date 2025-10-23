package com.example.book_exchange.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class HttpStatusExceptionTests {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private HttpStatusException exception;

    @BeforeEach
    public void createErrorlessException() {
        exception = new HttpStatusException(HttpStatus.OK, "Message");
        assertEquals(0, validator.validate(exception).size());
    }

    @Test
    public void testConstructor() {
        HttpStatusException[] exceptions = new HttpStatusException[] {
                new HttpStatusException(HttpStatus.BAD_REQUEST, "Oh oh"),
                new HttpStatusException(HttpStatus.CONFLICT, null),
        };

        for (int i = 0; i < exceptions.length; i++) {
            assertEquals(0, validator.validate(exceptions[i]).size());
        }
    }

    @Test
    public void testGetHttpStatus() {
        HttpStatus[] httpStatuses = new HttpStatus[] { HttpStatus.FAILED_DEPENDENCY, HttpStatus.GATEWAY_TIMEOUT };

        HttpStatusException[] exceptions = new HttpStatusException[] {
                new HttpStatusException(httpStatuses[0], "Oh oh"),
                new HttpStatusException(httpStatuses[1], "Oh oh"),
        };

        for (int i = 0; i < exceptions.length; i++) {
            assertEquals(httpStatuses[i], exceptions[i].getHttpStatus());
        }
    }

    @Test
    public void testGetMessage() {
        String[] messages = new String[] { "Oh oh\"", "", null };

        HttpStatusException[] exceptions = new HttpStatusException[] {
                new HttpStatusException(HttpStatus.OK, messages[0]),
                new HttpStatusException(HttpStatus.OK, messages[1]),
                new HttpStatusException(HttpStatus.OK, messages[2]),
        };

        for (int i = 0; i < exceptions.length; i++) {
            assertEquals(messages[i], exceptions[i].getMessage());
        }
    }
}