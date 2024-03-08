package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.dao.SubscriptionRepository;
import com.ford.bookbuddies.dto.TransactionDetails;
import com.ford.bookbuddies.entity.Payment;
import com.ford.bookbuddies.exception.OrderException;
import com.ford.bookbuddies.exception.PaymentException;
import com.ford.bookbuddies.exception.SubscriptionException;
import com.ford.bookbuddies.service.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private SubscriptionRepository subscriptionRepository;



    @PostMapping("/payment/buy")
    public Payment getDetails(@RequestBody Payment payment) throws Exception
    {
        if(payment.getTotalCost()== null || payment.getAddress()==null||payment.getPaymentMode()==null)
             throw new PaymentException("Payment attributes should not be null");

        return paymentService.makePayment(payment);
//
    }
    @GetMapping("/payment/subscribe/{subscriptionId}")
    public TransactionDetails createTransaction(@PathVariable("subscriptionId") Integer subscriptionId) throws PaymentException, SubscriptionException, RazorpayException {
        if(subscriptionId==null)
            throw new PaymentException("SubscriptionId Should not be null");

        if(!this.subscriptionRepository.existsById(subscriptionId))
            throw new PaymentException("Subscription does not exist with Id:"+subscriptionId);


        return this.paymentService.createTransaction(subscriptionId);
    }
}
