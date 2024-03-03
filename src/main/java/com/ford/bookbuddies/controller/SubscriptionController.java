package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.entity.Book;
import com.ford.bookbuddies.entity.Subscription;
import com.ford.bookbuddies.entity.SubscriptionPlan;
import com.ford.bookbuddies.exception.SubscriptionException;
import com.ford.bookbuddies.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/subscribe")
    public Book subscribeToBook(@RequestParam String bookName, @RequestParam String userName, @RequestParam SubscriptionPlan plan) throws SubscriptionException{
        return this.subscriptionService.subscribeToBook(bookName,userName,plan);
    }

    @GetMapping("/user/{username}/subscriptions")
    public List<Subscription> getSubscriptionsByUsername(@PathVariable String username)
    {
        return this.subscriptionService.findSubscriptionsByUsername(username);
    }
    @GetMapping("/book/{bookName}/subscriptions")
    public List<Subscription> getSubscriptionsByBookName(@PathVariable String bookName)
    {
        return this.subscriptionService.findSubscriptionsByBookName(bookName);
    }
    @PostMapping("/{subscriptionId}/extend")
    public void extendSubscription(@PathVariable Integer subscriptionId,@RequestParam SubscriptionPlan plan) throws SubscriptionException
    {
        this.subscriptionService.extendSubscription(subscriptionId,plan);
    }
    @PatchMapping("/cancelExpired")
    public void cancelExpiredSubscriptions()
    {
        this.subscriptionService.cancelExpiredSubscriptions();
    }
    @GetMapping("/subscriptions")
    public List<Subscription> getAllSubscriptions()
    {
        return this.subscriptionService.getAllSubscriptions();
    }
    @PatchMapping("/cancelSubscription/{subscriptionId}")
    public void cancelSubscription(@PathVariable Integer subscriptionId) throws SubscriptionException
    {
        this.subscriptionService.cancelSubscriptions(subscriptionId);
    }
    @PostMapping("/{subscriptionId}/renew")
    public void renewSubscription(@PathVariable Integer subscriptionId,@RequestParam SubscriptionPlan plan) throws SubscriptionException
    {
        this.subscriptionService.renewSubscription(subscriptionId,plan);
    }

}
