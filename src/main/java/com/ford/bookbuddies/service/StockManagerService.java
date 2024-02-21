package com.ford.bookbuddies.service;

import com.ford.bookbuddies.entity.BookStock;
import com.ford.bookbuddies.entity.Customer;
import com.ford.bookbuddies.exception.BookException;

import java.util.List;

public interface StockManagerService {
    //add books, delete books, update books -
    //get all users -
    //get books count, add books count, delete books count -
    //view user order, update user order status

    BookStock addNewBooks(BookStock newBook) throws BookException;

    BookStock updateBook(BookStock updateBook);

    Boolean deleteBookByBookName(String bookName);

    List<BookStock> displayAllBooks();

    List<Customer> displayAllCustomer();

    Integer viewBooksCountByName(String bookName);

    BookStock updateBookCountByName(String bookname, Integer quantity);
}
