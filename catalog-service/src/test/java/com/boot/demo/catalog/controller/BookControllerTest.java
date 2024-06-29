package com.boot.demo.catalog.controller;

import com.boot.demo.catalog.domain.Book;
import com.boot.demo.catalog.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(BookController.class)

public class BookControllerTest {

    private final Book book = Book.of("abc123", "someTitle", "someAuthor", "23.21");
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookService service;

    @Test
    void shouldSaveAnewBook() throws Exception {
        given(service.newBook(book)).willReturn(book);

        MockHttpServletResponse res = mvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(book)))
                .andReturn().getResponse();

        then(res.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        verify(service, times(1)).newBook(any());
    }

    @Test
    void shouldUpdateABook() throws Exception {
        given(service.updateBook(book)).willReturn(book);

        MockHttpServletResponse res = mvc.perform(put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(book)))
                .andReturn().getResponse();

        then(res.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        verify(service, times(1)).updateBook(any());
    }

    @Test
    void shouldDeleteABook() throws Exception {
        doNothing().when(service).removeBook(anyString());

        MockHttpServletResponse res = mvc.perform(delete("/books/abc123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        then(res.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());

        verify(service, times(1)).removeBook(anyString());
    }

    @Test
    void shouldGetABookByISBN() throws Exception {
        given(service.bookDetails(anyString())).willReturn(book);

        MockHttpServletResponse res = mvc.perform(get("/books/abc123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        then(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(service, times(1)).bookDetails(anyString());
    }

    @Test
    void shouldGetAllBooks() throws Exception {
        given(service.allBooks()).willReturn(List.of(book));

        MockHttpServletResponse res = mvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        then(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(service, times(1)).allBooks();
    }
}
