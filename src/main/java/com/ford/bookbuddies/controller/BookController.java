package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.dto.CustomerCartDto;
import com.ford.bookbuddies.entity.BookDetail;
import com.ford.bookbuddies.entity.Cart;
import com.ford.bookbuddies.exception.CartException;
import com.ford.bookbuddies.exception.CustomerException;
import com.ford.bookbuddies.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;



    @PostMapping("book/cart")
    public Cart addBookToCart(@RequestBody CustomerCartDto customerCartDto) throws Exception {
        if(null==customerCartDto) throw new CustomerException("CUSTOMERCARTDTO IS NULL");
        if(null==customerCartDto.getUserId()) throw new CustomerException("User not logged in to add Book To Cart");
        if(null==customerCartDto.getBookName()) throw new CartException("Book name should not be null");
        if (null==customerCartDto.getQuantity()) throw new CartException("Quantity should not be null");
        if (customerCartDto.getQuantity()< 0) throw new CartException("Quantity should be greater than zero");

        return this.bookService.addProductToCart(customerCartDto.getUserId(), customerCartDto.getBookName(),customerCartDto.getQuantity());
    }
    @PostMapping("book/buy")
    public List<BookDetail> buyBook(@RequestBody CustomerCartDto customerCartDto)throws Exception{
        if(null==customerCartDto) throw new CustomerException("CARTDTO IS NULL");
        if(null==customerCartDto.getUserId()) throw new CustomerException("User not logged in to buy Book");
        if(null==customerCartDto.getBookName()) throw new CartException("Book name should not be null");
        if (null==customerCartDto.getQuantity()) throw new CartException("Quantity should not be null");
        if (customerCartDto.getQuantity()< 0) throw new CartException("Quantity should be greater than zero");
        return this.bookService.buyBook(customerCartDto.getUserId(),customerCartDto.getBookName(),customerCartDto.getQuantity());
    }

}
