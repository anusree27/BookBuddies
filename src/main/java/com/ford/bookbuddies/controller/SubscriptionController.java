package com.ford.bookbuddies.controller;
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

    @PostMapping("subscription/subscribe")
    public Subscription subscribeToBook(@RequestParam String bookName, @RequestParam String userName, @RequestParam SubscriptionPlan plan) throws SubscriptionException{
        return this.subscriptionService.subscribeToBook(bookName,userName,plan);
    }

    @GetMapping("subscription/user/{username}/subscriptions")
    public List<Subscription> getSubscriptionsByUsername(@PathVariable String username) throws SubscriptionException
    {
        return this.subscriptionService.findSubscriptionsByUsername(username);
    }
    @GetMapping("subscription/book/{bookName}/subscriptions")
    public List<Subscription> getSubscriptionsByBookName(@PathVariable String bookName) throws SubscriptionException
    {
        return this.subscriptionService.findSubscriptionsByBookName(bookName);
    }
    @PostMapping("subscription/{subscriptionId}/extend")
    public void extendSubscription(@PathVariable Integer subscriptionId,@RequestParam SubscriptionPlan plan) throws SubscriptionException
    {
        this.subscriptionService.extendSubscription(subscriptionId,plan);
    }
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
    @PatchMapping("subscription/cancelSubscription/{subscriptionId}")
    public void cancelSubscription(@PathVariable Integer subscriptionId) throws SubscriptionException
    {
        this.subscriptionService.cancelSubscriptions(subscriptionId);
    }
    @PostMapping("subscription/{subscriptionId}/renew")
    public void renewSubscription(@PathVariable Integer subscriptionId,@RequestParam SubscriptionPlan plan) throws SubscriptionException
    {
        this.subscriptionService.renewSubscription(subscriptionId,plan);
    }


}
