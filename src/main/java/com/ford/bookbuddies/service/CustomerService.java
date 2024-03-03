package com.ford.bookbuddies.service;

import com.ford.bookbuddies.entity.Book;
import com.ford.bookbuddies.entity.BookCategory;
import com.ford.bookbuddies.entity.Cart;
import com.ford.bookbuddies.entity.Customer;
import com.ford.bookbuddies.exception.CustomerException;

import java.util.List;


public interface CustomerService {

    Customer createCustomerAccount(Customer newCustomer) throws CustomerException;
    Cart getCart(Integer id) throws CustomerException;
    Customer login(String email, String password)throws CustomerException;
    List<Book> getBooksByCategory(BookCategory category);

}
