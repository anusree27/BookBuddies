package com.ford.BookBuddies.controller;

import com.ford.BookBuddies.dto.Bookdto;
import com.ford.BookBuddies.dto.OrderBooksdto;
import com.ford.BookBuddies.entity.BookDetail;
import com.ford.BookBuddies.entity.Cart;
import com.ford.BookBuddies.exception.BookException;
import com.ford.BookBuddies.exception.CartException;
import com.ford.BookBuddies.exception.CustomerException;
import com.ford.BookBuddies.service.CartService;
import com.ford.BookBuddies.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private CustomerService customerService;

    @PostMapping("cart/buy")
    public List<BookDetail> buyBooks(@RequestBody OrderBooksdto orderBooksdto)throws Exception {
        if(orderBooksdto.getUserId()==null) throw new CustomerException("User not logged in");
        if(orderBooksdto.getIdList().isEmpty()) throw new CartException("Books to Buy is Empty!");
        return cartService.buyBooksinCart(orderBooksdto.getUserId(),orderBooksdto.getIdList());
    }
    @PatchMapping("cart/book/quantity/plus")
    public Cart increasebookQuantity(@RequestBody Bookdto quantitydto)throws Exception {
        if(quantitydto.getUserId()==null) throw new CustomerException("User not logged in");
        if(quantitydto.getBookId()==null) throw new BookException("Book Id can't be null");
        return this.cartService.increaseQuantity(quantitydto.getUserId(),quantitydto.getBookId());
    }
    @PatchMapping("cart/book/quantity/minus")
    public Cart decreasebookQuantity(@RequestBody Bookdto quantitydto)throws Exception{
        if(quantitydto.getUserId()==null) throw new CustomerException("User not logged in");
        if(quantitydto.getBookId()==null) throw new BookException("Book Id can't be null");
        return this.cartService.decreaseQuantity(quantitydto.getUserId(),quantitydto.getBookId());
    }
    @DeleteMapping("cart/{bookName}")
    public Cart deleteProductFromCart(@RequestBody Bookdto quantitydto)throws Exception{
        if(quantitydto.getUserId()==null) throw new CustomerException("User not logged in");
        if(quantitydto.getBookId()==null) throw new BookException("Book Id can't be null");
        return this.cartService.deleteProductFromCart(quantitydto.getUserId(),quantitydto.getBookId());
    }
}
