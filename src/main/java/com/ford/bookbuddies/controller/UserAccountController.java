package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.dto.Logindto;
import com.ford.bookbuddies.entity.Book;
import com.ford.bookbuddies.entity.BookCategory;
import com.ford.bookbuddies.entity.Cart;
import com.ford.bookbuddies.entity.Customer;
import com.ford.bookbuddies.exception.CustomerException;
import com.ford.bookbuddies.service.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@RestController
public class UserAccountController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("user/signup")
    public Customer addCustomer(@RequestBody Customer newCustomer) throws CustomerException {
        return this.customerService.createCustomerAccount(newCustomer);
    }
    @PostMapping("useraccount/login")
    public Customer userAccountLogin(@RequestBody Logindto logindto) throws CustomerException{
        Customer user=null;
        if(logindto==null || logindto.getEmail()==null || logindto.getPassword()==null) throw new CustomerException("Login details not entered!");
        user=this.customerService.login(logindto.getEmail(),logindto.getPassword());
        return user;
    }
    @GetMapping("customer/cart/{userId}")
    public Cart viewCart(@PathVariable Integer userId) throws CustomerException{
        if(userId==null) throw new CustomerException("User not logged in");
        return this.customerService.getCart(userId);
    }
    @GetMapping("Books/Category/{category}")
    public List<Book> getBooksByCategory(@PathVariable("category") BookCategory bookCategory) {
        return this.customerService.getBooksByCategory(bookCategory);
    }

}

