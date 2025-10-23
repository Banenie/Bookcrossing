package com.example.book_exchange.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDtoTests {

    private UserDto user;

    @BeforeEach
    public void createUser() {
        user = new UserDto(
                1L,
                "wasd",
                false);
    }

    @Test
    public void testId() {
        long[] cases = new long[] {
                12345678L,
                0L,
                1L
        };

        for (int i = 0; i < cases.length; i++) {
            user.setId(cases[i]);
            assertEquals(cases[i], user.getId());
        }
    }

    @Test
    public void testLogin() {
        String[] cases = new String[] {
                "wasd",
                null,
                "1234",
                "wasd@email.com",
        };

        for (int i = 0; i < cases.length; i++) {
            user.setLogin(cases[i]);
            assertEquals(cases[i], user.getLogin());
        }
    }

    @Test
    public void testIsAdmin() {
        boolean[] cases = new boolean[] {
                true,
                false
        };

        for (int i = 0; i < cases.length; i++) {
            user.setAdmin(cases[i]);
            assertEquals(cases[i], user.isAdmin());
        }
    }
}
