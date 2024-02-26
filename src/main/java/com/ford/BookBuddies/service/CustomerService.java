package com.ford.BookBuddies.service;

import com.ford.BookBuddies.entity.Book;
import com.ford.BookBuddies.entity.BookCategory;
import com.ford.BookBuddies.entity.Cart;
import com.ford.BookBuddies.entity.Customer;
import com.ford.BookBuddies.exception.CustomerException;

import java.util.List;


public interface CustomerService {
    Customer createCustomerAccount(Customer newCustomer) throws CustomerException;

    Cart getCart(Integer id) throws CustomerException;
    Customer login(String email, String password)throws CustomerException;

    List<Book> getBooksByCategory(BookCategory category);
}
