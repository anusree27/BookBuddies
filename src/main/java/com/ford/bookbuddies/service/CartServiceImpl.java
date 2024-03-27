package com.ford.bookbuddies.service;

import com.ford.bookbuddies.dao.*;
import com.ford.bookbuddies.entity.*;
import com.ford.bookbuddies.exception.BookException;
import com.ford.bookbuddies.exception.CartException;
import com.ford.bookbuddies.exception.CustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private BookStockRepository bookStockRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BookOrderRepository bookOrderRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookDetailRepository bookDetailRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ConfirmedOrdersRepository confirmedOrdersRepository;

    @Override
    public List<BookDetail> buyBooksinCart(Integer userId,List<Integer> list) throws Exception {
     List<BookDetail> orderedBooks = new ArrayList<>();


        Optional<Cart> userCart = Optional.ofNullable(customerService.getCart(userId));
        if(userCart.isEmpty()) throw new CartException("Customer Cart is Null!");
        for (BookDetail bd : userCart.get().getBooksDetails()) {
            if (bd.getBook() != null) {
                if (list.contains(bd.getBook().getBookId())) {
                    orderedBooks.add(bd);
                }
            }
        }

        ConfirmedOrders confirmedOrders = new ConfirmedOrders(userId,false, orderedBooks);
        this.confirmedOrdersRepository.save(confirmedOrders);
        return orderedBooks;
    }
    @Override
    public Cart increaseQuantity(Integer userId, Integer bookId)throws Exception{
        Optional<Customer> customer = this.customerRepository.findById(userId);
        if(customer.isEmpty()) throw new CustomerException("User is not registered");
        Optional<Book> book=this.bookRepository.findById(bookId);
        if(book.isEmpty()) throw new BookException("Book doesn't exists");
        Optional<BookStock> bookStock=this.bookStockRepository.findBookStockByBook(book.get());
        BookDetail bookDetail=null;
        try{bookDetail=customer.get().getCart().getBooksDetails().stream().filter((bd)->bd.getBook().equals(book.get())).findAny().get();}
        catch(Exception e){throw new BookException("Book not found in Cart!");}
        if(bookDetail.getQuantity()+1>bookStock.get().getStockQuantity()) throw new CartException("Available Stock quantity is lesser than quantity needed");
        bookDetail.setQuantity(bookDetail.getQuantity()+1);
        this.bookDetailRepository.save(bookDetail);
        return customer.get().getCart();
    }

    @Override
    public Cart decreaseQuantity(Integer userId, Integer bookId) throws Exception{
        Optional<Customer> customer = this.customerRepository.findById(userId);
        if(customer.isEmpty()) throw new CustomerException("User is not registered");
        Optional<Book> book=this.bookRepository.findById(bookId);
        if(book.isEmpty()) throw new BookException("Book doesn't exists");
        BookDetail bookDetail=null;
        try{bookDetail=customer.get().getCart().getBooksDetails().stream().filter((bd)->bd.getBook().equals(book.get())).findAny().get();}
        catch(Exception e){throw new BookException("Book not found in Cart!");}
        if(bookDetail.getQuantity()>1) bookDetail.setQuantity(bookDetail.getQuantity()-1);
        this.bookDetailRepository.save(bookDetail);
        return customer.get().getCart();
    }

}
