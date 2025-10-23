package com.example.book_exchange.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionDtoTests {

    private TransactionDto transaction;

    @BeforeEach
    public void createTransaction() {
        transaction = new TransactionDto(
                0L,
                1L,
                2L,
                "офис",
                true);
    }

    @Test
    public void testId() {
        Long[] cases = new Long[] {
                12345678L,
                null,
                1L,
                123124234234234234L
        };

        for (int id_case = 0; id_case < cases.length; id_case++) {
            transaction.setId(cases[id_case]);
            assertEquals(cases[id_case], transaction.getId());
        }
    }

    @Test
    public void testUserId() {
        long[] cases = new long[] {
                12345678L,
                0L,
                1L,
                123124234234234234L
        };

        for (int id_case = 0; id_case < cases.length; id_case++) {
            transaction.setUserId(cases[id_case]);
            assertEquals(cases[id_case], transaction.getUserId());
        }
    }

    @Test
    public void testBookId() {
        long[] cases = new long[] {
                12345678L,
                0L,
                1L,
                123124234234234234L
        };

        for (int id_case = 0; id_case < cases.length; id_case++) {
            transaction.setBookId(cases[id_case]);
            assertEquals(cases[id_case], transaction.getBookId());
        }
    }

    @Test
    public void testLocation() {
        String[] cases = new String[] {
                "школа",
                null,
                "офис",
                "кампус"
        };

        for (int id_case = 0; id_case < cases.length; id_case++) {
            transaction.setLocation(cases[id_case]);
            assertEquals(cases[id_case], transaction.getLocation());
        }
    }

    @Test
    public void testIsBorrowed() {
        Boolean[] cases = new Boolean[] {
                true,
                false
        };

        for (int id_case = 0; id_case < cases.length; id_case++) {
            transaction.setBorrowed(cases[id_case]);
            assertEquals(cases[id_case], transaction.isBorrowed());
        }
    }
}