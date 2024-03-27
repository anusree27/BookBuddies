package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.dto.CustomerCartDto;
import com.ford.bookbuddies.entity.BookDetail;
import com.ford.bookbuddies.entity.Cart;
import com.ford.bookbuddies.exception.CartException;
import com.ford.bookbuddies.exception.CustomerException;
import com.ford.bookbuddies.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
@RestController
public class BookController {
    @Autowired
    private BookService bookService;
    @PostMapping("book/cart")
    public Cart addBookToCart(@RequestBody CustomerCartDto customerCartDto) throws Exception {
        if(null==customerCartDto) throw new CustomerException("CUSTOMERCARTDTO IS NULL");
        if(null==customerCartDto.getUserId() ||customerCartDto.getUserId()==0) throw new CustomerException("User not logged in to add Book To Cart");
        if(null==customerCartDto.getBookId()) throw new CartException("Book Id should not be null");
        if (null==customerCartDto.getQuantity()) throw new CartException("Quantity should not be null");
        if (customerCartDto.getQuantity()< 0) throw new CartException("Quantity should be greater than zero");

        return this.bookService.addProductToCart(customerCartDto.getUserId(), customerCartDto.getBookId(),customerCartDto.getQuantity());
    }
    @PostMapping("book/buy")
    public List<BookDetail> buyBook(@RequestBody CustomerCartDto customerCartDto)throws Exception{
        if(null==customerCartDto) throw new CustomerException("CARTDTO IS NULL");
        if(null==customerCartDto.getUserId()) throw new CustomerException("User not logged in to buy Book");
        if(null==customerCartDto.getBookId()) throw new CartException("Book Id should not be null");
        if (null==customerCartDto.getQuantity()) throw new CartException("Quantity should not be null");
        if (customerCartDto.getQuantity()< 0) throw new CartException("Quantity should be greater than zero");
        return this.bookService.buyBook(customerCartDto.getUserId(),customerCartDto.getBookId(),customerCartDto.getQuantity());
    }
}
