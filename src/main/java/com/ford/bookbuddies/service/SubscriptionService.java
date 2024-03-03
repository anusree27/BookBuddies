package com.ford.bookbuddies.service;

import com.ford.bookbuddies.entity.Book;
import com.ford.bookbuddies.entity.Subscription;
import com.ford.bookbuddies.entity.SubscriptionPlan;
import com.ford.bookbuddies.exception.SubscriptionException;

import java.util.List;

public interface SubscriptionService {
    public Book subscribeToBook(String bookName, String userName, SubscriptionPlan plan) throws SubscriptionException;
    public String cancelExpiredSubscriptions();
    public List<Subscription> findSubscriptionsByUsername(String username);
    public String extendSubscription(Integer subscriptionId, SubscriptionPlan plan) throws SubscriptionException;
    public List<Subscription> getAllSubscriptions();
    public Double cancelSubscriptions(Integer subscriptionId) throws SubscriptionException;
    public List<Subscription> findSubscriptionsByBookName(String bookName);
    public String renewSubscription(Integer subscriptionId,SubscriptionPlan plan) throws SubscriptionException;

}
