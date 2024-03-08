package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.entity.*;
import com.ford.bookbuddies.exception.BookException;
import com.ford.bookbuddies.exception.OrderException;
import com.ford.bookbuddies.exception.PaymentException;
import com.ford.bookbuddies.exception.StockManagerException;
import com.ford.bookbuddies.service.StockManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StockManagerController {

    @Autowired
    private StockManagerService stockManagerService;

    private Integer adminId = null;

    @PostMapping("stockmanager/signup")
    public StockManager addStockManager(@RequestBody StockManager stockManager) throws StockManagerException {
        if (null == stockManager.getName() ) {
            throw new StockManagerException("Admin Name should not be null");
        }
        if (null == stockManager.getPassword()) {
            throw new StockManagerException("Admin Password should not be empty");
        }
        return this.stockManagerService.signUp(stockManager);
    }

    @PostMapping("stockmanager/login")
    public StockManager login(@RequestBody StockManager stockManager) throws StockManagerException {
        if (null == stockManager.getName()) {
            throw new StockManagerException("Admin Name should not be null");
        }
        if (null == stockManager.getPassword()) {
            throw new StockManagerException("Admin Password should not be empty");
        }
        StockManager manager = null;
        manager = this.stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        if (manager!=null) {
            adminId = manager.getAdminId();
            stockManagerService.setAdminId(adminId);
        }
        return manager;
    }
    @GetMapping("stockmanagers")
    public List<StockManager> allStockManagers() {
        return this.stockManagerService.getAllAdmins();
    }


    @PostMapping("stockmanager/Book")
    public BookStock addNewBooks(@RequestBody BookStock newBook) throws BookException, StockManagerException {
        if (null == adminId ) {
            throw new StockManagerException("Admin not logged");
        }
        if (null == newBook) {
            throw new BookException("book should not be null");
        }
        if (null == newBook.getBook().getBookTitle()) {
            throw new BookException("book Title should not be null");
        }
        if (null == newBook.getBook().getBookAuthor()) {
            throw new BookException("book Author should not be null");
        }
        if (null == newBook.getBook().getPrice()) {
            throw new BookException("book Price should not be null");
        }
        return this.stockManagerService.addNewBooks(adminId, newBook);
    }

    @PatchMapping("stockmanager/Book")
    public BookStock updateBook(@RequestBody BookStock updateBook) throws BookException, StockManagerException {
        if (null == adminId ) {
            throw new StockManagerException("Admin not logged");
        }
        if (null == updateBook) {
            throw new BookException("book should not be null");
        }
        if (null == updateBook.getBook().getBookTitle()) {
            throw new BookException("book Title should not be null");
        }
        if (null == updateBook.getBook().getBookAuthor()) {
            throw new BookException("book Author should not be null");
        }
        if (null == updateBook.getBook().getPrice()) {
            throw new BookException("book Price should not be null");
        }
        return this.stockManagerService.updateBook(adminId,updateBook);
    }

    @DeleteMapping("stockmanager/book/{name}")
    public Boolean deleteBook(@PathVariable("name") String bookName) throws BookException, StockManagerException {
        if (null == adminId) {
            throw new StockManagerException("Admin not logged");
        }
        if (null == bookName) {
            throw new BookException("Book name should not be null");
        }
        return this.stockManagerService.deleteBookByBookName(adminId,bookName);
    }

    @GetMapping("stockmanager/books")
    public List<BookStock> getAllBooks() throws StockManagerException {
        if (null == adminId) {
            throw new StockManagerException("Admin not logged");
        }
        return this.stockManagerService.displayAllBooks(adminId);
    }

    @GetMapping("stockmanager/users")
    public List<Customer> getAllCustomer() throws StockManagerException {
        if (null == adminId) {
            throw new StockManagerException("Admin not logged");
        }
        return this.stockManagerService.displayAllCustomer(adminId);
    }

    @GetMapping("stockmanager/bookcount/{name}")
    public Integer viewBookCount(@PathVariable("name") String name) throws BookException, StockManagerException {
        if (null == adminId) {
            throw new StockManagerException("Admin not logged");
        }
        return this.stockManagerService.viewBooksCountByName(adminId,name);
    }

    @PatchMapping("stockmanager/bookcount/update/{book}/{quantity}")
    public BookStock updateBookCount(@PathVariable("book") String bookName, @PathVariable("quantity") Integer quantity) throws BookException, StockManagerException {
        if (null == adminId) {
            throw new StockManagerException("Admin not logged");
        }
        if (null == bookName) {
            throw new BookException("Book name should not be null");
        }
        return this.stockManagerService.updateBookCountByName(adminId,bookName, quantity);
    }

    @PostMapping("stockmanager/orderstatus/{orderid}/{orderstatus}")
    public BookOrders setOrderStatus(@PathVariable("orderid") Integer orderId, @PathVariable("orderstatus")OrderStatus orderStatus) throws StockManagerException, OrderException {
        if (null == adminId) {
            throw new StockManagerException("Admin not logged");
        }
        if (null == orderId) {
            throw new OrderException("Order id should not be null");
        }
        if (null == orderStatus) {
            throw new OrderException("Order status should not be null");
        }
        return this.stockManagerService.updateOrderStatus(orderId, orderStatus);
    }
}
