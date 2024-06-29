package com.boot.demo.catalog.repository;

import com.boot.demo.catalog.domain.Book;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);


    @Modifying
    @Transactional
    @Query("DELETE FROM Book WHERE isbn = :isbn")
    void deleteByIsbn(String isbn);

}
