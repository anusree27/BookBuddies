package com.ford.bookbuddies;

import com.ford.bookbuddies.controller.StockManagerController;
import com.ford.bookbuddies.dao.BookRepository;
import com.ford.bookbuddies.dao.BookStockRepository;
import com.ford.bookbuddies.dao.CustomerRepository;
import com.ford.bookbuddies.dao.StockManagerRepository;
import com.ford.bookbuddies.entity.Book;
import com.ford.bookbuddies.entity.BookStock;
import com.ford.bookbuddies.entity.StockManager;
import com.ford.bookbuddies.exception.BookException;
import com.ford.bookbuddies.exception.StockManagerException;
import com.ford.bookbuddies.service.StockManagerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ford.bookbuddies.entity.BookCategory.FICTION;


@SpringBootTest
public class StockManagerControllerTest {

    @Autowired
    public StockManagerService stockManagerService;

    @Autowired
    public StockManagerRepository stockManagerRepository;

    @Autowired
    public BookRepository bookRepository;

    @Autowired
    public CustomerRepository customerRepository;

    @Autowired
    public BookStockRepository bookStockRepository;

    @Autowired
    public StockManagerController stockManagerController;


    @Test
    @DisplayName("add stock manager should throw exception when username is null")
    public void addStockManagerShouldThrowExceptionWhenUserNameIsNull() {
        StockManager stockManager = new StockManager(1029, null, "stocker");
        try {
            this.stockManagerController.login(stockManager);
        }
        catch (StockManagerException e) {
            Assertions.assertEquals("Admin Name should not be null", e.getMessage());
        }
    }


    @Test
    @DisplayName("Login should throw exception when username is null")
    public void loginShouldThrowExceptionWhenUserNameIsNull() throws StockManagerException {
        StockManager stockManager = new StockManager(80, null, "admin");
        stockManager = this.stockManagerRepository.save(stockManager);
        try {
            stockManager = stockManagerController.login(stockManager);
        }
        catch (StockManagerException e) {
            Assertions.assertEquals("Admin Name should not be null", e.getMessage());
        }
    }

    @Test
    @DisplayName("Login should throw exception when password is null")
    public void loginShouldThrowExceptionWhenPasswordIsNull() throws StockManagerException {
        StockManager stockManager = new StockManager(19, "admin", null);
        stockManager = this.stockManagerRepository.save(stockManager);
        try {
            stockManager = stockManagerController.login(stockManager);
        }
        catch (StockManagerException e) {
            Assertions.assertEquals("Admin Password should not be empty", e.getMessage());
        }
    }

    @Test
    @DisplayName("add new books should throw exception when Admin Id is null")
    public void addNewBooksShouldThrowExceptionWhenBookAdminIdIsNull() throws StockManagerException {
        StockManager stockManager = new StockManager(55, "title", "title");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = this.stockManagerController.login(stockManager);
        Book book = new Book("air", "Disney", 500.0, FICTION);
        Integer quantity = 2;
        BookStock bookStock = new BookStock(book, quantity);
        try {
            bookStock = this.stockManagerController.addNewBooks(bookStock);

        }
        catch (BookException | StockManagerException e)  {
            Assertions.assertEquals("Admin not logged", e.getMessage());
        }
    }


    @Test
    @DisplayName("add new books should throw exception when book stock is null")
    public void addNewBooksShouldThrowExceptionWhenBookStockIsNull() throws StockManagerException, BookException {
        StockManager stockManager = new StockManager(55, "title", "title");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = this.stockManagerController.login(stockManager);
        Book book = new Book("mac", "Disney", 500.0, FICTION);
        Integer quantity = 2;
        BookStock bookStock = new BookStock(book, quantity);
        try {
            this.stockManagerController.addNewBooks(null);

        }
        catch (BookException | StockManagerException e)  {
            Assertions.assertEquals("book should not be null", e.getMessage());
        }
        finally {
            this.stockManagerRepository.delete(stockManager);
        }
    }


    @Test
    @DisplayName("add new books should throw exception when book title is null")
    public void addNewBooksShouldThrowExceptionWhenBookTitleIsNull() {
        StockManager stockManager = new StockManager(55555, "micky", "micky");
        stockManager = this.stockManagerRepository.save(stockManager);
        try {
            stockManager = stockManagerController.login(stockManager);

            Book book = new Book(null, "Disney", 500.0, FICTION);
            Integer quantity = 2;
            BookStock bookStock = new BookStock(book, quantity);

            // Uncomment and use the assertion to expect the exception
            Assertions.assertThrows(BookException.class, ()-> stockManagerController.addNewBooks(bookStock));

        } catch (StockManagerException e) {
            // This catch block is redundant if you're using assertThrows, you can remove it
            Assertions.fail("An unexpected exception was thrown: " + e.getMessage());
        }
    }



