package com.boot.demo.catalog.controller;

import com.boot.demo.catalog.domain.Book;
import com.boot.demo.catalog.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<Book> all() {
        return service.allBooks();
    }

    @GetMapping("{isbn}")
    public Book byIsbn(@PathVariable String isbn) {
        return service.bookDetails(isbn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@Valid @RequestBody Book book) {
        return service.newBook(book);
    }


    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book updateBook(@Valid @RequestBody Book book) {
        return service.updateBook(book);
    }


    @DeleteMapping("{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable String isbn) {
        service.removeBook(isbn);
    }


}