package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.entity.BookStock;
import com.ford.bookbuddies.entity.StockManager;
import com.ford.bookbuddies.exception.BookException;
import com.ford.bookbuddies.entity.Customer;
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
    public StockManager addStockManager(@RequestBody StockManager stockManager) {
        return this.stockManagerService.signUp(stockManager);
    }

    @PostMapping("stockmanager/login")
    public StockManager login(@RequestBody StockManager stockManager) throws StockManagerException {
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


    @PostMapping("Book")
    public BookStock addNewBooks(@RequestBody BookStock newBook) throws BookException, StockManagerException {
        return this.stockManagerService.addNewBooks(adminId, newBook);
    }

    @PatchMapping("Book")
    public BookStock updateBook(@RequestBody BookStock updateBook) throws BookException, StockManagerException {
        return this.stockManagerService.updateBook(adminId,updateBook);
    }

    @DeleteMapping("book/{name}")
    public Boolean deleteBook(@PathVariable("name") String bookName) throws BookException, StockManagerException {
        return this.stockManagerService.deleteBookByBookName(adminId,bookName);
    }

    @GetMapping("books")
    public List<BookStock> getAllBooks() throws StockManagerException {
        return this.stockManagerService.displayAllBooks(adminId);
    }

    @GetMapping("users")
    public List<Customer> getAllCustomer() throws StockManagerException {
        return this.stockManagerService.displayAllCustomer(adminId);
    }

    @GetMapping("bookcount/{name}")
    public Integer viewBookCount(@PathVariable("name") String name) throws BookException, StockManagerException {
        return this.stockManagerService.viewBooksCountByName(adminId,name);
    }

    @PatchMapping("bookcount/update/{book}/{quantity}")
    public BookStock updateBookCount(@PathVariable("book") String bookName, @PathVariable("quantity") Integer quantity) throws BookException, StockManagerException {
        return this.stockManagerService.updateBookCountByName(adminId,bookName, quantity);
    }
}
