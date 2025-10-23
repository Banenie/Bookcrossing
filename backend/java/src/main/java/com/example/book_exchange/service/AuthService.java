package com.example.book_exchange.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;

import com.example.book_exchange.exception.HttpStatusException;
import com.example.book_exchange.model.UserDataDto;
import com.example.book_exchange.model.UserDto;
import com.example.book_exchange.protobuf.Token;
import com.example.book_exchange.protobuf.User;
import com.example.book_exchange.protobuf.UserData;
import com.example.book_exchange.protobuf.UserServiceGrpc;
import com.example.book_exchange.protobuf.UserServiceGrpc.UserServiceBlockingStub;

@Service
@RequiredArgsConstructor
public class AuthService {

    ManagedChannel channel;
    UserServiceBlockingStub stub;

    @PostConstruct
    public void postConstruct() {
        String goURL = System.getenv("GO_URL");

        if (goURL == null) {
            goURL = "localhost:6789";
        }

        channel = ManagedChannelBuilder
                .forTarget(goURL)
                .usePlaintext()
                .build();

        stub = UserServiceGrpc.newBlockingStub(channel);
    }

    @PreDestroy
    public void preDestroy() {
        channel.shutdown();
    }

    public String signInOrSignUp(UserDataDto userData) {
        UserData userDataProto = UserData
                .newBuilder()
                .setLogin(userData.getLogin())
                .setPassword(userData.getPassword())
                .build();

        try {
            return stub.signInOrSignUp(userDataProto).getToken();
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.INVALID_ARGUMENT) {
                throw new HttpStatusException(HttpStatus.UNAUTHORIZED, "Не удалось получить или создать пользователя");
            }
            throw new HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при авторизации");
        }
    }

    public UserDto getUserByToken(String token) {
        Token tokenProto = Token
                .newBuilder()
                .setToken(token)
                .build();

        try {
            User user = stub.getUserByToken(tokenProto);
            return new UserDto(user.getId(), user.getLogin(), user.getIsAdmin());
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.INVALID_ARGUMENT) {
                throw new HttpStatusException(HttpStatus.UNAUTHORIZED, "Не удалось получить или создать пользователя");
            }
            throw new HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при авторизации");
        }
    }

    public void checkToken(String token) {
        getUserByToken(token);
    }
}
