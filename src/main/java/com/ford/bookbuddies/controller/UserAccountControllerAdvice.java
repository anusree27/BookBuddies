package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.exception.CustomerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class UserAccountControllerAdvice {
    @ExceptionHandler(value = {CustomerException.class})
    public ResponseEntity<String> handleAccountException(CustomerException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}


