package com.ford.BookBuddies.service;

import com.ford.BookBuddies.entity.Cart;

public interface BookService {
    Cart addProductToCart(Integer userId, String bookName, Integer quantity);
    Cart buyBook();
    Cart subscribeBook();
}
