package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.dao.SubscriptionRepository;
import com.ford.bookbuddies.dto.TransactionDetails;
import com.ford.bookbuddies.entity.Payment;
import com.ford.bookbuddies.exception.PaymentException;
import com.ford.bookbuddies.exception.SubscriptionException;
import com.ford.bookbuddies.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private SubscriptionRepository subscriptionRepository;



    @PostMapping("/payment/buy")
    public TransactionDetails createTransaction(@RequestBody Payment payment) throws Exception
    {
        if(payment == null || payment.getTotalCost()== null || payment.getAddress()==null||payment.getMobileNo()==null)
            throw new PaymentException("Payment attributes should not be null");
        else if(payment.getTotalCost()<=0)
            throw new PaymentException("Total cost should not be negative or 0");
        else
            return paymentService.createTransaction(payment);
//
    }

//    @GetMapping({"/createTransaction/{amount}"})
//    public TransactionDetails createTransaction(@PathVariable(name = "amount") Double amount) {
//        return paymentService.createTransaction(amount);
//    }

//    @GetMapping("/payment/subscribe/{subscriptionId}")
//    public TransactionDetails createTransaction(@PathVariable("subscriptionId") Integer subscriptionId) throws PaymentException, SubscriptionException, RazorpayException {
//        if(subscriptionId==null)
//            throw new PaymentException("SubscriptionId Should not be null");
//
//        else if(!this.subscriptionRepository.existsById(subscriptionId))
//            throw new PaymentException("Subscription does not exist with Id:"+subscriptionId);
//
//        else
//            return this.paymentService.createTransaction(subscriptionId);
//    }
    @PostMapping("/payment/subscribe")
    public TransactionDetails createTransactionForSubscription(@RequestBody Integer subscriptionId) throws PaymentException, SubscriptionException{
    if(subscriptionId==null)
        throw new PaymentException("SubscriptionId Should not be null");

    else if(!this.subscriptionRepository.existsById(subscriptionId))
        throw new PaymentException("Subscription does not exist with Id:"+subscriptionId);

    else
        return this.paymentService.createTransactionForSubscription(subscriptionId);
}


}
