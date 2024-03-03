package com.ford.bookbuddies.service;

import com.ford.bookbuddies.dao.BookDetailRepository;
import com.ford.bookbuddies.dao.BookRepository;
import com.ford.bookbuddies.dao.CartRepository;
import com.ford.bookbuddies.dao.CustomerRepository;
import com.ford.bookbuddies.entity.Book;
import com.ford.bookbuddies.entity.BookDetail;
import com.ford.bookbuddies.entity.Cart;
import com.ford.bookbuddies.entity.Customer;
import com.ford.bookbuddies.exception.BookException;
import com.ford.bookbuddies.exception.CustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteServiceImpl implements DeleteService{
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookDetailRepository bookDetailRepository;
    @Autowired
    private CartRepository cartRepository;
    @Override
    public Cart deleteProductFromCart(Integer userId, Integer bookId)throws Exception{
        Optional<Customer> customerOptional = this.customerRepository.findById(userId);
        if(customerOptional.isEmpty()) throw new CustomerException("User is not registered");
        Optional<Book> bookOptional = this.bookRepository.findById(bookId);
        if(bookOptional.isEmpty()) throw new BookException("Book doesn't exists");
        Customer customer = customerOptional.get();
        Book book = bookOptional.get();
        Optional<BookDetail> bookDetailOptional=this.bookDetailRepository.findByBook(book);
        customer.getCart().getBooksDetails().remove(bookDetailOptional.get());
        this.cartRepository.save(customer.getCart());
        return customer.getCart();
    }
}
