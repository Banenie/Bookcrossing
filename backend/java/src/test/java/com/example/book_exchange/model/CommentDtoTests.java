package com.example.book_exchange.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommentDtoTests {

    private CommentDto comment;

    @BeforeEach
    public void createComment() {
        comment = new CommentDto(
                1L,
                2L,
                3L,
                "wasd",
                "wasdwasdwasd");
    }

    @Test
    public void testId() {
        Long[] cases = new Long[] {
                12345678L,
                null,
                1L,
                123124234234234234L
        };

        for (int i = 0; i < cases.length; i++) {
            comment.setId(cases[i]);
            assertEquals(cases[i], comment.getId());
        }
    }

    @Test
    public void testUserId() {
        Long[] cases = new Long[] {
                12345678L,
                null,
                1L,
                123124234234234234L
        };

        for (int i = 0; i < cases.length; i++) {
            comment.setUserId(cases[i]);
            assertEquals(cases[i], comment.getUserId());
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

        for (int i = 0; i < cases.length; i++) {
            comment.setBookId(cases[i]);
            assertEquals(cases[i], comment.getBookId());
        }
    }

    @Test
    public void testTitle() {
        String[] cases = new String[] {
                null,
                "1234",
                "wasd",
                "",
                "wasdwasdwasd"
        };

        for (int i = 0; i < cases.length; i++) {
            comment.setTitle(cases[i]);
            assertEquals(cases[i], comment.getTitle());
        }
    }

    @Test
    public void testText() {
        String[] cases = new String[] {
                null,
                "1234",
                "wasd",
        };

        for (int i = 0; i < cases.length; i++) {
            comment.setText(cases[i]);
            assertEquals(cases[i], comment.getText());
        }
    }
}