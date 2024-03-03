package com.ford.bookbuddies.service;

import com.ford.bookbuddies.entity.Cart;

public interface DeleteService {
    Cart deleteProductFromCart(Integer userId, Integer bookId)throws Exception;
}
