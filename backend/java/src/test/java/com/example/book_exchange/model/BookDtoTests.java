package com.example.book_exchange.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookDtoTests {

    private BookDto book;

    @BeforeEach
    public void createBook() {
        book = new BookDto(
                0l,
                "qwerty",
                "keybord",
                "офис",
                true,
                1l);
    }

    @Test
    public void testId() {
        Long[] cases = new Long[] {
                12345678l,
                null,
                1l,
                123124234234234234l
        };

        for (int id_case = 0; id_case < cases.length; id_case++) {
            book.setId(cases[id_case]);
            assertEquals(cases[id_case], book.getId());
        }
    }

    @Test
    public void testTitle() {
        String[] cases = new String[] {
                "peppa",
                null,
                "1234",
                "wasd"
        };

        for (int id_case = 0; id_case < cases.length; id_case++) {
            book.setTitle(cases[id_case]);
            assertEquals(cases[id_case], book.getTitle());
        }
    }

    @Test
    public void testAuthor() {
        String[] cases = new String[] {
                "peppa",
                null,
                "1234",
                "wasd"
        };

        for (int id_case = 0; id_case < cases.length; id_case++) {
            book.setAuthor(cases[id_case]);
            assertEquals(cases[id_case], book.getAuthor());
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
            book.setLocation(cases[id_case]);
            assertEquals(cases[id_case], book.getLocation());
        }
    }

    @Test
    public void testAvailable() {
        Boolean[] cases = new Boolean[] {
                true,
                false
        };

        for (int id_case = 0; id_case < cases.length; id_case++) {
            book.setAvailable(cases[id_case]);
            assertEquals(cases[id_case], book.isAvailable());
        }
    }

    @Test
    public void testBorrowBy() {
        Long[] cases = new Long[] {
                12345678l,
                null,
                1l,
                123124234234234234l
        };

        for (int id_case = 0; id_case < cases.length; id_case++) {
            book.setBorrowBy(cases[id_case]);
            assertEquals(cases[id_case], book.getBorrowBy());
        }
    }

}
