package com.example.book_exchange.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;

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
import com.example.book_exchange.model.UserDto;
import com.example.book_exchange.repository.BookRepository;
import com.example.book_exchange.service.AuthService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
public class BookControllerIntegrationTests {

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
    private BookRepository bookRepository;

    @MockitoBean
    private AuthService authService;

    private static long[] ids;

    @BeforeAll
    public static void testUploadBook(@Autowired TestRestTemplate testRestTemplate, @Autowired AuthService authService,
            @Autowired BookRepository bookRepository) {
        Mockito.when(authService.getUserByToken(any())).thenReturn(new UserDto());

        BookDto[] books = new BookDto[] { new BookDto(null, "Name 1", "Author 1", "Офис", true, null),
                new BookDto(null, "Name 2", "Author 2", "Офис", true, null) };
        ids = new long[books.length];

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
            ids[i] = savedBook.getId();

            assertEquals(books[i].getTitle(), savedBook.getTitle(), "Название не совпадает!");
            assertEquals(books[i].getAuthor(), savedBook.getAuthor(), "Автор не совпадает!");
            assertEquals(books[i].getLocation(), savedBook.getLocation(), "Локация не совпадает!");
            assertEquals(books[i].isAvailable(), savedBook.isAvailable(), "Доступность не совпадает!");
        }
    }

    @Test
    public void testGetBooks() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "1234");

        ResponseEntity<BookDto[]> response = testRestTemplate.exchange("/books", HttpMethod.GET,
                new HttpEntity<>(headers), BookDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        BookDto[] responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(ids.length, responseBody.length);

        for (BookDto book : responseBody) {
            Long id = book.getId();
            BookDto savedBook = bookRepository.findById(id).get();

            assertEquals(savedBook.getTitle(), book.getTitle(), "Название не совпадает!");
            assertEquals(savedBook.getAuthor(), book.getAuthor(), "Автор не совпадает!");
            assertEquals(savedBook.getLocation(), book.getLocation(), "Локация не совпадает!");
            assertEquals(savedBook.isAvailable(), book.isAvailable(), "Доступность не совпадает!");
        }
    }

    @Test
    public void testGetBookById() {
        for (Long id : ids) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "1234");

            ResponseEntity<BookDto> response = testRestTemplate.exchange("/books/" + id, HttpMethod.GET,
                    new HttpEntity<>(headers), BookDto.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());

            BookDto responseBody = response.getBody();
            assertNotNull(responseBody);

            BookDto savedBook = bookRepository.findById(id).get();

            assertEquals(id, responseBody.getId(), "ID книги не совпадает!");
            assertEquals(savedBook.getTitle(), responseBody.getTitle(), "Название не совпадает!");
            assertEquals(savedBook.getAuthor(), responseBody.getAuthor(), "Автор не совпадает!");
            assertEquals(savedBook.getLocation(), responseBody.getLocation(), "Локация не совпадает!");
            assertEquals(savedBook.isAvailable(), responseBody.isAvailable(), "Доступность не совпадает!");
        }
    }
}
