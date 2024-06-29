package com.catalog.orders.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;

public record Purchase(
        @Id
        Long id,
        @Column("book_isbn")
        String bookIsbn,
        @Column("book_price")
        Float bookPrice,
        @Column("quantity")
        Integer quantity,
        @Column("status")
        PurchaseStatus status,

        @Version
        Integer version
) {
    public static Purchase of(
            String bookIsbn,
            Float bookPrice,
            Integer quantity,
            PurchaseStatus status) {
        return new Purchase(null, bookIsbn, bookPrice, quantity, status, null);
    }

}
