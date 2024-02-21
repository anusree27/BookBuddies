package com.ford.bookbuddies.service;

import com.ford.bookbuddies.dao.BookStockRepository;
import com.ford.bookbuddies.entity.Book;
import com.ford.bookbuddies.entity.BookStock;
import com.ford.bookbuddies.entity.Customer;
import com.ford.bookbuddies.exception.BookException;
import com.ford.bookbuddies.dao.BookRepository;
import com.ford.bookbuddies.dao.CustomerRepository;
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

    @Override
    public Integer viewBooksCountByName(String bookName) {
        Optional<Book> book = this.bookRepository.findByBookTitle(bookName);
        Optional<BookStock> bookStock = this.bookStockRepository.findBookStockByBook(book.get());
        return bookStock.get().getStockQuantity();
    }

    @Override
    public BookStock updateBookCountByName(String bookName, Integer quantity) {
        Optional<Book> book = this.bookRepository.findByBookTitle(bookName);
        Optional<BookStock> bookStock = this.bookStockRepository.findBookStockByBook(book.get());
        bookStock.get().setStockQuantity(quantity);
        return bookStock.get();
    }


}
