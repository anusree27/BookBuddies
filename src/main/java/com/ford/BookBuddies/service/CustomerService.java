package com.ford.BookBuddies.service;

import com.ford.BookBuddies.entity.Book;
import com.ford.BookBuddies.entity.BookCategory;
import com.ford.BookBuddies.entity.Cart;
import com.ford.BookBuddies.entity.Customer;
import com.ford.BookBuddies.exception.CustomerException;

import java.util.List;


public interface CustomerService {

    // create customer account
    //customer login
    //add books to cart -
    //view book by category
    //get order history
    //get best-selling books
    Customer createCustomerAccount(Customer newCustomer) throws CustomerException;

//    Cart addProductToCart(Integer userId, String bookName, Integer quantity);
//    Cart deleteProductFromCart(Integer userId,String bookName);
    Cart getCart(Integer id) throws CustomerException;
    Customer login(String email, String password)throws CustomerException;
   void setCustomerLoginId(Integer user_id);
    Integer getCustomerLoginId();
    List<Book> getBooksByCategory(BookCategory category);
}
