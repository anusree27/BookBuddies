package com.ford.BookBuddies.controller;

import com.ford.BookBuddies.entity.BookDetail;
import com.ford.BookBuddies.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentController {
    @Autowired
    private CartService cartService;
    @GetMapping
    public List<BookDetail> getOrderedBooks(){
        return cartService.getItemsToBuy();
    }
}
