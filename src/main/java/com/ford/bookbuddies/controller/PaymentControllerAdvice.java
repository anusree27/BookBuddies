package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.exception.PaymentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class PaymentControllerAdvice {
    @ExceptionHandler(value = {PaymentException.class})
    public ResponseEntity<String> handleAccountException(PaymentException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}


