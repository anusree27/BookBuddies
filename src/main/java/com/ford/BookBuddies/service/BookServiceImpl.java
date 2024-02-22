package com.ford.BookBuddies.service;

import com.ford.BookBuddies.dao.BookDetailRepository;
import com.ford.BookBuddies.dao.BookRepository;
import com.ford.BookBuddies.dao.CartRepository;
import com.ford.BookBuddies.dao.CustomerRepository;
import com.ford.BookBuddies.entity.Book;
import com.ford.BookBuddies.entity.BookDetail;
import com.ford.BookBuddies.entity.Cart;
import com.ford.BookBuddies.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
        public Cart addProductToCart(Integer userId, String bookName, Integer quantity) {
            Optional<Customer> customerOptional = this.customerRepository.findById(userId);
            Optional<Book> bookOptional = this.bookRepository.findByBookTitle(bookName);
            Customer customer = customerOptional.get();
            Book book = bookOptional.get();
            BookDetail bookDetail = new BookDetail(quantity,book);
            customer.getCart().getBooksDetails().add(bookDetail);
            this.bookDetailRepository.save(bookDetail);
            this.cartRepository.save(customer.getCart());
            return customer.getCart();
        }

    @Override
    public Cart buyBook() {
        return null;
    }

    @Override
    public Cart subscribeBook() {
        return null;
    }
}
