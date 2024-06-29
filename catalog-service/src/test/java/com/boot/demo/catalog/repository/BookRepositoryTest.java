package com.boot.demo.catalog.repository;

import com.boot.demo.catalog.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class BookRepositoryTest extends RepositoryTest {

    @Autowired
    BookRepository repository;

    @Autowired
    JdbcAggregateTemplate template;


    @Test
    void findByIsbn() {

        template.insert(Book.of("abc231", "any", "any", "any"));

        var found = repository.findByIsbn("abc231");

        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().id()).isNotNull();
    }

    @Test
    void existsByIsbn() {
        template.insert(Book.of("abc231", "any", "any", "any"));

        assertThat(repository.existsByIsbn("abc231")).isTrue();
    }

    @Test
    void deleteByIsbn() {
        template.insert(Book.of("abc231", "any", "any", "any"));

        repository.deleteByIsbn("abc231");

        assertThat(repository.existsByIsbn("abc231")).isFalse();
    }
}
