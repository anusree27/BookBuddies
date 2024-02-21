package com.ford.bookbuddies.service;

import com.ford.bookbuddies.entity.BookDetail;
import com.ford.bookbuddies.entity.Cart;
import com.ford.bookbuddies.exception.CustomerException;

import java.util.List;

public interface CartService {
    //buy books
    //subscribe books
    //
    List<BookDetail> buyBooks(List<Integer>list) throws CustomerException;
    void subsribeBooks();
    Cart increaseQuantity();
    Cart decreaseQuantity();
}
