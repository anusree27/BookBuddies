package com.ford.BookBuddies.service;

import com.ford.BookBuddies.dao.*;
import com.ford.BookBuddies.entity.*;
import com.ford.BookBuddies.exception.CustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CustomerService customerService;
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

    static List<BookDetail> orderedBooks = new ArrayList<>();


    @Override
    public List<BookDetail> buyBooksinCart(List<Integer> list) throws CustomerException {
        Integer loginId = this.customerService.getCustomerLoginId();
        Cart userCart = customerService.getCart(loginId);
        for (BookDetail bd : userCart.getBooksDetails()) {
            if (bd.getBook() != null) {
                if (list.contains(bd.getBook().getBookId())) {
                    orderedBooks.add(bd);
                }
            }
        }
//        Integer loginId = this.customerService.getCustomerLoginId();
//        Optional<Customer> customer = this.customerRepository.findById(loginId);
//        BookOrders bookOrders = new BookOrders(orderList);
//        this.bookOrderRepository.save(bookOrders);
//        customer.get().getOrderList().add(bookOrders);
//        this.customerRepository.save(customer.get());
        return orderedBooks;
    }

    @Override
    public Cart deleteProductFromCart(Integer userId, String bookName) {
        Optional<Customer> customerOptional = this.customerRepository.findById(userId);
        Optional<Book> bookOptional = this.bookRepository.findByBookTitle(bookName);
        Customer customer = customerOptional.get();
        Book book = bookOptional.get();
        Optional<BookDetail> bookDetailOptional=this.bookDetailRepository.findByBook(book);
        customer.getCart().getBooksDetails().remove(bookDetailOptional.get());
//        this.bookDetailRepository.deleteByBook(book);
        this.cartRepository.save(customer.getCart());
        return customer.getCart();
    }

    @Override
    public Cart increaseQuantity(Integer bookId) {
        Integer loginId = this.customerService.getCustomerLoginId();
        Optional<Customer> customer = this.customerRepository.findById(loginId);
        Optional<Book> book=this.bookRepository.findById(bookId);
        BookDetail bookDetail=customer.get().getCart().getBooksDetails().stream().filter((bd)->bd.getBook().equals(book.get())).findAny().get();
        bookDetail.setQuantity(bookDetail.getQuantity()+1);
        this.bookDetailRepository.save(bookDetail);
        return customer.get().getCart();
    }

    @Override
    public Cart decreaseQuantity(Integer bookId) {
        Integer loginId = this.customerService.getCustomerLoginId();
        Optional<Customer> customer = this.customerRepository.findById(loginId);
        Optional<Book> book=this.bookRepository.findById(bookId);
        BookDetail bookDetail=customer.get().getCart().getBooksDetails().stream().filter((bd)->bd.getBook().equals(book.get())).findAny().get();
        bookDetail.setQuantity(bookDetail.getQuantity()-1);
        this.bookDetailRepository.save(bookDetail);
        return customer.get().getCart();
    }

    @Override
    public List<BookDetail> getItemsToBuy() {
        return orderedBooks;
    }

}
