package com.ford.bookbuddies;

import com.ford.bookbuddies.controller.BookController;
import com.ford.bookbuddies.dao.*;
import com.ford.bookbuddies.dto.CustomerCartDto;
import com.ford.bookbuddies.entity.*;
import com.ford.bookbuddies.service.CustomerService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.ford.bookbuddies.entity.BookCategory.FICTION;

@SpringBootTest
public class BookControllerTest {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    public BookRepository bookRepository;
    @Autowired
    private BookDetailRepository bookDetailRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private BookController bookController;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BookStockRepository bookStockRepository;

    @Test
    void addBookToCartNullTestforUserId() {
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        Customer customer = new Customer("nithya", "nithya@gmail.com", "nithya", "nithya");
        try {
            customer = this.customerService.createCustomerAccount(customer);
            this.bookController.addBookToCart(new CustomerCartDto(null, book.getBookTitle(), 2));
        } catch (Exception e) {
            this.customerRepository.deleteById(customer.getId());
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertEquals("User not logged in to add Book To Cart", e.getMessage());
        }
    }

    @Test
    void addBookToCartNullTestforBookName() {
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        Customer customer = new Customer("nithya", "nithya@gmail.com", "nithya", "nithya");
        try {
            customer = this.customerService.createCustomerAccount(customer);
            this.bookController.addBookToCart(new CustomerCartDto(customer.getId(), null, 2));
        } catch (Exception e) {
            this.customerRepository.deleteById(customer.getId());
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertEquals("Book name should not be null", e.getMessage());
        }
    }

    @Test
    void addBookToCartNullTestforQuantity() {
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        Customer customer = new Customer("nithya", "nithya@gmail.com", "nithya", "nithya");
        try {
            customer = this.customerService.createCustomerAccount(customer);
            this.bookController.addBookToCart(new CustomerCartDto(customer.getId(), book.getBookTitle(), null));
        } catch (Exception e) {
            this.customerRepository.deleteById(customer.getId());
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertEquals("Quantity should not be null", e.getMessage());
        }
    }

    @Test
    void testNegativeQuantity() {
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        Customer customer = new Customer("nithya", "nithya@gmail.com", "nithya", "nithya");
        try {
            customer = this.customerService.createCustomerAccount(customer);
            this.bookController.addBookToCart(new CustomerCartDto(customer.getId(), book.getBookTitle(), -1));
        } catch (Exception e) {
            this.customerRepository.deleteById(customer.getId());
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertEquals("Quantity should be greater than zero", e.getMessage());
        }
    }

    @Test
    void testIsDtoNull() {
        try {
            this.bookController.addBookToCart(null);
        } catch (Exception e) {

            Assertions.assertEquals("CUSTOMERCARTDTO IS NULL", e.getMessage());
        }
    }

    @Test
    @Transactional
    void addProductToCartTest() throws Exception {
        Book book = new Book("POK", "potter", 250.0, FICTION);
        this.bookRepository.save(book);
        this.bookStockRepository.save(new BookStock(book,20));
        Customer customer = new Customer("nithya", "nithya@gmail.com", "nithya", "nithya");
        try {
            customer = this.customerService.createCustomerAccount(customer);
            Cart cart = this.bookController.addBookToCart(new CustomerCartDto(customer.getId(), book.getBookTitle(), 3));
            this.customerRepository.deleteById(customer.getId());
            this.cartRepository.deleteById(customer.getCart().getId());
            this.bookDetailRepository.deleteByBook(book);
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertFalse(cart.getBooksDetails().isEmpty());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void buyBookInCartNullTestforUserId() {
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        Customer customer = new Customer("nithya", "nithya@gmail.com", "nithya", "nithya");
        try {
            customer = this.customerService.createCustomerAccount(customer);
            this.bookController.buyBook(new CustomerCartDto(null, book.getBookTitle(), 2));
        } catch (Exception e) {
            this.customerRepository.deleteById(customer.getId());
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertEquals("User not logged in to buy Book", e.getMessage());
        }
    }

    @Test
    void buyBookInCartNullTestforBookName() {
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        Customer customer = new Customer("nithya", "nithya@gmail.com", "nithya", "nithya");
        try {
            customer = this.customerService.createCustomerAccount(customer);
            this.bookController.buyBook(new CustomerCartDto(customer.getId(), null, 2));
        } catch (Exception e) {
            this.customerRepository.deleteById(customer.getId());
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertEquals("Book name should not be null", e.getMessage());
        }
    }

    @Test
    void buyBookInCartNullTestforQuantity() {
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        Customer customer = new Customer("nithya", "nithya@gmail.com", "nithya", "nithya");
        try {
            customer = this.customerService.createCustomerAccount(customer);
            this.bookController.buyBook(new CustomerCartDto(customer.getId(), book.getBookTitle(), null));
        } catch (Exception e) {
            this.customerRepository.deleteById(customer.getId());
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertEquals("Quantity should not be null", e.getMessage());
        }
    }

    @Test
    void testNegativeQuantityInBuyBook() {
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        Customer customer = new Customer("nithya", "nithya@gmail.com", "nithya", "nithya");
        try {
            customer = this.customerService.createCustomerAccount(customer);
            this.bookController.buyBook(new CustomerCartDto(customer.getId(), book.getBookTitle(), -1));
        } catch (Exception e) {
            this.customerRepository.deleteById(customer.getId());
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertEquals("Quantity should be greater than zero", e.getMessage());
        }
    }

    @Test
    void testIsDtoNullInBuyBook() {
        try {
            this.bookController.addBookToCart(null);
        } catch (Exception e) {

            Assertions.assertEquals("CUSTOMERCARTDTO IS NULL", e.getMessage());
        }
    }

    @Test
    @Transactional
    void buyBookTest() throws Exception {
        Customer customer = new Customer("nithya", "nithya@gmail.com", "nithya", "nithya");
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        this.bookStockRepository.save(new BookStock(book,20));
        try {
            customer = this.customerService.createCustomerAccount(customer);
            List<BookDetail> list = this.bookController.buyBook(new CustomerCartDto(customer.getId(), book.getBookTitle(), 3));
            this.customerRepository.deleteById(customer.getId());
            this.bookDetailRepository.deleteByBook(book);
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertFalse(list.isEmpty());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

}



