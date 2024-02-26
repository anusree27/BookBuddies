package com.ford.BookBuddies.service;

import com.ford.BookBuddies.entity.BookDetail;
import com.ford.BookBuddies.entity.Cart;
import com.ford.BookBuddies.exception.BookException;

import java.util.List;

public interface BookService {
    Cart addProductToCart(Integer userId, String bookName, Integer quantity) throws Exception;
    List<BookDetail> buyBook(Integer userId, String bookName, Integer quantity)throws Exception;
    Cart subscribeBook();
}












































