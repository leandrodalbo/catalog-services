package com.boot.demo.catalog.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Item not found");
    }
}
