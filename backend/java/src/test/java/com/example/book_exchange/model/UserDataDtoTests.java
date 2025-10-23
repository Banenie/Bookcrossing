package com.example.book_exchange.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDataDtoTests {

    private UserDataDto userData;

    @BeforeEach
    public void createUserData() {
        userData = new UserDataDto(
                "wasd",
                "wasd");
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
            userData.setLogin(cases[i]);
            assertEquals(cases[i], userData.getLogin());
        }
    }

    @Test
    public void testPassword() {
        String[] cases = new String[] {
                "wasd!wasd",
                null,
                "wasdwasdwasdwasdwasdwasdwasd"
        };

        for (int i = 0; i < cases.length; i++) {
            userData.setPassword(cases[i]);
            assertEquals(cases[i], userData.getPassword());
        }
    }
}