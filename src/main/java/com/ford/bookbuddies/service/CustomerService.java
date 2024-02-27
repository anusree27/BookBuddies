package com.ford.bookbuddies.service;

import com.ford.bookbuddies.entity.Book;
import com.ford.bookbuddies.entity.BookCategory;
import com.ford.bookbuddies.entity.Cart;
import com.ford.bookbuddies.entity.Customer;
import com.ford.bookbuddies.exception.BookException;
import com.ford.bookbuddies.exception.CartException;
import com.ford.bookbuddies.exception.CustomerException;

import java.util.List;


public interface CustomerService {

    // create customer account -
    //customer login -
    //add books to cart -
    //view book by category -
    //get order history
    //get best-selling books
    Customer createCustomerAccount(Customer newCustomer) throws CustomerException;

    Cart addProductToCart(Integer userId, String bookName, Integer quantity) throws CustomerException, BookException, CartException;
    Cart getCart(Integer id) throws CustomerException;
    Customer login(String email, String password)throws CustomerException;
   void setCustomerLoginId(Integer user_id);
    Integer getCustomerLoginId();
    List<Book> getBooksByCategory(BookCategory category);
}
