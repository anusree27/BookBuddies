package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.dto.SubscriptionDto;
import com.ford.bookbuddies.dto.UpdateSubscriptionDto;
import com.ford.bookbuddies.entity.Subscription;
import com.ford.bookbuddies.exception.SubscriptionException;
import com.ford.bookbuddies.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
@RestController
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("subscription/subscribe")
    public Subscription subscribeToBook(@RequestBody SubscriptionDto subscriptionDto) throws SubscriptionException{
        return this.subscriptionService.subscribeToBook(subscriptionDto.getBookId(),subscriptionDto.getUserId(),subscriptionDto.getSubscriptionPlan());
    }

    @GetMapping("subscription/subscriptions/user/{userId}")
    public List<Subscription> getSubscriptionsByUserId(@PathVariable Integer userId) throws SubscriptionException
    {
        return this.subscriptionService.findSubscriptionsByUserId(userId);
    }
    @GetMapping("subscription/subscriptions/book/{bookName}")
    public List<Subscription> getSubscriptionsByBookName(@PathVariable String bookName) throws SubscriptionException
    {
        return this.subscriptionService.findSubscriptionsByBookName(bookName);
    }
    @PatchMapping("subscription/extend")
    public void extendSubscription(@RequestBody UpdateSubscriptionDto updateSubscriptionDto) throws SubscriptionException
    {
        this.subscriptionService.extendSubscription(updateSubscriptionDto.getSubscriptionId(),updateSubscriptionDto.getSubscriptionPlan());
    }
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    @PatchMapping("subscription/cancelExpired")
    public void cancelExpiredSubscriptions() throws SubscriptionException
    {
        this.subscriptionService.cancelExpiredSubscriptions();
    }
    @GetMapping("subscription/subscriptions")
    public List<Subscription> getAllSubscriptions() throws SubscriptionException
    {
        return this.subscriptionService.getAllSubscriptions();
    }

    @PatchMapping("subscription/renew")
    public void renewSubscription(@RequestBody UpdateSubscriptionDto updateSubscriptionDto) throws SubscriptionException
    {
        this.subscriptionService.renewSubscription(updateSubscriptionDto.getSubscriptionId(),updateSubscriptionDto.getSubscriptionPlan());
    }
}

