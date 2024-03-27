package com.ford.bookbuddies.service;

import com.ford.bookbuddies.dao.*;
import com.ford.bookbuddies.entity.*;
import com.ford.bookbuddies.exception.BookException;
import com.ford.bookbuddies.exception.CartException;
import com.ford.bookbuddies.exception.CustomerException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookDetailRepository bookDetailRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private BookStockRepository bookStockRepository;
    @Autowired
    private ConfirmedOrdersRepository confirmedOrdersRepository;
    @Override
    @Transactional
    public Cart addProductToCart(Integer userId, Integer bookId, Integer quantity) throws Exception {
        BookDetail bookDetail=null;
        Optional<Customer> customerOptional = this.customerRepository.findById(userId);
        if(customerOptional.isEmpty()) throw new CustomerException("User is not registered");

        Optional<Book> bookOptional = this.bookRepository.findById(bookId);
        if(bookOptional.isEmpty()) throw new BookException("Book doesn't exists");
        Optional<BookStock> bookStock=this.bookStockRepository.findBookStockByBook(bookOptional.get());
        try{
            bookDetail=customerOptional.get().getCart().getBooksDetails().stream().filter((bd)->bd.getBook().equals(bookOptional.get())).findAny().get();
            if(bookDetail.getQuantity()+quantity>bookStock.get().getStockQuantity()) throw new CartException("Available Stock quantity is lesser than quantity needed");
            bookDetail.setQuantity(bookDetail.getQuantity()+quantity);
        }
        catch(Exception e){
            if(quantity>bookStock.get().getStockQuantity()) throw new CartException("Available Stock quantity is lesser than quantity needed");
            if(bookDetail==null) bookDetail = new BookDetail(quantity,bookOptional.get());
            customerOptional.get().getCart().getBooksDetails().add(bookDetail);
        }

        this.bookDetailRepository.save(bookDetail);
        this.cartRepository.save(customerOptional.get().getCart());
        return customerOptional.get().getCart();
    }

    @Override
    public List<BookDetail> buyBook(Integer userId,Integer bookId,Integer quantity)throws Exception{

        List<BookDetail> orderedBooks = new ArrayList<>();
        Optional<Customer> customerOptional = this.customerRepository.findById(userId);
        if(customerOptional.isEmpty()) throw new CustomerException("User is not registered");
        Optional<Book> bookOptional=this.bookRepository.findById(bookId);
        if(bookOptional.isEmpty()) throw new BookException("Book doesn't exists");
        Optional<BookStock> bookStock=this.bookStockRepository.findBookStockByBook(bookOptional.get());
        if(quantity>bookStock.get().getStockQuantity()) throw new CartException("Available Stock quantity is lesser than quantity needed");
        BookDetail bookDetail=new BookDetail(quantity,bookOptional.get());
        bookDetail=this.bookDetailRepository.save(bookDetail);
        orderedBooks.add(bookDetail);
        ConfirmedOrders  confirmedOrders = new ConfirmedOrders(userId,false, orderedBooks);
        this.confirmedOrdersRepository.save(confirmedOrders);
        return orderedBooks;
    }

}
