package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.entity.Payment;
import com.ford.bookbuddies.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;



    @PostMapping("/payment/buy")
    public Payment getDetails(@RequestBody Payment payment) throws Exception
    {

//        try {
            return paymentService.makePayment(payment);
//        } catch (PaymentException e) {
//            throw new Exception(e.getMessage());
//        }
    }
}
