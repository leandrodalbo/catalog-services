package com.catalog.orders.service;

import com.catalog.orders.domain.Book;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class BooksCatalogService {

    private static final String BOOKS = "/books/";
    private final WebClient webClient;

    public BooksCatalogService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Book> byIsbn(String isbn) {
        return webClient.get()
                .uri(BOOKS + isbn)
                .retrieve().bodyToMono(Book.class)
                .timeout(Duration.ofSeconds(1), Mono.empty())
                .onErrorResume(WebClientResponseException.NotFound.class, e -> Mono.empty())
                .retryWhen(Retry.backoff(3, Duration.ofMillis(100)))
                .onErrorResume(Exception.class, e -> Mono.empty());

    }
}
