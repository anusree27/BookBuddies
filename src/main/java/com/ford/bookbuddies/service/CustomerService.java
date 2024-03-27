package com.ford.bookbuddies.service;

import com.ford.bookbuddies.entity.*;
import com.ford.bookbuddies.exception.CustomerException;

import java.util.List;


public interface CustomerService {

    Customer createCustomerAccount(Customer newCustomer) throws CustomerException;
    Cart getCart(Integer id) throws CustomerException;
    Customer login(String email, String password)throws CustomerException;
    List<Book> getBooksByCategory(BookCategory category);
    List<Book> displayAllBooks();
    List<BookOrders> getMyOrders(Integer userId)throws CustomerException;

     Customer getUserById(Integer userId) throws CustomerException;

     Customer editCustomerAccount(Customer editCustomer) throws CustomerException;
}
