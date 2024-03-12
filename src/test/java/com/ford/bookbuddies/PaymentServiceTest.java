package com.ford.bookbuddies;

import com.ford.bookbuddies.dao.*;
import com.ford.bookbuddies.dto.ConfirmedBooksDto;
import com.ford.bookbuddies.entity.*;
import com.ford.bookbuddies.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ford.bookbuddies.entity.BookCategory.FICTION;

@SpringBootTest
public class PaymentServiceTest {
    @Autowired
    public PaymentService paymentService;
    @Autowired
    public BookRepository bookRepository;
    @Autowired
    public BookStockRepository bookStockRepository;
    @Autowired
    public CustomerService customerService;
    @Autowired
    public BookService bookService;
    @Autowired
    public CartService cartService;
    @Autowired
    public CustomerRepository customerRepository;
    @Autowired
    public CartRepository cartRepository;
    @Autowired
    public BookDetailRepository bookDetailRepository;
    @Autowired
    public SubscriptionRepository subscriptionRepository;



    @Test
    public void userEnteredTotalCostNotEqualToActualOrderCostShouldThrowExceptionTest(){
        Customer customer=new Customer("suba","suba@gmail.com","suba","suba@123");
        List<Integer> booksToBuyList=new ArrayList<>();
        Book book = new Book("harry", "potter", 200.0, FICTION);
        this.bookRepository.save(book);
        this.bookStockRepository.save(new BookStock(book,25));
        booksToBuyList.add(book.getBookId());
        Book book2=new Book("dumbledore","jk",100.0,FICTION);
        this.bookRepository.save(book2);
        this.bookStockRepository.save(new BookStock(book2,24));
        booksToBuyList.add(book2.getBookId());

        try {
            customer = this.customerService.createCustomerAccount(customer);
            this.bookService.addProductToCart(customer.getId(),book.getBookTitle(),2);
            this.bookService.addProductToCart(customer.getId(),book2.getBookTitle(),1);
            this.cartService.buyBooksinCart(customer.getId(),booksToBuyList);
            Payment payment = new Payment(100.0,"193,Jainagar,Tiruppur",PaymentMode.CREDIT_CARD);
            this.paymentService.makePayment(payment);
        }catch(Exception e)
        {
            Assertions.assertEquals("Entered payment not matches with the actual order payment",e.getMessage());
        }
    }

    @Test
    public void denialOfTransactionShouldThrowExceptionTest(){
        Book book = new Book("Wings of the fire", "AbdulKalam", 1000.0, BookCategory.SCIFI);
        this.bookRepository.save(book);
        Customer customer = new Customer("suba","suba@gmail.com","suba","suba@123");
        this.customerRepository.save(customer);
        LocalDate subscriptionDate = LocalDate.now();
        LocalDate expireDate = subscriptionDate.plusDays(SubscriptionPlan.DAILY.getDuration());
        Optional<Customer> customerOpt=this.customerRepository.findByUserName("suba");
        Optional<Book> bookOpt=this.bookRepository.findByBookTitle("Wings of the fire");
        Customer customer1=customerOpt.get();
        Book book1=bookOpt.get();
        Subscription subscription=new Subscription();
        subscription.setBook(book1);
        subscription.setCustomer(customer1);
        subscription.setSubscriptionDate(subscriptionDate);
        subscription.setExpireDate(expireDate);
        subscription.setPaymentPlan(SubscriptionPlan.MONTHLY);
        subscription.setSubscriptionCost(SubscriptionPlan.MONTHLY.getCost());
        subscription.setSubscriptionStatus("PAYMENT PENDING");
        this.subscriptionRepository.save(subscription);
        try
        {

            this.paymentService.createTransaction(subscription.getSubscriptionId());

        }catch(Exception e)
        {
            Assertions.assertEquals("Transaction denied",e.getMessage());
        }
    }


}
