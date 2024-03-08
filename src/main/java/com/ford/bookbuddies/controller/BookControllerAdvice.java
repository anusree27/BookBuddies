package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.exception.BookException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookControllerAdvice {
    @ExceptionHandler(value = {BookException.class})
    public ResponseEntity<String> handleAccountException(BookException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
