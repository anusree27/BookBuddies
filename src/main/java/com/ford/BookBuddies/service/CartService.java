package com.ford.BookBuddies.service;

import com.ford.BookBuddies.entity.BookDetail;
import com.ford.BookBuddies.entity.Cart;
import com.ford.BookBuddies.exception.CustomerException;

import java.util.List;

public interface CartService {
    List<BookDetail> buyBooks(List<Integer>list) throws CustomerException;
    void subsribeBooks();
    Cart increaseQuantity();
    Cart decreaseQuantity();
}
