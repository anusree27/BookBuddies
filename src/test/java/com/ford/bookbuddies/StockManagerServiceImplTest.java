package com.ford.bookbuddies;

import com.ford.bookbuddies.dao.BookRepository;
import com.ford.bookbuddies.dao.BookStockRepository;
import com.ford.bookbuddies.dao.CustomerRepository;
import com.ford.bookbuddies.dao.StockManagerRepository;
import com.ford.bookbuddies.entity.Book;
import com.ford.bookbuddies.entity.BookStock;
import com.ford.bookbuddies.entity.Customer;
import com.ford.bookbuddies.entity.StockManager;
import com.ford.bookbuddies.exception.BookException;
import com.ford.bookbuddies.exception.StockManagerException;
import com.ford.bookbuddies.service.StockManagerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.ford.bookbuddies.entity.BookCategory.FICTION;

@SpringBootTest
public class StockManagerServiceImplTest {

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


    @Test
    public void loginTest() throws StockManagerException {
        StockManager stockManager = new StockManager(20, "admin", "admin");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        Assertions.assertNotNull(stockManager);
    }

    @Test
    @DisplayName("Login should throw exception when username is null")
    public void loginShouldThrowExceptionWhenUserNameIsNull() throws StockManagerException {
        StockManager stockManager = new StockManager(80, null, "admin");
        stockManager = this.stockManagerRepository.save(stockManager);
        try {
            stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        }
        catch (StockManagerException e) {
            Assertions.assertEquals("name should not be null", e.getMessage());
        }
    }

    @Test
    @DisplayName("Login should throw exception when password is null")
    public void loginShouldThrowExceptionWhenPasswordIsNull() throws StockManagerException {
        StockManager stockManager = new StockManager(19, "admin", null);
        stockManager = this.stockManagerRepository.save(stockManager);
        try {
            stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        }
        catch (StockManagerException e) {
            Assertions.assertEquals("Password should not be null", e.getMessage());
        }
    }

    @Test
    @DisplayName("Login should throw exception when Stock manager not registered")
    public void loginShouldThrowExceptionWhenStockManagerNotRegistered() throws StockManagerException {
        try {
            StockManager stockManager = stockManagerService.login("john", "john");
        }
        catch (StockManagerException e) {
            Assertions.assertEquals("Admin is not registered", e.getMessage());
        }
    }

    @Test
    @DisplayName("Login should throw exception when password is incorrect")
    public void loginShouldThrowExceptionWhenPasswordIsIncorrect() throws StockManagerException {
        StockManager stockManager = new StockManager(200, "john", "john");
        stockManager = this.stockManagerRepository.save(stockManager);
        try {
            stockManager = stockManagerService.login(stockManager.getName(), "password");
        }
        catch (StockManagerException e) {
            Assertions.assertEquals("Incorrect password", e.getMessage());
        }
    }

    @Test
    public void addNewBooksTest() throws BookException, StockManagerException {
        StockManager stockManager = new StockManager(44, "bob", "bob");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        Book book = new Book("Rapunzel", "Disney", 500.0, FICTION);
        Integer quantity = 2;
        BookStock bookStock = new BookStock(book, quantity);
        try {
            bookStock = this.stockManagerService.addNewBooks(stockManager.getAdminId(), bookStock);
            Assertions.assertNotNull(bookStock);
        } catch (BookException | StockManagerException e) {
            e.printStackTrace();
        }
        this.stockManagerRepository.delete(stockManager);
    }

    @Test
    @DisplayName("add new books should throw exception when Admin Id is null")
    public void addNewBooksShouldThrowExceptionWhenBookAdminIdIsNull() throws StockManagerException {
        Book book = new Book(null, "Disney", 500.0, FICTION);
        Integer quantity = 2;
        BookStock bookStock = new BookStock(book, quantity);
        try {
            bookStock = this.stockManagerService.addNewBooks(null,bookStock);

        }
        catch (BookException | StockManagerException e)  {
            Assertions.assertEquals("Admin not logged", e.getMessage());
        }
    }



    @Test
    @DisplayName("add new books should throw exception when book title is null")
    public void addNewBooksShouldThrowExceptionWhenBookTitleIsNull() throws StockManagerException {
        StockManager stockManager = new StockManager(55, "title", "title");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        Book book = new Book(null, "Disney", 500.0, FICTION);
        Integer quantity = 2;
        BookStock bookStock = new BookStock(book, quantity);
        try {
            bookStock = this.stockManagerService.addNewBooks(stockManager.getAdminId(),bookStock);

        }
        catch (BookException | StockManagerException e)  {
            Assertions.assertEquals("book Title should not be null", e.getMessage());
        }
        finally {
            this.stockManagerRepository.delete(stockManager);
        }

    }

