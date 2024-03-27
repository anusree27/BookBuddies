package com.ford.bookbuddies.service;

import com.ford.bookbuddies.dao.*;
import com.ford.bookbuddies.entity.*;
import com.ford.bookbuddies.exception.BookException;
import com.ford.bookbuddies.exception.StockManagerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class StockManagerServiceImpl implements StockManagerService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookOrderRepository bookOrderRepository;

    @Autowired
    private BookStockRepository bookStockRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StockManagerRepository stockManagerRepository;

    static Integer adminId = null;


    @Override
    public StockManager signUp(StockManager stockManager) {
        return this.stockManagerRepository.save(stockManager);
    }

    @Override
    public StockManager login(String username, String password) throws StockManagerException {
        if (username == null) {
            throw new StockManagerException("name should not be null");
        }
        if (password == null) {
            throw new StockManagerException("Password should not be null");
        }
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
        if (adminId == null) {
            throw new StockManagerException("Admin not logged");
        }
        if (newBook == null) {
            throw new BookException("book should not be null");
        }
        if (newBook.getBook().getBookTitle() == null) {
            throw new BookException("book Title should not be null");
        }
        if (newBook.getBook().getBookAuthor() == null) {
            throw new BookException("book Author should not be null");
        }
        if (newBook.getBook().getPrice() == null) {
            throw new BookException("book Price should not be null");
        }
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
    public BookStock getBookById(Integer adminId,Integer bookId) throws BookException, StockManagerException {
        Optional<BookStock> bookStockOptional = this.bookStockRepository.findById(bookId);
        if (adminId == null) {
            throw new StockManagerException("Admin not logged");
        }
        if (bookStockOptional.isEmpty()) {
            throw new BookException("Book is not present");
        }
        return bookStockOptional.get();
    }


    @Override
    public Boolean deleteBookByBookId(Integer adminId, Integer bookId) throws BookException, StockManagerException {
        if (adminId == null) {
            throw new StockManagerException("Admin not logged");
        }
        if (bookId == null) {
            throw new BookException("Book id should not be null");
        }
        Optional<Book> book = this.bookRepository.findById(bookId);
        if (book.isEmpty()) {
            throw new BookException("Book is not present in the stocks to delete");
        }
        Optional<BookStock> bookStock = this.bookStockRepository.findById(bookId);
        if (bookStock.isEmpty()) {
            throw new BookException("Book is not present in the stocks to delete");
        }
        BookStock bookStock1 = bookStock.get();
//        Optional<BookStock> bookStock = this.bookStockRepository.findBookStockByBook(book.get());
        this.bookStockRepository.delete(bookStock1);
        this.bookRepository.delete(bookStock1.getBook());

        return true;

    }

    @Override
    public List<BookStock> displayAllBooks(Integer adminId) throws StockManagerException {
        if (adminId == null) {
            throw new StockManagerException("Admin not logged");
        }
        return this.bookStockRepository.findAll();
    }

    @Override
    public List<Customer> displayAllCustomer(Integer adminId) throws StockManagerException {
        if (adminId == null) {
            throw new StockManagerException("Admin not logged");
        }
        return this.customerRepository.findAll();
    }


    @Override
    public List<BookStock> viewLessStocks() throws BookException {
        List<BookStock> result = new ArrayList<>();
        List<BookStock> bookStockList = this.bookStockRepository.findAll();
        if (bookStockList.isEmpty()) {
            throw new BookException("No books in stock");
        }
        for (BookStock bk : bookStockList) {
            if (bk.getStockQuantity() < 5) {
                result.add(bk);
            }
        }
        return result;
    }

    @Override
    public List<BookOrders> updateOrderStatus() {
        List<BookOrders> ordersList=this.bookOrderRepository.findAll();
        for(BookOrders bo:ordersList){
            Long days=java.time.temporal.ChronoUnit.DAYS.between(bo.getOrderDate(), LocalDate.now());
            if(days==0) bo.setOrderStatus(OrderStatus.CONFIRMED);
            else if(days>=0 && days<=2) bo.setOrderStatus(OrderStatus.PACKED);
            else if(days>2 && days<=7) bo.setOrderStatus(OrderStatus.SHIPPED);
            else bo.setOrderStatus(OrderStatus.DELIVERED);
            this.bookOrderRepository.save(bo);
        }
        return ordersList;
    }

}
