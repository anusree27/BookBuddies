package com.ford.bookbuddies.service;

import com.ford.bookbuddies.entity.BookDetail;
import com.ford.bookbuddies.entity.Cart;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface BookService {
    Cart addProductToCart(Integer userId, String bookName, Integer quantity) throws Exception;

    List<BookDetail> buyBook(Integer userId, String bookName, Integer quantity) throws Exception;
}












































