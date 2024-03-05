package com.ford.bookbuddies;


import com.ford.bookbuddies.dao.*;
import com.ford.bookbuddies.entity.*;
import com.ford.bookbuddies.service.BookService;
import com.ford.bookbuddies.service.CartService;
import com.ford.bookbuddies.service.CustomerService;
import com.ford.bookbuddies.service.DeleteService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.ford.bookbuddies.entity.BookCategory.FICTION;

@SpringBootTest
public class CartServiceTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookStockRepository bookStockRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DeleteService deleteService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BookDetailRepository bookDetailRepository;
    @Autowired
    private BookService bookService;

    @Test
    @Transactional
    void buyBooksInCartNullTestForCart(){
        Customer customer=new Customer("nithya","nithya@gmail.com","nithya","nithya");
        List<Integer> booksToBuyList=new ArrayList<>();
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        booksToBuyList.add(book.getBookId());
        Book book2=new Book("dumbledore","jk",100.0,FICTION);
        this.bookRepository.save(book2);
        booksToBuyList.add(book2.getBookId());
        Integer cartId=0;
        try{
            customer=this.customerService.createCustomerAccount(customer);
            cartId=customer.getCart().getId();
            customer.setCart(null);
            this.customerRepository.save(customer);
            this.cartService.buyBooksinCart(customer.getId(),booksToBuyList);
        }
        catch(Exception e){
            this.customerRepository.deleteById(customer.getId());
            this.cartRepository.deleteById(cartId);
            this.bookDetailRepository.deleteByBook(book);
            this.bookRepository.deleteById(book.getBookId());
            this.bookDetailRepository.deleteByBook(book2);

            this.bookRepository.deleteById(book2.getBookId());
            Assertions.assertEquals("Customer Cart is Null!", e.getMessage());
        }
    }

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
        this.bookStockRepository.save(new BookStock(book2,24));
        booksToBuyList.add(book2.getBookId());
        try{
            customer=this.customerService.createCustomerAccount(customer);
            this.bookService.addProductToCart(customer.getId(),book.getBookTitle(),2);
            this.bookService.addProductToCart(customer.getId(),book2.getBookTitle(),1);
            List<BookDetail> list=this.cartService.buyBooksinCart(customer.getId(),booksToBuyList);
            this.customerRepository.deleteById(customer.getId());
            this.cartRepository.deleteById(customer.getCart().getId());
            this.bookDetailRepository.deleteByBook(book);
            this.bookStockRepository.deleteBookStockByBook(book);
            this.bookRepository.deleteById(book.getBookId());
            this.bookDetailRepository.deleteByBook(book2);
            this.bookStockRepository.deleteBookStockByBook(book2);
            this.bookRepository.deleteById(book2.getBookId());
            Assertions.assertEquals(2, list.size());
        }
        catch(Exception e){
            Assertions.fail(e.getMessage());
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
            Cart cart=this.cartService.increaseQuantity(customer.getId(),book.getBookId());
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
@Transactional
void increaseQuantityInCartTestUser() {
    Book book = new Book("harry", "potter", 200.0, FICTION);
    this.bookRepository.save(book);
    try {
        this.cartService.increaseQuantity(1001,book.getBookId());
    }
    catch(Exception e){
        this.bookRepository.deleteById(book.getBookId());
        Assertions.assertEquals("User is not registered", e.getMessage());
    }
}
    @Test
    @Transactional
    void increaseQuantityInCartTestBookExists() {
        Customer customer = new Customer("nithya", "nithya@gmail.com", "nithya", "nithya");
        try {
            customer = this.customerService.createCustomerAccount(customer);
            this.cartService.increaseQuantity(customer.getId(),10001);
        }
        catch(Exception e){
            this.customerRepository.deleteById(customer.getId());
            this.cartRepository.deleteById(customer.getCart().getId());
            Assertions.assertEquals("Book doesn't exists", e.getMessage());
        }
    }

    @Test
    @Transactional
    void increaseQuantityInCartTestBookInCart() {
        Customer customer = new Customer("nithya", "nithya@gmail.com", "nithya", "nithya");
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        try {
            customer = this.customerService.createCustomerAccount(customer);
            this.cartService.increaseQuantity(customer.getId(),book.getBookId());
        }
        catch(Exception e){
            this.customerRepository.deleteById(customer.getId());
            this.cartRepository.deleteById(customer.getCart().getId());
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertEquals("Book not found in Cart!", e.getMessage());
        }
    }
    @Test
    @Transactional
    void increaseQuantityInCartTestQuantity() {
        Customer customer = new Customer("nithya", "nithya@gmail.com", "nithya", "nithya");
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        this.bookStockRepository.save(new BookStock(book,5));
        try {
            customer = this.customerService.createCustomerAccount(customer);
            this.bookService.addProductToCart(customer.getId(), book.getBookTitle(), 6);
            this.cartService.increaseQuantity(customer.getId(),book.getBookId());
        }
        catch(Exception e){
            this.customerRepository.deleteById(customer.getId());
            this.cartRepository.deleteById(customer.getCart().getId());
            this.bookDetailRepository.deleteByBook(book);
            this.bookStockRepository.deleteBookStockByBook(book);
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertEquals("Available Stock quantity is lesser than quantity needed", e.getMessage());
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
            Cart cart=this.cartService.decreaseQuantity(customer.getId(),book.getBookId());
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
    @Transactional
    void decreaseQuantityInCartTestUser() {
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        try {
            this.cartService.decreaseQuantity(1001,book.getBookId());
        }
        catch(Exception e){
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertEquals("User is not registered", e.getMessage());
        }
    }
    @Test
    @Transactional
    void decreaseQuantityInCartTestBookExists() {
        Customer customer = new Customer("nithya", "nithya@gmail.com", "nithya", "nithya");
        try {
            customer = this.customerService.createCustomerAccount(customer);
            this.cartService.decreaseQuantity(customer.getId(),10001);
        }
        catch(Exception e){
            this.customerRepository.deleteById(customer.getId());
            this.cartRepository.deleteById(customer.getCart().getId());
            Assertions.assertEquals("Book doesn't exists", e.getMessage());
        }
    }
    @Test
    @Transactional
    void decreaseQuantityInCartTestBookInCart() {
        Customer customer = new Customer("nithya", "nithya@gmail.com", "nithya", "nithya");
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        try {
            customer = this.customerService.createCustomerAccount(customer);
            this.cartService.decreaseQuantity(customer.getId(),book.getBookId());
        }
        catch(Exception e){
            this.customerRepository.deleteById(customer.getId());
            this.cartRepository.deleteById(customer.getCart().getId());
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertEquals("Book not found in Cart!", e.getMessage());
        }

    }



}

