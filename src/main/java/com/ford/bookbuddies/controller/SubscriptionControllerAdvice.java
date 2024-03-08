package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.exception.CartException;
import com.ford.bookbuddies.exception.SubscriptionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class SubscriptionControllerAdvice {
    @ExceptionHandler(value = {SubscriptionException.class})
    public ResponseEntity<String> handleAccountException(SubscriptionException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}


