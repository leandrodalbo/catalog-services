package com.boot.demo.catalog.domain;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public record Book(

        @Id
        Long id,
        @NotBlank(message = "isbn is required")
        String isbn,

        @NotBlank(message = "title is required")
        String title,
        @NotBlank(message = "author is required")
        String author,
        @NotBlank(message = "price is required")
        String price,

        @Version
        Integer version
) {

    public static Book of(
            String isbn,
            String title,
            String author,
            String price
    ) {
        return new Book(null, isbn, title, author, price, 0);
    }
}
