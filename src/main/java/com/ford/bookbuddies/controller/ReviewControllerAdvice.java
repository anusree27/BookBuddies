package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.exception.CartException;
import com.ford.bookbuddies.exception.ReviewException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ReviewControllerAdvice {
    @ExceptionHandler(value = {ReviewException.class})
    public ResponseEntity<String> handleAccountException(ReviewException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}


