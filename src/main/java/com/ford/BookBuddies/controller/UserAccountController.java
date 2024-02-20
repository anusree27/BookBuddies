package com.ford.BookBuddies.controller;

import com.ford.BookBuddies.dto.Logindto;
import com.ford.BookBuddies.exception.CustomerException;
import com.ford.BookBuddies.service.CustomerService;
import com.ford.BookBuddies.dto.CustomerCartDto;
import com.ford.BookBuddies.entity.Book;
import com.ford.BookBuddies.entity.BookCategory;
import com.ford.BookBuddies.entity.Cart;
import com.ford.BookBuddies.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserAccountController {
    private Integer userId=null;
    @Autowired
    private CustomerService customerService;

    @PostMapping("user/signup")
    public Customer addCustomer(@RequestBody Customer newCustomer) throws CustomerException {
        return this.customerService.createCustomerAccount(newCustomer);
    }
    @PostMapping("useraccount/login")
    public Customer userAccountLogin(@RequestBody Logindto logindto) throws CustomerException{
        Customer user=null;
        user=this.customerService.login(logindto.getEmail(),logindto.getPassword());
        if(user!=null){
            userId=user.getId();
            customerService.setCustomerLoginId(userId);
        }
        return user;
    }


    @PostMapping("customer/cart")
    public Cart addProductsToCart(@RequestBody CustomerCartDto customerCartDto) {
        return this.customerService.addProductToCart(userId, customerCartDto.getBookName(),customerCartDto.getQuantity());
    }



    @GetMapping("Books/Category/{category}")
    public List<Book> getBooksByCategory(@PathVariable("category") BookCategory bookCategory) {
        return this.customerService.getBooksByCategory(bookCategory);
    }
}

