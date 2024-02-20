package com.ford.BookBuddies.controller;

import com.ford.BookBuddies.entity.BookDetail;
import com.ford.BookBuddies.entity.Cart;
import com.ford.BookBuddies.exception.CustomerException;
import com.ford.BookBuddies.service.CartService;
import com.ford.BookBuddies.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("cart/buy")
    public List<BookDetail> buyBooks(@RequestBody List<Integer> list)throws CustomerException {
        return cartService.buyBooks(list);
    }
}
