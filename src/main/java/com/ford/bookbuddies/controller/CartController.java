package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.entity.BookDetail;
import com.ford.bookbuddies.exception.CartException;
import com.ford.bookbuddies.exception.CustomerException;
import com.ford.bookbuddies.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("cart/buy")
    public List<BookDetail> buyBooks(@RequestBody List<Integer> list) throws CustomerException, CartException {
        return cartService.buyBooks(list);
    }
}
