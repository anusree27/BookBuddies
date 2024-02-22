package com.ford.BookBuddies.controller;

import com.ford.BookBuddies.dto.CustomerCartDto;
import com.ford.BookBuddies.entity.Cart;
import com.ford.BookBuddies.service.BookService;
import com.ford.BookBuddies.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("book/cart")
    public Cart addBookToCart(@RequestBody CustomerCartDto customerCartDto) {
        return this.bookService.addProductToCart(customerService.getCustomerLoginId(), customerCartDto.getBookName(),customerCartDto.getQuantity());
    }


}
