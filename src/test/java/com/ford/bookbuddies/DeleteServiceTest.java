package com.ford.bookbuddies;

import com.ford.bookbuddies.dao.*;
import com.ford.bookbuddies.entity.Book;
import com.ford.bookbuddies.entity.BookStock;
import com.ford.bookbuddies.entity.Cart;
import com.ford.bookbuddies.entity.Customer;
import com.ford.bookbuddies.service.BookService;
import com.ford.bookbuddies.service.CustomerService;
import com.ford.bookbuddies.service.DeleteService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ford.bookbuddies.entity.BookCategory.FICTION;

@SpringBootTest
public class DeleteServiceTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DeleteService deleteService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BookDetailRepository bookDetailRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookStockRepository bookStockRepository;
    @Test
    @Transactional
    void deleteProductFromCartTest() {
        Book book = new Book("POK", "potter", 250.0, FICTION);
        this.bookRepository.save(book);
        this.bookStockRepository.save(new BookStock(book,12));
        Customer customer = new Customer("nithya", "nithya@gmail.com", "nithya", "nithya");
        try {
            customer = this.customerService.createCustomerAccount(customer);
            this.bookService.addProductToCart(customer.getId(), book.getBookTitle(), 3);
            Cart deletedCart = this.deleteService.deleteProductFromCart(customer.getId(), book.getBookId());
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
    @Test
    void deleteProductFromCartRegisteredUser() throws Exception {
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        try{
            this.deleteService.deleteProductFromCart(1001, book.getBookId());
        }
        catch(Exception e){
            this.bookRepository.deleteById(book.getBookId());
            Assertions.assertEquals("User is not registered",e.getMessage());
        }
    }
    @Test
    void deleteProductFromCartTestBook() throws Exception {
        Customer customer=new Customer("nithya","nithya@gmail.com","nithya","nithya");
        try{
            customer=this.customerService.createCustomerAccount(customer);
            this.deleteService.deleteProductFromCart(customer.getId(), 10001);
        }
        catch(Exception e){
            this.customerRepository.deleteById(customer.getId());
            Assertions.assertEquals("Book doesn't exists",e.getMessage());
        }
    }
}
