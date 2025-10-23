package com.example.book_exchange.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;

public class ErrorMethodArgumentNotValidFieldTests {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private ErrorMethodArgumentNotValid.Field field;

    @BeforeEach
    public void createErrorlessField() {
        field = new ErrorMethodArgumentNotValid.Field("field", "error");
        assertEquals(0, validator.validate(field).size());
    }

    @Test
    public void testConstructor() {
        ErrorMethodArgumentNotValid.Field[] fields = new ErrorMethodArgumentNotValid.Field[] {
                new ErrorMethodArgumentNotValid.Field("field", "error"),
                new ErrorMethodArgumentNotValid.Field(null, "error"),
        };

        for (int i = 0; i < fields.length; i++) {
            assertEquals(0, validator.validate(fields[i]).size());
        }
    }

    @Test
    public void testGetField() {
        String[] fieldNames = new String[] { "Problem 0", "", null };

        ErrorMethodArgumentNotValid.Field[] fields = new ErrorMethodArgumentNotValid.Field[] {
                new ErrorMethodArgumentNotValid.Field(fieldNames[0], "error"),
                new ErrorMethodArgumentNotValid.Field(fieldNames[1], "error"),
                new ErrorMethodArgumentNotValid.Field(fieldNames[2], "error"),
        };

        for (int i = 0; i < fields.length; i++) {
            assertEquals(fieldNames[i], fields[i].getField());
        }
    }

    @Test
    public void testSetField() {
        String[] fieldNames = new String[] { "Problem 0", "", null };

        for (int i = 0; i < fieldNames.length; i++) {
            field.setField(fieldNames[i]);

            assertEquals(0, validator.validate(field).size());
            assertEquals(fieldNames[i], field.getField());
        }
    }

    @Test
    public void testGetError() {
        String[] errors = new String[] { "Problem 0", "", null };

        ErrorMethodArgumentNotValid.Field[] fields = new ErrorMethodArgumentNotValid.Field[] {
                new ErrorMethodArgumentNotValid.Field("field", errors[0]),
                new ErrorMethodArgumentNotValid.Field("field", errors[1]),
                new ErrorMethodArgumentNotValid.Field("field", errors[2]),
        };

        for (int i = 0; i < fields.length; i++) {
            assertEquals(errors[i], fields[i].getError());
        }
    }

    @Test
    public void testSetError() {
        String[] errors = new String[] { "Problem 0", "", null };

        for (int i = 0; i < errors.length; i++) {
            field.setError(errors[i]);

            assertEquals(0, validator.validate(field).size());
            assertEquals(errors[i], field.getError());
        }
    }
}
