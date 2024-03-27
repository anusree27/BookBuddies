package com.ford.bookbuddies.service;

import com.ford.bookbuddies.entity.BookOrders;
import com.ford.bookbuddies.entity.BookStock;
import com.ford.bookbuddies.entity.Customer;
import com.ford.bookbuddies.entity.StockManager;
import com.ford.bookbuddies.exception.BookException;
import com.ford.bookbuddies.exception.StockManagerException;

import java.util.List;

public interface StockManagerService {
    //add books, delete books, update books -
    //get all users -
    //get books count, add books count, delete books count -
    //view user order, update user order status

    StockManager signUp(StockManager stockManager);

    StockManager login(String username, String password) throws StockManagerException;

    public void setAdminId(Integer id);

    public Integer getAdminId();
    List<StockManager> getAllAdmins();

    BookStock addNewBooks(Integer adminId, BookStock newBook) throws BookException, StockManagerException;

    BookStock updateBook(Integer adminId,BookStock updateBook) throws BookException, StockManagerException;

    BookStock getBookById(Integer adminId,Integer bookId) throws BookException, StockManagerException;
    List<BookOrders> updateOrderStatus();

    Boolean deleteBookByBookId(Integer adminId,Integer bookId) throws BookException, StockManagerException;

    List<BookStock> displayAllBooks(Integer adminId) throws StockManagerException;

    List<Customer> displayAllCustomer(Integer adminId) throws StockManagerException;

    public List<BookStock> viewLessStocks() throws BookException;
}
