package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.entity.BookStock;
import com.ford.bookbuddies.exception.BookException;
import com.ford.bookbuddies.entity.Customer;
import com.ford.bookbuddies.service.StockManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StockManagerController {

    @Autowired
    private StockManagerService stockManagerService;

    @PostMapping("Book")
    public BookStock addNewBooks(@RequestBody BookStock newBook) throws BookException {
        return this.stockManagerService.addNewBooks(newBook);
    }

    @PatchMapping("Book")
    public BookStock updateBook(@RequestBody BookStock updateBook) {
        return this.stockManagerService.updateBook(updateBook);
    }

    @DeleteMapping("book/{name}")
    public Boolean deleteBook(@PathVariable("name") String bookName) {
        return this.stockManagerService.deleteBookByBookName(bookName);
    }

    @GetMapping("books")
    public List<BookStock> getAllBooks() {
        return this.stockManagerService.displayAllBooks();
    }

    @GetMapping("users")
    public List<Customer> getAllCustomer() {
        return this.stockManagerService.displayAllCustomer();
    }

    @GetMapping("bookcount/{name}")
    public Integer viewBookCount(@PathVariable("name") String name) {
        return this.stockManagerService.viewBooksCountByName(name);
    }

    @PatchMapping("bookcount/update/{book}/{quantity}")
    public BookStock updateBookCount(@PathVariable("book") String bookName, @PathVariable("quantity") Integer quantity) {
        return this.stockManagerService.updateBookCountByName(bookName, quantity);
    }
}
