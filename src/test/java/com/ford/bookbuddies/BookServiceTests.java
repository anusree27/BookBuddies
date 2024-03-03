package com.ford.bookbuddies;

import com.ford.bookbuddies.dao.BookDetailRepository;
import com.ford.bookbuddies.dao.BookRepository;
import com.ford.bookbuddies.entity.*;
import com.ford.bookbuddies.exception.CustomerException;
import com.ford.bookbuddies.service.BookService;
import com.ford.bookbuddies.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ford.bookbuddies.entity.BookCategory.FICTION;

@SpringBootTest
public class BookServiceTests {

    @Autowired
    private BookService bookService;
    @Autowired
    private CustomerService customerService;

    @Autowired
    public BookRepository bookRepository;

    @Autowired
    public BookDetailRepository bookDetailRepository;

    @Test
    void addProductToCartTest() throws Exception {
        Customer customer=new Customer("nith","nith@gmail.com","nith","nith");
//        Book book = new Book("harry", "potter", 200.0, FICTION);
//        BookDetail bookDetail = new BookDetail(2, book);
//        this.bookRepository.save(book);
//        this.bookDetailRepository.save(bookDetail);
        try{
            customer=this.customerService.createCustomerAccount(customer);
            Cart cart=this.bookService.addProductToCart(customer.getId(), "harry",3);
            Assertions.assertFalse(cart.getBooksDetails().isEmpty());
        }
        catch(Exception e){
            Assertions.fail(e.getMessage());
        }
    }
}

