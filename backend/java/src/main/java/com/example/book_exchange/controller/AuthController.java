package com.example.book_exchange.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import com.example.book_exchange.model.UserDataDto;
import com.example.book_exchange.model.UserDto;
import com.example.book_exchange.service.AuthService;

@RestController
@Tag(name = "Auth", description = "Auth API")
@CrossOrigin("${REACT_URL:http://localhost:6767}")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Получить пользователя по токену")
    @GetMapping("/token")
    public UserDto getUserByToken(@RequestHeader("Authorization") String token) {
        return authService.getUserByToken(token);
    }

    @Operation(summary = "Получить токен")
    @PostMapping("/token")
    public String signInOrSignUp(@RequestBody @Valid UserDataDto userData) {
        return authService.signInOrSignUp(userData);
    }
}
