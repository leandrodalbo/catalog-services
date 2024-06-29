package com.catalog.orders.dto;

import jakarta.validation.constraints.NotNull;

public record PurchaseDto(

        String bookIsbn,
        @NotNull
        Float bookPrice,
        @NotNull
        Integer quantity) {

}