    @Test
    @DisplayName("add new books should throw exception when book author is null")
    public void addNewBooksShouldThrowExceptionWhenBookAuthorIsNull() throws StockManagerException {
        StockManager stockManager = new StockManager(66, "addy", "addy");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        Book book = new Book("Rapunzel", null, 500.0, FICTION);
        Integer quantity = 2;
        BookStock bookStock = new BookStock(book, quantity);
        try {
            bookStock = this.stockManagerService.addNewBooks(stockManager.getAdminId(),bookStock);

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
        stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        Book book = new Book("Rapunzel", "Disney", null, FICTION);
        Integer quantity = 2;
        BookStock bookStock = new BookStock(book, quantity);
        try {
            bookStock = this.stockManagerService.addNewBooks(stockManager.getAdminId(),bookStock);

        }
        catch (BookException | StockManagerException e) {
            Assertions.assertEquals("book Price should not be null", e.getMessage());
        }
        finally {
            this.stockManagerRepository.delete(stockManager);
        }
    }

    @Test
    @DisplayName("add new books should throw exception when book price is less than zero")
    public void addNewBooksShouldThrowExceptionWhenBookPriceIsLessThanZero() throws StockManagerException {
        StockManager stockManager = new StockManager(88, "zero", "zero");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        Book book = new Book("Rapunzel", "Disney", -10.0, FICTION);
        Integer quantity = 2;
        BookStock bookStock = new BookStock(book, quantity);
        try {
            bookStock = this.stockManagerService.addNewBooks(stockManager.getAdminId(),bookStock);

        }
        catch (BookException | StockManagerException e) {
            Assertions.assertEquals("book Price should not be less than zero", e.getMessage());
        }
        finally {
            this.stockManagerRepository.delete(stockManager);
        }
    }

    //update book tests

    @Test
    public void updateBookTest() throws BookException, StockManagerException {
        StockManager stockManager = new StockManager(99, "srock", "srock");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        Book book = new Book("Rapunzel", "Disney", 500.0, FICTION);
        Integer quantity = 2;
        BookStock bookStock = new BookStock(book, quantity);
        try {
            bookStock = this.stockManagerService.updateBook(stockManager.getAdminId(),bookStock);
            Assertions.assertNotNull(bookStock);
        }
        catch (BookException | StockManagerException e) {
            e.printStackTrace();
        }
        finally {
            this.stockManagerRepository.delete(stockManager);
        }
    }


    @Test
    @DisplayName("update books should throw exception when Admin id is null")
    public void updateBooksShouldThrowExceptionWhenAdminIdIsNull() throws StockManagerException {
         Book book = new Book(null, "Disney", 500.0, FICTION);
        Integer quantity = 2;
        BookStock bookStock = new BookStock(book, quantity);
        try {
            bookStock = this.stockManagerService.updateBook(null,bookStock);

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
        stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        Book book = new Book(null, "Disney", 500.0, FICTION);
        Integer quantity = 2;
        BookStock bookStock = new BookStock(book, quantity);
        try {
            bookStock = this.stockManagerService.updateBook(stockManager.getAdminId(),bookStock);

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
        stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        Book book = new Book("Rapunzel", null, 500.0, FICTION);
        Integer quantity = 2;
        BookStock bookStock = new BookStock(book, quantity);
        try {
            bookStock = this.stockManagerService.updateBook(stockManager.getAdminId(),bookStock);

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
        stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        Book book = new Book("Rapunzel", "Disney", null, FICTION);
        Integer quantity = 2;
        BookStock bookStock = new BookStock(book, quantity);
        try {
            bookStock = this.stockManagerService.updateBook(stockManager.getAdminId(),bookStock);

        }
        catch (BookException | StockManagerException e) {
            Assertions.assertEquals("book Price should not be null", e.getMessage());
        }
        finally {
            this.stockManagerRepository.delete(stockManager);
        }
    }

    @Test
    @DisplayName("update books should throw exception when book price is less than zero")
    public void updateNewBooksShouldThrowExceptionWhenBookPriceIsLessThanZero() throws StockManagerException {
        StockManager stockManager = new StockManager(113, "ids", "ids");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        Book book = new Book("Rapunzel", "Disney", -10.0, FICTION);
        Integer quantity = 2;
        BookStock bookStock = new BookStock(book, quantity);
        try {
            bookStock = this.stockManagerService.updateBook(stockManager.getAdminId(),bookStock);

        }
        catch (BookException | StockManagerException e) {
            Assertions.assertEquals("book Price should not be less than zero", e.getMessage());
        }
        finally {
            this.stockManagerRepository.delete(stockManager);
        }
    }


    //delete books by name


    @Test
    public void deleteBookByNameTest() throws BookException, StockManagerException {
        StockManager stockManager = new StockManager(29, "del", "del");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        Book book = new Book("intel", "stock", 200.0, FICTION);
        Integer quantity = 2;
        Boolean result;
        BookStock bookStock = new BookStock(book, quantity);
        bookStock = this.stockManagerService.addNewBooks(stockManager.getAdminId(),bookStock);
        bookStock = this.bookStockRepository.save(bookStock);
        try {
            result = this.stockManagerService.deleteBookByBookName(stockManager.getAdminId(), "intel");
            Assertions.assertEquals(true, result);
        }
        catch (BookException | StockManagerException e) {
            e.printStackTrace();
        }
    }


    @Test
    @DisplayName("delete book by name should throw exception when admin id is null")
    public void deleteBookByNameShouldThrowExceptionWhenAdminIdIsNull() throws StockManagerException, BookException {
        StockManager stockManager = new StockManager(2222, "throw", "throw");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        Book book = new Book("Rapunzel", "Disney", 200.0, FICTION);
        Integer quantity = 2;
        Boolean result;
        BookStock bookStock = new BookStock(book, quantity);
        bookStock = this.stockManagerService.addNewBooks(stockManager.getAdminId(),bookStock);
        bookStock = this.bookStockRepository.save(bookStock);
        try {
            result = this.stockManagerService.deleteBookByBookName(null, "Rapunzel");

        }
        catch (BookException | StockManagerException e) {
            Assertions.assertEquals("Admin not logged", e.getMessage());
        }
        finally {
            this.stockManagerRepository.delete(stockManager);
        }
    }


    @Test
    @DisplayName("delete book by name should throw exception when book name is null")
    public void deleteBookByNameShoukdThrowExceptionWhenBookNameIsNull() throws BookException, StockManagerException{
        StockManager stockManager = new StockManager(29, "del", "del");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        Book book = new Book("coding", "me", 200.0, FICTION);
        Integer quantity = 2;
        Boolean result;
        BookStock bookStock = new BookStock(book, quantity);
        bookStock = this.stockManagerService.addNewBooks(stockManager.getAdminId(),bookStock);
        bookStock = this.bookStockRepository.save(bookStock);
        try {
            result = this.stockManagerService.deleteBookByBookName(stockManager.getAdminId(), null);

        }
        catch (BookException | StockManagerException e) {
            Assertions.assertEquals("Book name should not be null", e.getMessage());
        }
        finally {
            this.stockManagerRepository.delete(stockManager);
        }
    }


    @Test
    @DisplayName("delete boook by name should throw exception when book name is not present")
    public void deleteBookByNameShouldThrowExceptionWhenBookNameIsNotPresent() throws StockManagerException, BookException {
        StockManager stockManager = new StockManager(999, "present", "present");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
       Boolean result = null;
        try {
            result = this.stockManagerService.deleteBookByBookName(stockManager.getAdminId(), "tamilnadu");

        }
        catch (BookException | StockManagerException e) {
            Assertions.assertEquals("Book is not present in the stocks to delete", e.getMessage());
        }
        finally {
            this.stockManagerRepository.delete(stockManager);
        }
    }

    //display all books

    @Test
    public void displayAllBooksTest() throws BookException, StockManagerException {
        StockManager stockManager = new StockManager(456, "display", "display");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        Book book1 = new Book("book1", "book1", 200.0, FICTION);
        Book book2 = new Book("book2", "book2", 200.0, FICTION);
        Integer quantity = 2;
        Boolean result;
        BookStock bookStock1 = new BookStock(book1, quantity);
        BookStock bookStock2 = new BookStock(book2, quantity);
        bookStock1 = this.stockManagerService.addNewBooks(stockManager.getAdminId(),bookStock1);
        bookStock2 = this.stockManagerService.addNewBooks(stockManager.getAdminId(),bookStock2);
        bookStock1 = this.bookStockRepository.save(bookStock1);
        bookStock2 = this.bookStockRepository.save(bookStock2);
        List<BookStock> bookStockList = null;
        try {
            bookStockList = this.stockManagerService.displayAllBooks(stockManager.getAdminId());
            Assertions.assertNotNull(bookStockList);
        }
        catch (StockManagerException e) {
            e.printStackTrace();
        }
        finally {
            this.stockManagerRepository.delete(stockManager);
        }
    }

    @Test
    @DisplayName("display All Books should throw exception when admin id is null")
    public void displayAllBooksShouldThrowExceptionWhenAdminIdIsNull() {
        try {
            this.stockManagerService.displayAllBooks(null);
        }
        catch (StockManagerException e) {
            Assertions.assertEquals("Admin not logged", e.getMessage());
        }
    }


    // display all customers

    @Test
    public void displayAllCustomersTest() throws StockManagerException {
        StockManager stockManager = new StockManager(144, "tester", "tester");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        Customer customer = new Customer("customer", "customer@gmail.com", "customer", "customer", null);
        customer = this.customerRepository.save(customer);
        List<Customer> customerList = null;
        try {
            customerList = this.stockManagerService.displayAllCustomer(stockManager.getAdminId());
            Assertions.assertNotNull(customerList);
        }
        catch (StockManagerException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("display all customers should throw exception when admin id is null")
    public void displayAllCustomersShouldThrowExceptionWhenAdminIdIsNull() throws StockManagerException {
        try {
            this.stockManagerService.displayAllCustomer(null);
        }
        catch (StockManagerException e) {
            Assertions.assertEquals("Admin not logged", e.getMessage());
        }
    }


    //view books count by name

    @Test
    public void viewBooksCountByNameTest() throws StockManagerException, BookException {
        StockManager stockManager = new StockManager(1002, "house", "house");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        Book book = new Book("black", "black", 200.0, FICTION);
        Integer quantity = 20;
        BookStock bookStock = new BookStock(book, quantity);
        bookStock = this.stockManagerService.addNewBooks(stockManager.getAdminId(),bookStock);
        bookStock = this.bookStockRepository.save(bookStock);
        Integer count = null;
        try {
            count = this.stockManagerService.viewBooksCountByName(stockManager.getAdminId(), "black");
            Assertions.assertEquals(count, quantity);
        }
        catch (StockManagerException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("view book count by name should throw exception when admin id is null")
    public void viewBooksCountByNameShouldThrowExceptionWhenAdminIdIsNull() {
        try {
            this.stockManagerService.viewBooksCountByName(null, "abcd");
        }
        catch (StockManagerException | BookException e) {
            Assertions.assertEquals("Admin not logged", e.getMessage());
        }
    }


    @Test
    @DisplayName("view book count by name should throw exception when book is not present")
    public void viewBooksCountByNameShouldThrowExceptionWhenBookIsNotPresent() throws StockManagerException {
        StockManager stockManager = new StockManager(2002, "counter", "counter");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        try {
            this.stockManagerService.viewBooksCountByName(stockManager.getAdminId(), "bookname");
        }
        catch (StockManagerException | BookException e) {
            Assertions.assertEquals("book is not present", e.getMessage());
        }
    }

    //update books count by name test

    @Test
    public void updateBooksCountByNameTest() throws StockManagerException, BookException {
        StockManager stockManager = new StockManager(8080, "viewer", "viewer");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        Book book = new Book("redbook", "red", 200.0, FICTION);
        Integer quantity = 20;
        BookStock bookStock = new BookStock(book, quantity);
        bookStock = this.stockManagerService.addNewBooks(stockManager.getAdminId(),bookStock);
        bookStock = this.bookStockRepository.save(bookStock);

        try {
            bookStock = this.stockManagerService.updateBookCountByName(stockManager.getAdminId(), book.getBookTitle(), quantity);
            Assertions.assertNotNull(bookStock);
        }
        catch (StockManagerException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("updatebook count by name should throw exception when admin id is null")
    public void updateBooksCountByNameShouldThrowExceptionWhenAdminIdIsNull() {
        try {
            this.stockManagerService.updateBookCountByName(null, "hello", 2);
        }
        catch (StockManagerException | BookException e) {
            Assertions.assertEquals("Admin not logged", e.getMessage());
        }
    }


    @Test
    @DisplayName("updatebook count by name should throw exception when book name is null")
    public void updateBooksCountByNameShouldThrowExceptionWhenBookNameIsNull() throws StockManagerException {
        StockManager stockManager = new StockManager(2004, "trees", "trees");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        try {
            this.stockManagerService.updateBookCountByName(stockManager.getAdminId(), null, 2);
        }
        catch (StockManagerException | BookException e) {
            Assertions.assertEquals("Book name should not be null", e.getMessage());
        }
    }

    @Test
    @DisplayName("update book count by name should throw exception when book is not present")
    public void updateBooksCountByNameShouldThrowExceptionWhenBookIsNotPresent() throws StockManagerException {
        StockManager stockManager = new StockManager(2006, "bags", "bags");
        stockManager = this.stockManagerRepository.save(stockManager);
        stockManager = stockManagerService.login(stockManager.getName(), stockManager.getPassword());
        try {
            this.stockManagerService.updateBookCountByName(stockManager.getAdminId(), "apples", 20);
        }
        catch (StockManagerException | BookException e) {
            Assertions.assertEquals("book is not present", e.getMessage());
        }
    }

}
