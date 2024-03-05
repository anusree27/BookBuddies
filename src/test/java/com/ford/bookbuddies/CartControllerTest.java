package com.ford.bookbuddies;

import com.ford.bookbuddies.controller.BookController;
import com.ford.bookbuddies.controller.CartController;
import com.ford.bookbuddies.dao.*;
import com.ford.bookbuddies.dto.Bookdto;
import com.ford.bookbuddies.dto.CustomerCartDto;
import com.ford.bookbuddies.dto.OrderBooksdto;
import com.ford.bookbuddies.entity.*;
import com.ford.bookbuddies.service.BookService;
import com.ford.bookbuddies.service.CustomerService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.ford.bookbuddies.entity.BookCategory.FICTION;

@SpringBootTest
public class CartControllerTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookStockRepository bookStockRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CartController cartController;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BookDetailRepository bookDetailRepository;
    @Autowired
    private BookController bookController;

    @Test
    @Transactional
    void buyBooksInCartTest(){
        Customer customer=new Customer("nithya","nithya@gmail.com","nithya","nithya");
        List<Integer> booksToBuyList=new ArrayList<>();
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        this.bookStockRepository.save(new BookStock(book,25));
        booksToBuyList.add(book.getBookId());
        Book book2=new Book("dumbledore","jk",100.0,FICTION);
        this.bookRepository.save(book2);
        this.bookStockRepository.save(new BookStock(book2,25));
        booksToBuyList.add(book2.getBookId());
        try{
            customer=this.customerService.createCustomerAccount(customer);
            this.bookService.addProductToCart(customer.getId(),book.getBookTitle(),2);
            this.bookService.addProductToCart(customer.getId(),book2.getBookTitle(),1);
            List<BookDetail> list=this.cartController.buyBooks(new OrderBooksdto(customer.getId(),booksToBuyList));
            this.customerRepository.deleteById(customer.getId());
            this.cartRepository.deleteById(customer.getCart().getId());
            this.bookDetailRepository.deleteByBook(book);
            this.bookStockRepository.deleteBookStockByBook(book);
            this.bookRepository.deleteById(book.getBookId());
            this.bookDetailRepository.deleteByBook(book2);
            this.bookStockRepository.deleteBookStockByBook(book);
            this.bookRepository.deleteById(book2.getBookId());
            Assertions.assertEquals(2, list.size());
        }
        catch(Exception e){
            Assertions.fail(e.getMessage());
        }
    }
    @Test
    void buyBookDtoNullTest(){
        OrderBooksdto orderBooksdto=null;
        try{
            this.cartController.buyBooks(orderBooksdto);
        }
        catch(Exception e){
            Assertions.assertEquals("OrderBooksDto is null",e.getMessage());
        }
    }
    @Test
    void buyBookInCartNullTestForUserId(){
        List<Integer> booksToBuyList=new ArrayList<>();
        try{
           this.cartController.buyBooks(new OrderBooksdto(null,booksToBuyList));
        }
        catch(Exception e){
            Assertions.assertEquals("User not logged in",e.getMessage());
        }
    }
    @Test
    void buyBookInCartNullTestForList(){
        Customer customer=new Customer("nithya","nithya@gmail.com","nithya","nithya");
        List<Integer> booksToBuyList=null;
        try{
            this.customerService.createCustomerAccount(customer);
            this.cartController.buyBooks(new OrderBooksdto(customer.getId(),booksToBuyList));
        }
        catch(Exception e){
            this.customerRepository.deleteById(customer.getId());
            Assertions.assertEquals("Books to Buy List is Null",e.getMessage());
        }
    }
    @Test
    void buyBookInCartIsEmptyTestForList(){
        Customer customer=new Customer("nithya","nithya@gmail.com","nithya","nithya");
        List<Integer> booksToBuyList=new ArrayList<>();
        try{
            this.customerService.createCustomerAccount(customer);
            this.cartController.buyBooks(new OrderBooksdto(customer.getId(),booksToBuyList));
        }
        catch(Exception e){
            this.customerRepository.deleteById(customer.getId());
            Assertions.assertEquals("Books to Buy is Empty!",e.getMessage());
        }
    }
    @Test
    void increaseQuantityInCartDtoNullTest() {
        try {
           this.cartController.increasebookQuantity(null);
        }
        catch(Exception e){
            Assertions.assertEquals("BookDto is null",e.getMessage());
        }
    }
    @Test
    void increaseQuantityInCartTestForIsUserIdNull() {
        Book book = new Book("harry", "potter", 200.0, FICTION);
        try {
            this.cartController.increasebookQuantity(new Bookdto(null,book.getBookId()));
        }
        catch(Exception e){
            Assertions.assertEquals("User not logged in",e.getMessage());
        }
    }
    @Test
    void increaseQuantityInCartTestForIsBookIdNull() {
        Customer customer=new Customer("nithya","nithya@gmail.com","nithya","nithya");
        try {
            this.customerService.createCustomerAccount(customer);
            this.cartController.increasebookQuantity(new Bookdto(customer.getId(), null));
        }
        catch (Exception e) {
            this.customerRepository.deleteById(customer.getId());
            Assertions.assertEquals("Book Id can't be null", e.getMessage());
        }
    }

    @Test
    @Transactional
    void increaseQuantityInCartTest() {
        Customer customer = new Customer("nithya", "nithya@gmail.com", "nithya", "nithya");
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        this.bookStockRepository.save(new BookStock(book,25));
        try {
            customer = this.customerService.createCustomerAccount(customer);
            Integer quantity=3;
            this.bookService.addProductToCart(customer.getId(), book.getBookTitle(), quantity);
            Cart cart=this.cartController.increasebookQuantity(new Bookdto(customer.getId(),book.getBookId()));
            BookDetail bookDetail=cart.getBooksDetails().stream().filter((bd)->bd.getBook().equals(book)).findAny().get();
            this.customerRepository.deleteById(customer.getId());
            this.cartRepository.deleteById(customer.getCart().getId());
            this.bookDetailRepository.deleteByBook(book);
            this.bookStockRepository.deleteBookStockByBook(book);
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertEquals(quantity+1,bookDetail.getQuantity());
        }
        catch(Exception e){
            Assertions.fail(e.getMessage());
        }
    }
    @Test
    void decreaseQuantityInCartDtoNullTest() {
        try {
            this.cartController.decreasebookQuantity(null);
        }
        catch(Exception e){
            Assertions.assertEquals("BookDto is null",e.getMessage());
        }
    }
    @Test
    void decreaseQuantityInCartTestForIsUserIdNull() {
        Book book = new Book("harry", "potter", 200.0, FICTION);
        try {
            this.cartController.decreasebookQuantity(new Bookdto(null,book.getBookId()));
        }
        catch(Exception e){
            Assertions.assertEquals("User not logged in",e.getMessage());
        }
    }
    @Test
    void decreaseQuantityInCartTestForIsBookIdNull() {
        Customer customer=new Customer("nithya","nithya@gmail.com","nithya","nithya");
        try {
            this.customerService.createCustomerAccount(customer);
            this.cartController.decreasebookQuantity(new Bookdto(customer.getId(), null));
        }
        catch (Exception e) {
            this.customerRepository.deleteById(customer.getId());
            Assertions.assertEquals("Book Id can't be null", e.getMessage());
        }
    }

    @Test
    @Transactional
    void decreaseQuantityInCartTest() {
        Customer customer = new Customer("nithya", "nithya@gmail.com", "nithya", "nithya");
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        this.bookStockRepository.save(new BookStock(book,25));
        try {
            customer = this.customerService.createCustomerAccount(customer);
            Integer quantity=3;
            this.bookService.addProductToCart(customer.getId(), book.getBookTitle(), quantity);
            Cart cart=this.cartController.decreasebookQuantity(new Bookdto(customer.getId(),book.getBookId()));
            BookDetail bookDetail=cart.getBooksDetails().stream().filter((bd)->bd.getBook().equals(book)).findAny().get();
            this.customerRepository.deleteById(customer.getId());
            this.cartRepository.deleteById(customer.getCart().getId());
            this.bookDetailRepository.deleteByBook(book);
            this.bookStockRepository.deleteBookStockByBook(book);
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertEquals(quantity-1,bookDetail.getQuantity());
        }
        catch(Exception e){
            Assertions.fail(e.getMessage());
        }
    }
    @Test
    void deleteProductFromCartDtoNullTest() {
        try {
            this.cartController.deleteProductFromCart(null);
        } catch (Exception e) {
            Assertions.assertEquals("BookDto is null", e.getMessage());
        }
    }

    @Test
    void deleteProductFromCartTestForIsUserIdNull() {
        Book book = new Book("harry", "potter", 200.0, FICTION);
        try {
            this.cartController.deleteProductFromCart(new Bookdto(null, book.getBookId()));
        } catch (Exception e) {
            Assertions.assertEquals("User not logged in", e.getMessage());
        }
    }

    @Test
    void deleteProductFromCartTestForIsBookIdNull() {
        Customer customer = new Customer("nithya", "nithya@gmail.com", "nithya", "nithya");
        try {
            this.customerService.createCustomerAccount(customer);
            this.cartController.deleteProductFromCart(new Bookdto(customer.getId(), null));
        } catch (Exception e) {
            this.customerRepository.deleteById(customer.getId());
            Assertions.assertEquals("Book Id can't be null", e.getMessage());
        }
    }

    @Test
    @Transactional
    void deleteProductFromCartTest() {
        Book book = new Book("POK", "potter", 250.0, FICTION);
        this.bookRepository.save(book);
        this.bookStockRepository.save(new BookStock(book,25));
        Customer customer = new Customer("nithya", "nithya@gmail.com", "nithya", "nithya");
        try {
            customer = this.customerService.createCustomerAccount(customer);
            this.bookController.addBookToCart(new CustomerCartDto(customer.getId(), book.getBookTitle(), 3));
            Cart deletedCart = this.cartController.deleteProductFromCart(new Bookdto(customer.getId(), book.getBookId()));
            this.customerRepository.deleteById(customer.getId());
            this.cartRepository.deleteById(customer.getCart().getId());
            this.bookDetailRepository.deleteByBook(book);
            this.bookStockRepository.deleteBookStockByBook(book);
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertTrue(deletedCart.getBooksDetails().isEmpty());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}
