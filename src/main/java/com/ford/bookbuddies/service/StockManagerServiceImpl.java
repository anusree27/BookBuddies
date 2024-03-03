package com.ford.bookbuddies.service;

import com.ford.bookbuddies.dao.*;
import com.ford.bookbuddies.entity.*;
import com.ford.bookbuddies.exception.BookException;
import com.ford.bookbuddies.exception.OrderException;
import com.ford.bookbuddies.exception.PaymentException;
import com.ford.bookbuddies.exception.StockManagerException;
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

    @Autowired
    private StockManagerRepository stockManagerRepository;

    @Autowired
    private BookOrderRepository bookOrderRepository;

    static Integer adminId = null;


    @Override
    public StockManager signUp(StockManager stockManager) {
        return this.stockManagerRepository.save(stockManager);
    }

    @Override
    public StockManager login(String username, String password) throws StockManagerException {
//        if (username == null) {
//            throw new StockManagerException("name should not be null");
//        }
//        if (password == null) {
//            throw new StockManagerException("Password should not be null");
//        }
        Optional<StockManager> stockManagerOptional = this.stockManagerRepository.findByName(username);
        if (stockManagerOptional.isEmpty()) {
            throw new StockManagerException("Admin is not registered");
        }
        StockManager stockManager = stockManagerOptional.get();
        if (!username.equals(stockManager.getPassword())) {
            throw new StockManagerException("Incorrect password");
        }
        return stockManager;
    }

    @Override
    public void setAdminId(Integer id) {
        adminId = id;
    }

    @Override
    public Integer getAdminId() {
        return adminId;
    }

    @Override
    public List<StockManager> getAllAdmins() {
        return this.stockManagerRepository.findAll();
    }


    @Override
    public BookStock addNewBooks(Integer adminId, BookStock newBook) throws BookException, StockManagerException {
//        if (adminId == null) {
//            throw new StockManagerException("Admin not logged");
//        }
//        if (newBook == null) {
//            throw new BookException("book should not be null");
//        }
//        if (newBook.getBook().getBookTitle() == null) {
//            throw new BookException("book Title should not be null");
//        }
//        if (newBook.getBook().getBookAuthor() == null) {
//            throw new BookException("book Author should not be null");
//        }
//        if (newBook.getBook().getPrice() == null) {
//            throw new BookException("book Price should not be null");
//        }
        if (newBook.getBook().getPrice() < 0) {
            throw new BookException("book Price should not be less than zero");
        }
        Optional<StockManager> stockManagerOptional = this.stockManagerRepository.findById(adminId);
        if (stockManagerOptional.isEmpty()) {
            throw new StockManagerException("Admin should log in before adding books");
        }
        this.bookRepository.save(newBook.getBook());
        return this.bookStockRepository.save(newBook);
    }

    @Override
    public BookStock updateBook(Integer adminId, BookStock updateBook) throws BookException, StockManagerException {
        if (adminId == null) {
            throw new StockManagerException("Admin not logged");
        }
        if (updateBook == null) {
            throw new BookException("BookStock should not be null");
        }
        if (updateBook.getBook() == null) {
            throw new BookException("Book should not be null");
        }
        if (updateBook.getBook().getBookTitle() == null) {
            throw new BookException("book Title should not be null");
        }
        if (updateBook.getBook().getBookAuthor() == null) {
            throw new BookException("book Author should not be null");
        }
        if (updateBook.getBook().getPrice() == null) {
            throw new BookException("book Price should not be null");
        }
        if (updateBook.getBook().getPrice() < 0) {
            throw new BookException("book Price should not be less than zero");
        }
        this.bookRepository.save(updateBook.getBook());
        return this.bookStockRepository.save(updateBook);
    }

    @Override
    public Boolean deleteBookByBookName(Integer adminId, String name) throws BookException, StockManagerException {
//        if (adminId == null) {
//            throw new StockManagerException("Admin not logged");
//        }
//        if (name == null) {
//            throw new BookException("Book name should not be null");
//        }
         Optional<Book> book = this.bookRepository.findByBookTitle(name);
        if (book.isEmpty()) {
            throw new BookException("Book is not present in the stocks to delete");
        }
         Optional<BookStock> bookStock = this.bookStockRepository.findBookStockByBook(book.get());
         this.bookStockRepository.deleteBookStockByBook(book.get());
        this.bookRepository.deleteByBookTitle(name);

        return true;
    }

    @Override
    public List<BookStock> displayAllBooks(Integer adminId) throws StockManagerException {
//        if (adminId == null) {
//            throw new StockManagerException("Admin not logged");
//        }
        return this.bookStockRepository.findAll();
    }

    @Override
    public List<Customer> displayAllCustomer(Integer adminId) throws StockManagerException {
//        if (adminId == null) {
//            throw new StockManagerException("Admin not logged");
//        }
        return this.customerRepository.findAll();
    }

    @Override
    public Integer viewBooksCountByName(Integer adminId, String bookName) throws BookException, StockManagerException {
//        if (adminId == null) {
//            throw new StockManagerException("Admin not logged");
//        }
        Optional<Book> book = this.bookRepository.findByBookTitle(bookName);
        if (book.isEmpty()) {
            throw new BookException("book is not present");
        }
        Optional<BookStock> bookStock = this.bookStockRepository.findBookStockByBook(book.get());
        return bookStock.get().getStockQuantity();
    }

    @Override
    public BookStock updateBookCountByName(Integer adminId, String bookName, Integer quantity) throws BookException, StockManagerException {
//        if (adminId == null) {
//            throw new StockManagerException("Admin not logged");
//        }
//        if (bookName == null) {
//            throw new BookException("Book name should not be null");
//        }
        Optional<Book> book = this.bookRepository.findByBookTitle(bookName);
        if (book.isEmpty()) {
            throw new BookException("book is not present");
        }
        Optional<BookStock> bookStock = this.bookStockRepository.findBookStockByBook(book.get());
        bookStock.get().setStockQuantity(quantity);
        return bookStock.get();
    }

    @Override
    public BookOrders updateOrderStatus(Integer orderId, OrderStatus orderStatus) throws OrderException {
        Optional<BookOrders> bookOrdersOptional = this.bookOrderRepository.findById(orderId);
        if (bookOrdersOptional.isEmpty()) {
            throw new OrderException("Order is not present");
        }
        BookOrders bookOrders = bookOrdersOptional.get();
        bookOrders.setOrderStatus(orderStatus);
        this.bookOrderRepository.save(bookOrders);
        return bookOrders ;
    }


}
