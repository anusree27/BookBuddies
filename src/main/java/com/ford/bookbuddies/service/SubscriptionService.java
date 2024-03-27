package com.ford.bookbuddies.service;

import com.ford.bookbuddies.entity.Subscription;
import com.ford.bookbuddies.entity.SubscriptionPlan;
import com.ford.bookbuddies.exception.SubscriptionException;

import java.util.List;

public interface SubscriptionService {
    public Subscription subscribeToBook(Integer bookId, Integer userId, SubscriptionPlan plan) throws SubscriptionException;

    public String cancelExpiredSubscriptions() throws SubscriptionException;

    public List<Subscription> findSubscriptionsByUserId(Integer userId) throws SubscriptionException;

    public String extendSubscription(Integer subscriptionId, SubscriptionPlan plan) throws SubscriptionException;

    public List<Subscription> getAllSubscriptions() throws SubscriptionException;

    public List<Subscription> findSubscriptionsByBookName(String bookName) throws SubscriptionException;

    public String renewSubscription(Integer subscriptionId, SubscriptionPlan plan) throws SubscriptionException;

}

