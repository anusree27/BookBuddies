package com.ford.bookbuddies.service;

import com.ford.bookbuddies.dto.ConfirmedBooksDto;
import com.ford.bookbuddies.dto.TransactionDetails;
import com.ford.bookbuddies.entity.Payment;
import com.ford.bookbuddies.exception.PaymentException;
import com.ford.bookbuddies.exception.SubscriptionException;
import com.razorpay.RazorpayException;

public interface PaymentService {

    Payment makePayment(Payment payment) throws Exception;

    void orderDetails(ConfirmedBooksDto confirmedBooksDto);
    public TransactionDetails createTransaction(Integer subscriptionId) throws PaymentException, SubscriptionException, RazorpayException;
;



}
