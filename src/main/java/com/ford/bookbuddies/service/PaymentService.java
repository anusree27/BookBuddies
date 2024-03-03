package com.ford.bookbuddies.service;

import com.ford.bookbuddies.dto.ConfirmedBooksDto;
import com.ford.bookbuddies.entity.Payment;

public interface PaymentService {

    Payment makePayment(Payment payment) throws Exception;

    void orderDetails(ConfirmedBooksDto confirmedBooksDto);


}
