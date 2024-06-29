package com.boot.demo.catalog.exceptions.handler;

import com.boot.demo.catalog.exceptions.AlreadyExistException;
import com.boot.demo.catalog.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AlreadyExistException.class)
    protected ResponseEntity<Object> alreadyExists(AlreadyExistException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> itemNotFound(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
    }

}
