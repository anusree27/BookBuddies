package com.ford.BookBuddies.controller;

import com.ford.BookBuddies.dto.Bookdto;
import com.ford.BookBuddies.dto.CustomerCartDto;
import com.ford.BookBuddies.entity.BookDetail;
import com.ford.BookBuddies.entity.Cart;
import com.ford.BookBuddies.exception.BookException;
import com.ford.BookBuddies.exception.CartException;
import com.ford.BookBuddies.exception.CustomerException;
import com.ford.BookBuddies.service.BookService;
import com.ford.BookBuddies.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("book/cart")
    public Cart addBookToCart(@RequestBody CustomerCartDto customerCartDto) throws Exception {
        if(customerCartDto.getUserId()==null) throw new CustomerException("User not logged in to add Book To Cart");
        if(customerCartDto.getBookName()==null) throw new CartException("Book name should not be null");
        if (customerCartDto.getQuantity()== null) throw new CartException("Quantity should not be null");
        if (customerCartDto.getQuantity()< 0) throw new CartException("Quantity should be greater than zero");

        return this.bookService.addProductToCart(customerCartDto.getUserId(), customerCartDto.getBookName(),customerCartDto.getQuantity());
    }
    @PostMapping("book/buy")
    public List<BookDetail> buyBook(@RequestBody CustomerCartDto customerCartDto)throws Exception{
        if(customerCartDto.getUserId()==null) throw new CustomerException("User not logged ");
        if(customerCartDto.getBookName()==null) throw new CartException("Book name should not be null");
        if (customerCartDto.getQuantity()== null) throw new CartException("Quantity should not be null");
        if (customerCartDto.getQuantity()< 0) throw new CartException("Quantity should be greater than zero");
        return this.bookService.buyBook(customerCartDto.getUserId(),customerCartDto.getBookName(),customerCartDto.getQuantity());
    }

}
