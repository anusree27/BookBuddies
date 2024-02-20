package com.ford.BookBuddies.service;

import com.ford.BookBuddies.dao.BookStockRepository;
import com.ford.BookBuddies.entity.Book;
import com.ford.BookBuddies.entity.BookStock;
import com.ford.BookBuddies.entity.Customer;
import com.ford.BookBuddies.exception.BookException;
import com.ford.BookBuddies.dao.BookRepository;
import com.ford.BookBuddies.dao.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional

@Service
public class StockManagerServiceImpl implements StockManagerService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookStockRepository bookStockRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public BookStock addNewBooks(BookStock newBook) throws BookException {
        if (newBook == null) {
            throw new BookException("book should not be null");
        }

//        if (newBook.getBooksQuantity() < 0) {
//            throw new BookException("Book quantity should be greater than zero");
//        }

        this.bookRepository.save(newBook.getBook());
        return this.bookStockRepository.save(newBook);
    }

    @Override
    public BookStock updateBook(BookStock updateBook) {
        this.bookRepository.save(updateBook.getBook());
        return this.bookStockRepository.save(updateBook);
    }

    @Override
    public Boolean deleteBookByBookName(String name) {

         Optional<Book> book = this.bookRepository.findByBookTitle(name);
         Optional<BookStock> bookStock = this.bookStockRepository.findBookStockByBook(book.get());

         this.bookStockRepository.deleteBookStockByBook(book.get());
        this.bookRepository.deleteByBookTitle(name);

        return true;
    }

    @Override
    public List<BookStock> displayAllBooks() {
        return this.bookStockRepository.findAll();
    }

    @Override
    public List<Customer> displayAllCustomer() {
        return this.customerRepository.findAll();
    }


}
