package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.exception.StockManagerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StockManagerControllerAdvice {
    @ExceptionHandler(value = {StockManagerException.class})
    public ResponseEntity<String> handleAccountException(StockManagerException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
