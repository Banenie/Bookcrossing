package com.example.book_exchange.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.book_exchange.model.BookDto;
import com.example.book_exchange.model.CommentDto;
import com.example.book_exchange.model.UserDto;
import com.example.book_exchange.repository.BookRepository;
import com.example.book_exchange.repository.CommentRepository;
import com.example.book_exchange.service.AuthService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
public class CommentControllerIntegrationTests {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14.1")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CommentRepository commentRepository;

    @MockitoBean
    private AuthService authService;

    private static Map<Long, List<Long>> ids;

    @BeforeAll
    public static void testUploadComment(@Autowired TestRestTemplate testRestTemplate,
            @Autowired AuthService authService, @Autowired BookRepository bookRepository,
            @Autowired CommentRepository commentRepository) {
        Mockito.when(authService.getUserByToken(any())).thenReturn(new UserDto());

        BookDto[] books = new BookDto[] { new BookDto(null, "Name 1", "Author 1", "Офис", true, null),
                new BookDto(null, "Name 2", "Author 2", "Офис", true, null) };
        long[] bookIds = new long[books.length];

        for (int i = 0; i < books.length; i++) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "1234");

            // Отправляем POST-запрос через контроллер
            ResponseEntity<Void> response = testRestTemplate.postForEntity("/books",
                    new HttpEntity<>(books[i], headers), Void.class);

            // Проверяем HTTP-статус и содержимое ответа
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        List<BookDto> savedBooks = bookRepository.findAll();
        assertEquals(books.length, savedBooks.size());

        for (int i = 0; i < books.length; i++) {
            BookDto savedBook = savedBooks.get(i);
            bookIds[i] = savedBook.getId();
        }

        CommentDto[] comments = new CommentDto[] { new CommentDto(null, null, bookIds[0], "Name 1", "Text 1"),
                new CommentDto(null, null, bookIds[1], "Name 2", "Text 2") };
        ids = new HashMap<>();

        for (int i = 0; i < comments.length; i++) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "1234");

            // Отправляем POST-запрос через контроллер
            ResponseEntity<Void> response = testRestTemplate.postForEntity("/comments",
                    new HttpEntity<>(comments[i], headers), Void.class);

            // Проверяем HTTP-статус и содержимое ответа
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        List<CommentDto> savedComments = commentRepository.findAll();
        assertEquals(books.length, savedComments.size());

        for (int i = 0; i < comments.length; i++) {
            CommentDto savedComment = savedComments.get(i);
            List<Long> idsBook = ids.getOrDefault(savedComment.getBookId(), new ArrayList<>());
            idsBook.add(savedComment.getId());
            ids.put(savedComment.getBookId(), idsBook);

            assertEquals(comments[i].getBookId(), savedComment.getBookId(), "ID киниги не совпадает!");
            assertEquals(comments[i].getTitle(), savedComment.getTitle(), "Название не совпадает!");
            assertEquals(comments[i].getText(), savedComment.getText(), "Текст не совпадает!");
        }
    }

    @Test
    public void testGetCommentByBookId() {
        for (Map.Entry<Long, List<Long>> entry : ids.entrySet()) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "1234");

            ResponseEntity<CommentDto[]> response = testRestTemplate.exchange("/comments/book/" + entry.getKey(),
                    HttpMethod.GET, new HttpEntity<>(headers), CommentDto[].class);

            assertEquals(HttpStatus.OK, response.getStatusCode());

            CommentDto[] responseBody = response.getBody();
            assertNotNull(responseBody);
            assertEquals(entry.getValue().size(), responseBody.length);

            for (CommentDto comment : responseBody) {
                CommentDto savedComment = commentRepository.findById(comment.getId()).get();

                assertEquals(entry.getKey(), comment.getId(), "ID книги не совпадает!");
                assertEquals(savedComment.getBookId(), comment.getBookId(), "ID киниги не совпадает!");
                assertEquals(savedComment.getTitle(), comment.getTitle(), "Название не совпадает!");
                assertEquals(savedComment.getText(), comment.getText(), "Текст не совпадает!");
            }
        }
    }
}
