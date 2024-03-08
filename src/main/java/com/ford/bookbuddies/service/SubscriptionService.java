package com.ford.bookbuddies.service;
import com.ford.bookbuddies.entity.Subscription;
import com.ford.bookbuddies.entity.SubscriptionPlan;
import com.ford.bookbuddies.exception.SubscriptionException;

import java.util.List;

public interface SubscriptionService {
    public Subscription subscribeToBook(String bookName, String userName, SubscriptionPlan plan) throws SubscriptionException;
    public String cancelExpiredSubscriptions() throws SubscriptionException;
    public List<Subscription> findSubscriptionsByUsername(String username) throws SubscriptionException;
    public String extendSubscription(Integer subscriptionId, SubscriptionPlan plan) throws SubscriptionException;
    public List<Subscription> getAllSubscriptions() throws SubscriptionException;
    public Double cancelSubscriptions(Integer subscriptionId) throws SubscriptionException;
    public List<Subscription> findSubscriptionsByBookName(String bookName) throws SubscriptionException;
    public String renewSubscription(Integer subscriptionId,SubscriptionPlan plan) throws SubscriptionException;



}