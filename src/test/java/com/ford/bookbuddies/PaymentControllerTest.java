package com.ford.bookbuddies;

import com.ford.bookbuddies.controller.PaymentController;
import com.ford.bookbuddies.dao.BookRepository;
import com.ford.bookbuddies.dao.CustomerRepository;
import com.ford.bookbuddies.dao.SubscriptionRepository;
import com.ford.bookbuddies.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
public class PaymentControllerTest {
    @Autowired
    public PaymentController paymentController;

    @Autowired
    public CustomerRepository customerRepository;

    @Autowired
    public BookRepository bookRepository;

    @Autowired
    public SubscriptionRepository subscriptionRepository;

    @Test
    public void paymentAttributesIsNullShouldThrowExceptionTest() {
        Payment payment = new Payment(null, null, null);
        try {
            this.paymentController.getDetails(payment);
        } catch (Exception e) {
            Assertions.assertEquals("Payment attributes should not be null", e.getMessage());
        }
    }
    @Test
    public void paymentNegativeOrZeroShouldThrowExceptionTest() {
        Payment payment = new Payment(-0.0,"193,Jainagar,Tirupur",PaymentMode.CREDIT_CARD);
        try
        {
            this.paymentController.getDetails(payment);
        }catch(Exception e){
            Assertions.assertEquals("Total cost should not be negative or 0",e.getMessage());
        }
    }
    @Test
    public void subscriptionIdIsNullShouldThrowExceptionTest() {
        try {
            this.paymentController.createTransaction(null);
        } catch (Exception e) {
            Assertions.assertEquals("SubscriptionId Should not be null", e.getMessage());
        }
    }

    @Test
    public void subscriptionIdNotValidShouldThrowExceptionTest() {
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
        Integer id=2;
        try
        {
            this.paymentController.createTransaction(id);
        }
        catch (Exception e)
        {
            Assertions.assertEquals("Subscription does not exist with Id:"+id, e.getMessage());
        }

    }
}