package com.boot.demo.catalog.exceptions;

public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException() {
        super("Item already exists");
    }
}
