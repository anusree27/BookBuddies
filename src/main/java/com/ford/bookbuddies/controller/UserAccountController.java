package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.dto.Logindto;
import com.ford.bookbuddies.entity.*;
import com.ford.bookbuddies.exception.CustomerException;
import com.ford.bookbuddies.service.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Transactional
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
@RestController
public class UserAccountController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("user/signup")
    public Customer addCustomer(@RequestBody Customer newCustomer) throws CustomerException {
        return this.customerService.createCustomerAccount(newCustomer);
    }
    @PostMapping("user/login")
    public Customer userAccountLogin(@RequestBody Logindto logindto) throws CustomerException{
        Customer user=null;
        if(logindto==null || logindto.getEmail()==null || logindto.getPassword()==null) throw new CustomerException("Login details not entered!");
        user=this.customerService.login(logindto.getEmail(),logindto.getPassword());
        return user;
    }
    @GetMapping("user/cart/{userId}")
    public List<BookDetail> viewCart(@PathVariable Integer userId) throws CustomerException{
        if(userId==null) throw new CustomerException("UserId is null");
        if(userId==0) throw new CustomerException("User not logged in to view Cart");
        return this.customerService.getCart(userId).getBooksDetails();
    }

    @GetMapping("user/allbooks")
    public List<Book> getAllBooks() throws Exception {
        return this.customerService.displayAllBooks();
    }
    @GetMapping("user/orders/{userId}")
    public List<BookOrders> getMyOrders(@PathVariable Integer userId) throws Exception {
        if(userId==null) throw new CustomerException("User Id is null");
        if(userId==0) throw new CustomerException("User not logged in to view Orders");
        return this.customerService.getMyOrders(userId);
    }
    @GetMapping("user/books/category/{category}")
    public List<Book> getBooksByCategory(@PathVariable("category") BookCategory bookCategory) {
        return this.customerService.getBooksByCategory(bookCategory);
    }


    @GetMapping("user/profile/{id}")
    public Customer userProfile(@PathVariable("id") Integer userId) throws CustomerException {
        if (userId == null) {
            throw new CustomerException("User Id cannot be null");
        }
        return this.customerService.getUserById(userId);
    }

    @PatchMapping("user/profile")
    public Customer editProfile(@RequestBody Customer customer) throws CustomerException {
        if (customer == null) {
            throw new CustomerException("Customer cannot be null");
        }
        return this.customerService.editCustomerAccount(customer);
    }
}

