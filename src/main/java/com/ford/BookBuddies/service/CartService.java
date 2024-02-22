package com.ford.BookBuddies.service;

import com.ford.BookBuddies.entity.BookDetail;
import com.ford.BookBuddies.entity.Cart;
import com.ford.BookBuddies.exception.CustomerException;

import java.util.List;

public interface CartService {
    List<BookDetail> buyBooksinCart(List<Integer>list) throws CustomerException;
    Cart deleteProductFromCart(Integer userId,String bookName);
    Cart increaseQuantity(Integer bookId);
    Cart decreaseQuantity(Integer bookId);
    List<BookDetail> getItemsToBuy();
}
