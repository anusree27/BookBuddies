package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.entity.BookOrders;
import com.ford.bookbuddies.entity.BookStock;
import com.ford.bookbuddies.entity.Customer;
import com.ford.bookbuddies.entity.StockManager;
import com.ford.bookbuddies.exception.BookException;
import com.ford.bookbuddies.exception.StockManagerException;
import com.ford.bookbuddies.service.StockManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
@RestController
public class StockManagerController {

    @Autowired
    private StockManagerService stockManagerService;

    private Integer adminId = null;

    @PostMapping("stockmanager/signup")
    public StockManager addStockManager(@RequestBody StockManager stockManager) throws StockManagerException {
        if (stockManager.getName() == null) {
            throw new StockManagerException("Admin Name should not be null");
        }
        if (stockManager.getPassword() == null) {
            throw new StockManagerException("Password should not be null");
        }
        return this.stockManagerService.signUp(stockManager);
    }

    @PostMapping("stockmanager/login")
    public StockManager login(@RequestBody StockManager stockManager) throws StockManagerException {
        if (stockManager.getName() == null) {
            throw new StockManagerException("Admin Name should not be null");
        }
        if (stockManager.getPassword() == null) {
            throw new StockManagerException("Password should not be null");
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


    @PostMapping("stockmanager/book")
    public BookStock addNewBooks(@RequestBody BookStock bookStock) throws BookException, StockManagerException {
        if (adminId == null) {
            throw new StockManagerException("Admin not logged");
        }
        if (bookStock == null) {
            throw new BookException("book should not be null");
        }
        if (bookStock.getBook().getBookTitle() == null) {
            throw new BookException("book should not be null");
        }
        if (bookStock.getBook().getBookAuthor() == null) {
            throw new BookException("book Author should not be null");
        }
        if (bookStock.getBook().getPrice() == null) {
            throw new BookException("book Price should not be null");
        }
        return this.stockManagerService.addNewBooks(adminId, bookStock);
    }

    @PatchMapping("stockmanager/book")
    public BookStock updateBook(@RequestBody BookStock updateBook) throws BookException, StockManagerException {
        if (adminId == null) {
            throw new StockManagerException("Admin not logged");
        }
        if (updateBook.getBook().getBookTitle() == null) {
            throw new BookException("book Title should not be null");
        }
        if (updateBook.getBook().getBookAuthor() == null) {
            throw new BookException("book Author should not be null");
        }
        if (updateBook.getBook().getPrice()== null) {
            throw new BookException("book Price should not be null");
        }
        return this.stockManagerService.updateBook(adminId,updateBook);
    }

    @GetMapping("stockmanager/book/{id}")
    public BookStock getBookById(@PathVariable("id") Integer bookId) throws BookException, StockManagerException {
        if (bookId == null) {
            throw new BookException("Id should not be null");
        }
        return this.stockManagerService.getBookById(adminId, bookId);
    }

    @DeleteMapping("stockmanager/book/{id}")
    public Boolean deleteBook(@PathVariable("id") Integer bookId) throws BookException, StockManagerException {
        if (bookId == null) {
            throw new BookException("Book id should not be null");
        }
        return this.stockManagerService.deleteBookByBookId(adminId,bookId);
    }

    @GetMapping("stockmanager/books")
    public List<BookStock> getAllBooks() throws StockManagerException {
        if (adminId == null) {
            throw new StockManagerException("Admin not logged");
        }
        return this.stockManagerService.displayAllBooks(adminId);
    }

    @GetMapping("stockmanager/users")
    public List<Customer> getAllCustomer() throws StockManagerException {
        if (adminId == null) {
            throw new StockManagerException("Admin not logged");
        }
        return this.stockManagerService.displayAllCustomer(adminId);
    }

    @GetMapping("stockmanager/update-orders")
    public List<BookOrders> updateOrderStatus(){
        return this.stockManagerService.updateOrderStatus();
    }

    @GetMapping("stockmanager/less-stocks")
    public List<BookStock> displayLessStocks() throws BookException {
        return this.stockManagerService.viewLessStocks();
    }
}
