package com.ford.BookBuddies.service;

import com.ford.BookBuddies.dao.BookDetailRepository;
import com.ford.BookBuddies.dao.BookRepository;
import com.ford.BookBuddies.dao.CartRepository;
import com.ford.BookBuddies.dao.CustomerRepository;
import com.ford.BookBuddies.dto.ConfirmedBooks;
import com.ford.BookBuddies.entity.Book;
import com.ford.BookBuddies.entity.BookDetail;
import com.ford.BookBuddies.entity.Cart;
import com.ford.BookBuddies.entity.Customer;
import com.ford.BookBuddies.exception.BookException;
import com.ford.BookBuddies.exception.CustomerException;
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
    @Override
        public Cart addProductToCart(Integer userId, String bookName, Integer quantity) throws Exception {
            BookDetail bookDetail=null;
            Optional<Customer> customerOptional = this.customerRepository.findById(userId);
            if(customerOptional.isEmpty()) throw new CustomerException("User is not registered");

            Optional<Book> bookOptional = this.bookRepository.findByBookTitle(bookName);
            if(bookOptional.isEmpty()) throw new BookException("Book doesn't exists");

            try{
                bookDetail=customerOptional.get().getCart().getBooksDetails().stream().filter((bd)->bd.getBook().equals(bookOptional.get())).findAny().get();
                bookDetail.setQuantity(bookDetail.getQuantity()+quantity);
            }
            catch(Exception e){
                if(bookDetail==null) bookDetail = new BookDetail(quantity,bookOptional.get());
                customerOptional.get().getCart().getBooksDetails().add(bookDetail);
        }

            this.bookDetailRepository.save(bookDetail);
            this.cartRepository.save(customerOptional.get().getCart());
            return customerOptional.get().getCart();
        }

    @Override
    public List<BookDetail> buyBook(Integer userId,String bookName,Integer quantity)throws Exception{
        List<BookDetail> orderedBooks = new ArrayList<>();
        Optional<Book> bookOptional=this.bookRepository.findByBookTitle(bookName);
        if(bookOptional.isEmpty()) throw new BookException("Book doesn't exists");
        BookDetail bookDetail=new BookDetail(quantity,bookOptional.get());
        bookDetail=this.bookDetailRepository.save(bookDetail);
        orderedBooks.add(bookDetail);
        ConfirmedBooks confirmedBooks=new ConfirmedBooks(userId,orderedBooks);
        return orderedBooks;
    }

    @Override
    public Cart subscribeBook() {
        return null;
    }
}
