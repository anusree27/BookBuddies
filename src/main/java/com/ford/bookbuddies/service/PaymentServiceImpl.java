package com.ford.bookbuddies.service;

import com.ford.bookbuddies.dao.*;
import com.ford.bookbuddies.dto.ConfirmedBooksDto;
import com.ford.bookbuddies.dto.TransactionDetails;
import com.ford.bookbuddies.entity.*;
import com.ford.bookbuddies.exception.PaymentException;
import com.ford.bookbuddies.exception.SubscriptionException;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    static ConfirmedBooksDto confirmedBooksDto;
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
    private final Environment env;
    private static final String CURRENCY="currency";

    public PaymentServiceImpl(Environment env) {
        this.env = env;
    }

    public Payment makePayment(Payment payment) throws Exception {
        if(confirmedBooksDto.getOrderedBooks()==null)
        {
            throw new PaymentException("Books to Order list should not be null");
        }

        double price=0.0;
        Integer totalQuantity=0;
        for(BookDetail books: confirmedBooksDto.getOrderedBooks())
        {
            price += books.getBook().getPrice()*books.getQuantity();
            totalQuantity+=books.getQuantity();
        }

        Double comparePrice=payment.getTotalCost();
        if(comparePrice != price)
        {
            throw new PaymentException("Entered payment not matches with the actual order payment");
        }

        else
        {
            payment.setPaymentStatus(true);
            BookOrders bookOrders=new BookOrders();
            bookOrders.setPayment(payment);
            bookOrders.setOrderStatus(OrderStatus.CONFIRMED);
            bookOrders.setBookList(confirmedBooksDto.getOrderedBooks());
            bookOrders.setTotalBookCount(totalQuantity);
            this.paymentRepository.save(payment);
            this.bookOrderRepository.save(bookOrders);
            Optional<Customer> users=this.customerRepository.findById(confirmedBooksDto.getUserId());
            users.get().getOrderList().add(bookOrders);
            for(BookDetail books: confirmedBooksDto.getOrderedBooks())
            {
                Book book=books.getBook();
                this.deleteService.deleteProductFromCart(users.get().getId(),book.getBookId());
                Optional<BookStock> bookStock=this.bookStockRepository.findBookStockByBook(book);
                bookStock.get().setStockQuantity(bookStock.get().getStockQuantity()-books.getQuantity());
                this.bookStockRepository.save(bookStock.get());
            }

            this.customerRepository.save(users.get());
        }

        return payment;
    }
    @Override
    public void orderDetails(ConfirmedBooksDto confirmedBookDto) {
        confirmedBooksDto=confirmedBookDto;

    }

    public TransactionDetails createTransaction(Integer subscriptionId) throws PaymentException, SubscriptionException, RazorpayException {

        Optional<Subscription> findSubscription=this.subscriptionRepository.findById(subscriptionId);
        if(findSubscription.isPresent()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("amount", findSubscription.get().getSubscriptionCost());
            jsonObject.put(CURRENCY,env.getProperty(CURRENCY));

            RazorpayClient razorpayClient=new RazorpayClient(env.getProperty("razorpay.api.key"), env.getProperty("razorpay.api.secret"));
            com.razorpay.Order  razorpayOrder =razorpayClient.orders.create(jsonObject);
            String key=env.getProperty("razorpay.api.key");
            String transactionId=razorpayOrder.get("id");
            String currency=razorpayOrder.get(CURRENCY);
            Integer amount=razorpayOrder.get("amount");
            if(transactionId != null)
            {
                findSubscription.get().setSubscriptionStatus("Subscribed Successfully");
            }
            else
            {
                throw new PaymentException("Transaction denied");
            }
            this.subscriptionRepository.save(findSubscription.get());


            return new TransactionDetails(transactionId,currency,amount.doubleValue(),key);


        }else throw new SubscriptionException("No Subscription Exist with Id:"+subscriptionId);
    }

}
