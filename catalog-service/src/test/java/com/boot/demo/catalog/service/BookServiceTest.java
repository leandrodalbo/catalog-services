package com.boot.demo.catalog.service;

import com.boot.demo.catalog.domain.Book;
import com.boot.demo.catalog.exceptions.AlreadyExistException;
import com.boot.demo.catalog.exceptions.NotFoundException;
import com.boot.demo.catalog.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private final Book book = Book.of("ABC112", "ANY", "ANY", "ANY");
    @Mock
    private BookRepository repository;
    @InjectMocks
    private BookService service;

    @Test
    void willReturnAllBooks() {
        given(repository.findAll()).willReturn(List.of(book));
        List<Book> result = new ArrayList<>();

        service.allBooks().forEach(result::add);

        assertThat(result.isEmpty()).isFalse();
        verify(repository, times(1)).findAll();

    }

    @Test
    void willReturnABookByIsbn() {
        given(repository.findByIsbn(anyString())).willReturn(Optional.of(book));

        var result = service.bookDetails("ABC112");

        assertThat(result).isEqualTo(book);
        verify(repository, times(1)).findByIsbn(anyString());
    }

    @Test
    void willThrowExceptionWhenTheBookIsNotFound() {
        given(repository.findByIsbn(anyString())).willReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(
                () -> service.bookDetails("ABC11d")
        );
        verify(repository, times(1)).findByIsbn(anyString());
    }

    @Test
    void shouldNotSaveItIfAlreadyExists() {
        given(repository.existsByIsbn(anyString())).willReturn(true);

        assertThatExceptionOfType(AlreadyExistException.class).isThrownBy(
                () -> service.newBook(book)
        );

        verify(repository, times(0)).save(any());

    }

    @Test
    void shouldSaveANewBook() {
        given(repository.existsByIsbn(anyString())).willReturn(false);

        service.newBook(book);

        verify(repository, times(1)).save(book);
    }


    @Test
    void shouldDeleteAnExistingBook() {
        given(repository.existsByIsbn(anyString())).willReturn(true);

        service.removeBook(anyString());

        verify(repository, times(1)).deleteByIsbn(anyString());

    }

    @Test
    void shouldNotDeleteItIFNotFound() {
        given(repository.existsByIsbn(anyString())).willReturn(false);

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(
                () -> service.removeBook(anyString())
        );

        verify(repository, times(0)).deleteByIsbn(anyString());
    }


    @Test
    void shouldUpdateAnExistingBook() {
        given(repository.findByIsbn(anyString())).willReturn(Optional.of(book));
        var updating = Book.of("ABC112", "updated", "updated", "updated");

        service.updateBook(updating);

        verify(repository, times(1)).save(updating);
    }

    @Test
    void shouldNotUpdateItIFNotFound() {
        given(repository.findByIsbn(anyString())).willReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(
                () -> service.updateBook(book)
        );

        verify(repository, times(0)).save(any());
    }


}
