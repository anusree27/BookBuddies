package com.ford.bookbuddies;



import com.ford.bookbuddies.dao.*;
import com.ford.bookbuddies.entity.*;
import com.ford.bookbuddies.service.BookService;
import com.ford.bookbuddies.service.CustomerService;
import com.ford.bookbuddies.service.DeleteService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.ford.bookbuddies.entity.BookCategory.FICTION;

@SpringBootTest
public class BookServiceTests {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private BookStockRepository bookStockRepository;

    @Autowired
    public BookRepository bookRepository;

    @Autowired
    public BookDetailRepository bookDetailRepository;

    @Test
    @Transactional
    void addProductToCartTest() throws Exception {
        Customer customer=new Customer("nithya","nithya@gmail.com","nithya","nithya");
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        this.bookStockRepository.save(new BookStock(book,20));
        try{
            customer=this.customerService.createCustomerAccount(customer);
            Cart cart=this.bookService.addProductToCart(customer.getId(), book.getBookTitle(), 3);
            this.customerRepository.deleteById(customer.getId());
            this.cartRepository.deleteById(customer.getCart().getId());
            this.bookDetailRepository.deleteByBook(book);
            this.bookStockRepository.deleteBookStockByBook(book);
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertFalse(cart.getBooksDetails().isEmpty());

        }
        catch(Exception e){
            Assertions.fail(e.getMessage());
        }
    }
    @Test
    void addProductToCartTestRegisteredUser() throws Exception {

        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        try{
            this.bookService.addProductToCart(100, book.getBookTitle(), 3);
        }
        catch(Exception e){
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertEquals("User is not registered",e.getMessage());
        }
    }
    @Test
    void addProductToCartTestBook() throws Exception {
        Customer customer=new Customer("nithya","nithya@gmail.com","nithya","nithya");
        try{
            customer=this.customerService.createCustomerAccount(customer);
            this.bookService.addProductToCart(customer.getId(), "NOBOOK", 3);
        }
        catch(Exception e){
            this.customerRepository.deleteById(customer.getId());
            Assertions.assertEquals("Book doesn't exists",e.getMessage());
        }
    }
    @Test
    @Transactional
    void addProductToCartTestQuantity() throws Exception {
        Customer customer=new Customer("nithya","nithya@gmail.com","nithya","nithya");
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        this.bookStockRepository.save(new BookStock(book,5));
        try {
            customer = this.customerService.createCustomerAccount(customer);
            this.bookService.addProductToCart(customer.getId(), book.getBookTitle(), 6);
        }
        catch(Exception e){
            this.customerRepository.deleteById(customer.getId());
            this.cartRepository.deleteById(customer.getCart().getId());
            this.bookDetailRepository.deleteByBook(book);
            this.bookStockRepository.deleteBookStockByBook(book);
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertEquals("Available Stock quantity is lesser than quantity needed",e.getMessage());
        }
    }
    @Test
    void buyBookInCartTestRegisteredUser() throws Exception {

        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        try{
            this.bookService.buyBook(100, book.getBookTitle(), 3);
        }
        catch(Exception e){
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertEquals("User is not registered",e.getMessage());
        }
    }
    @Test
    void buyBookInCartTestBook() throws Exception {
        Customer customer=new Customer("nithya","nithya@gmail.com","nithya","nithya");
        try{
            customer=this.customerService.createCustomerAccount(customer);
            this.bookService.buyBook(customer.getId(), "NOBOOK", 3);
        }
        catch(Exception e){
            this.customerRepository.deleteById(customer.getId());
            Assertions.assertEquals("Book doesn't exists",e.getMessage());
        }
    }
    @Test
    @Transactional
    void buyBookTestQuantity() throws Exception {
        Customer customer=new Customer("nithya","nithya@gmail.com","nithya","nithya");
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        this.bookStockRepository.save(new BookStock(book,10));
        try{
            customer=this.customerService.createCustomerAccount(customer);
            this.bookService.buyBook(customer.getId(), book.getBookTitle(), 11);
        }
        catch(Exception e){
            this.customerRepository.deleteById(customer.getId());
            this.bookDetailRepository.deleteByBook(book);
            this.bookStockRepository.deleteBookStockByBook(book);
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertEquals("Available Stock quantity is lesser than quantity needed",e.getMessage());
        }
    }
    @Test
    @Transactional
    void buyBookTest() throws Exception {
        Customer customer=new Customer("nithya","nithya@gmail.com","nithya","nithya");
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        this.bookStockRepository.save(new BookStock(book,25));
        try{
            customer=this.customerService.createCustomerAccount(customer);
            List<BookDetail> list=this.bookService.buyBook(customer.getId(), book.getBookTitle(), 3);
            this.customerRepository.deleteById(customer.getId());
            this.bookDetailRepository.deleteByBook(book);
            this.bookStockRepository.deleteBookStockByBook(book);
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertFalse(list.isEmpty());
        }
        catch(Exception e){
            Assertions.fail(e.getMessage());
        }
    }

}