    @Test
    @DisplayName("add new books should throw exception when book author is null")
    public void addNewBooksShouldThrowExceptionWhenBookAuthorIsNull() throws StockManagerException {
        StockManager stockManager = new StockManager(66, "addy", "addy");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerController.login(stockManager);
        Book book = new Book("Rapunzel", null, 500.0, FICTION);
        Integer quantity = 2;
        BookStock bookStock = new BookStock(book, quantity);
        Integer adminId = null;
        try {

            bookStock = this.stockManagerController.addNewBooks(bookStock);

        }
        catch (BookException | StockManagerException e) {
            Assertions.assertEquals("book Author should not be null", e.getMessage());
        }
        finally {
            this.stockManagerRepository.delete(stockManager);
        }
    }

    @Test
    @DisplayName("add new books should throw exception when book price is null")
    public void addNewBooksShouldThrowExceptionWhenBookPriceIsNull() throws StockManagerException {
        StockManager stockManager = new StockManager(77, "nully", "nully");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerController.login(stockManager);
        Book book = new Book("Rapunzel", "Disney", null, FICTION);
        Integer quantity = 2;
        BookStock bookStock = new BookStock(book, quantity);
        try {
            bookStock = this.stockManagerController.addNewBooks(bookStock);

        }
        catch (BookException | StockManagerException e) {
            Assertions.assertEquals("book Price should not be null", e.getMessage());
        }
        finally {
            this.stockManagerRepository.delete(stockManager);
        }
    }

    @Test
    @DisplayName("update books should throw exception when Admin id is null")
    public void updateBooksShouldThrowExceptionWhenAdminIdIsNull() throws StockManagerException {
        Book book = new Book("greens", "Disney", 500.0, FICTION);
        Integer quantity = 2;
        BookStock bookStock = new BookStock(book, quantity);
        try {
            bookStock = this.stockManagerController.updateBook(bookStock);

        }
        catch (BookException | StockManagerException e) {
            Assertions.assertEquals("Admin not logged", e.getMessage());
        }
    }
    @Test
    @DisplayName("update books should throw exception when book title is null")
    public void updateBooksShouldThrowExceptionWhenBookTitleIsNull() throws StockManagerException {
        StockManager stockManager = new StockManager(10, "admin", "admin");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerController.login(stockManager);
        Book book = new Book(null, "Disney", 500.0, FICTION);
        Integer quantity = 2;
        BookStock bookStock = new BookStock(book, quantity);
        try {
            bookStock = this.stockManagerController.updateBook(bookStock);

        }
        catch (BookException | StockManagerException e) {
            Assertions.assertEquals("book Title should not be null", e.getMessage());
        }
        finally {
            this.stockManagerRepository.delete(stockManager);
        }
    }

    @Test
    @DisplayName("update books should throw exception when book author is null")
    public void updateBooksShouldThrowExceptionWhenBookAuthorIsNull() throws StockManagerException {
        StockManager stockManager = new StockManager(111, "john", "john");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerController.login(stockManager);
        Book book = new Book("Rapunzel", null, 500.0, FICTION);
        Integer quantity = 2;
        BookStock bookStock = new BookStock(book, quantity);
        try {
            bookStock = this.stockManagerController.updateBook(bookStock);

        }
        catch (BookException | StockManagerException e) {
            Assertions.assertEquals("book Author should not be null", e.getMessage());
        }
        finally {
            this.stockManagerRepository.delete(stockManager);
        }
    }

    @Test
    @DisplayName("update books should throw exception when book price is null")
    public void updateBooksShouldThrowExceptionWhenBookPriceIsNull() throws StockManagerException {
        StockManager stockManager = new StockManager(123, "india", "india");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerController.login(stockManager);
        Book book = new Book("Rapunzel", "Disney", null, FICTION);
        Integer quantity = 2;
        BookStock bookStock = new BookStock(book, quantity);
        try {
            bookStock = this.stockManagerController.updateBook(bookStock);

        }
        catch (BookException | StockManagerException e) {
            Assertions.assertEquals("book Price should not be null", e.getMessage());
        }
        finally {
            this.stockManagerRepository.delete(stockManager);
        }
    }



    @Test
    @DisplayName("display All Books should throw exception when admin id is null")
    public void displayAllBooksShouldThrowExceptionWhenAdminIdIsNull() {
        try {
            this.stockManagerController.getAllBooks();
        }
        catch (StockManagerException e) {
            Assertions.assertEquals("Admin not logged", e.getMessage());
        }
    }

    @Test
    @DisplayName("display all customers should throw exception when admin id is null")
    public void displayAllCustomersShouldThrowExceptionWhenAdminIdIsNull() throws StockManagerException {
        try {
            this.stockManagerController.getAllCustomer();
        }
        catch (StockManagerException e) {
            Assertions.assertEquals("Admin not logged", e.getMessage());
        }
    }







}
