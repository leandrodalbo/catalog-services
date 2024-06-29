package com.catalog.orders.domain;

public record Book(

        Long id,
        String isbn,
        String title,
        String author,
        String price,
        Integer version
) {
}