package com.ford.BookBuddies.service;

import com.ford.BookBuddies.entity.BookDetail;
import com.ford.BookBuddies.entity.Cart;
import com.ford.BookBuddies.exception.CustomerException;

import java.util.List;

public interface CartService {
    List<BookDetail> buyBooksinCart(Integer userId,List<Integer>list) throws Exception;
    Cart deleteProductFromCart(Integer userId,Integer bookId)throws Exception;
    Cart increaseQuantity(Integer userId, Integer bookId)throws Exception;
    Cart decreaseQuantity(Integer userId, Integer bookId)throws Exception;
//    List<BookDetail> getItemsToBuy();
}
