package com.ford.bookbuddies.service;

import com.ford.bookbuddies.dao.*;
import com.ford.bookbuddies.dto.TransactionDetails;
import com.ford.bookbuddies.entity.*;
import com.ford.bookbuddies.exception.CustomerException;
import com.ford.bookbuddies.exception.PaymentException;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
//    static ConfirmedBooksDto confirmedBooksDto;

    @Autowired
    private BookOrderRepository bookOrderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DeleteService deleteService;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private BookStockRepository bookStockRepository;
    @Autowired
    private ConfirmedOrdersRepository confirmedOrdersRepository;

    private static final String KEY = "rzp_test_VN5rZsFARLYSdj";
    private static final String KEY_SECRET = "KmQo43wlMJfTklxoUCowDgmf";
    private static final String CURRENCY = "INR";


    public TransactionDetails createTransaction(Payment payment) throws Exception {


        Optional<List<ConfirmedOrders>> confirmedOrdersList=this.confirmedOrdersRepository.findAllByUserId(payment.getUserId());
        ConfirmedOrders confirmedOrders = null;
        for (ConfirmedOrders co :  confirmedOrdersList.get()) {
            if (co.getPaid() == false) {
                confirmedOrders = co;
            }
        }
        if(confirmedOrders.getOrderedBooks()==null)
        {
            throw new PaymentException("Books to Order list should not be null");
        }
        if(confirmedOrders.getUserId() == null) {
            throw new CustomerException("User id cannot be null");
        }
        if (confirmedOrders.getUserId() != payment.getUserId()) {
            throw new PaymentException("User does not match");
        }

        System.out.println(confirmedOrders.getOrderedBooks());



        Integer totalQuantity=0;
        for(BookDetail books: confirmedOrders.getOrderedBooks())
        {
            totalQuantity+=books.getQuantity();
        }

        if(payment.getTotalCost() == null)
        {
            this.confirmedOrdersRepository.delete(confirmedOrders);
            throw new PaymentException("Entered payment not matches with the actual order payment");
        }

        else
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount", (payment.getTotalCost() * 100) );
            jsonObject.put("currency", CURRENCY);

            RazorpayClient razorpayClient = new RazorpayClient(KEY, KEY_SECRET);

            Order order = razorpayClient.orders.create(jsonObject);

            TransactionDetails transactionDetails =  prepareTransactionDetails(order);
            payment.setPaymentStatus(true);
            BookOrders bookOrders=new BookOrders();
            bookOrders.setPayment(payment);
            bookOrders.setOrderStatus(OrderStatus.CONFIRMED);
            bookOrders.setOrderDate(LocalDate.now());
            bookOrders.setConfirmedOrders(confirmedOrders);
            bookOrders.setTotalBookCount(totalQuantity);
            confirmedOrders.setPaid(true);
            this.confirmedOrdersRepository.save(confirmedOrders);
            bookOrders.setConfirmedOrders(confirmedOrders);
            this.paymentRepository.save(payment);
            this.bookOrderRepository.save(bookOrders);
            Optional<Customer> users=this.customerRepository.findById(confirmedOrders.getUserId());
            users.get().getOrderList().add(bookOrders);
            for(BookDetail books: confirmedOrders.getOrderedBooks()) {
                Book book = books.getBook();
                this.deleteService.deleteProductFromCart(users.get().getId(), book.getBookId());
                Optional<BookStock> bookStock = this.bookStockRepository.findBookStockByBook(book);
                bookStock.get().setStockQuantity(bookStock.get().getStockQuantity() - books.getQuantity());
                this.bookStockRepository.save(bookStock.get());
            }
            this.customerRepository.save(users.get());
            return transactionDetails;
        }

    }




    public TransactionDetails prepareTransactionDetails(Order order) {
        String orderId = order.get("id");
        String currency = order.get("currency");
        Integer amount = order.get("amount");

        TransactionDetails transactionDetails = new TransactionDetails(orderId, currency, amount, KEY);
        return transactionDetails;
    }


    /*public TransactionDetails createTransaction(Integer subscriptionId) throws PaymentException, SubscriptionException, RazorpayException, com.razorpay.RazorpayException {

        Optional<Subscription> findSubscription=this.subscriptionRepository.findById(subscriptionId);
       if(findSubscription.isPresent()){
           JSONObject jsonObject=new JSONObject();
           jsonObject.put("amount", findSubscription.get().getSubscriptionCost());
           jsonObject.put("currency",CURRENCY);

           RazorpayClient razorpayClient=new RazorpayClient(KEY,KEY_SECRET);
           com.razorpay.Order  razorpayOrder =razorpayClient.orders.create(jsonObject);
           String key=env.getProperty("razorpay.api.key");
           String transactionId=razorpayOrder.get("id");
           String currency=razorpayOrder.get(CURRENCY);
           Integer amount=razorpayOrder.get("amount");
            if(transactionId != null)
            {
                findSubscription.get().setSubscriptionStatus("ACTIVE");
           }
            else
           {
                throw new PaymentException("Transaction denied");
            }
            this.subscriptionRepository.save(findSubscription.get());


           return new TransactionDetails(transactionId,currency,amount.doubleValue(),key);


        }else throw new SubscriptionException("No Subscription Exist with Id:"+subscriptionId);
       return null;
}*/

    public TransactionDetails createTransactionForSubscription(Integer subscriptionId){
        try {
            Optional<Subscription> findSubscription=this.subscriptionRepository.findById(subscriptionId);
            Double cost=findSubscription.get().getSubscriptionCost();
            if(findSubscription.isPresent()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("amount", cost * 100);
                jsonObject.put("currency", CURRENCY);
                RazorpayClient razorpayClient = new RazorpayClient(KEY, KEY_SECRET);
                Order order = razorpayClient.orders.create(jsonObject);
                System.out.println("suba" + order);
                findSubscription.get().setSubscriptionStatus("ACTIVE");
                System.out.println(findSubscription.get().getSubscriptionStatus());
                this.subscriptionRepository.save(findSubscription.get());
                return prepareTransactionDetails(order);

            }
        } catch (com.razorpay.RazorpayException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


}
