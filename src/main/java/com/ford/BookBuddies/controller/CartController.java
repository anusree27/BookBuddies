package com.ford.BookBuddies.controller;

import com.ford.BookBuddies.entity.BookDetail;
import com.ford.BookBuddies.entity.Cart;
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
    public List<BookDetail> buyBooks(@RequestBody List<Integer> list)throws CustomerException {
        return cartService.buyBooksinCart(list);
    }
    @PatchMapping("cart/book/quantity/plus/{bookId}")
    public Cart increasebookQuantity(@PathVariable Integer bookId){
        return this.cartService.increaseQuantity(bookId);
    }
    @PatchMapping("cart/book/quantity/minus/{bookId}")
    public Cart decreasebookQuantity(@PathVariable Integer bookId){
        return this.cartService.decreaseQuantity(bookId);
    }
    @DeleteMapping("cart/{bookName}")
    public Cart deleteProductFromCart(@PathVariable String bookName){
        return this.cartService.deleteProductFromCart(customerService.getCustomerLoginId(),bookName);
    }
}
