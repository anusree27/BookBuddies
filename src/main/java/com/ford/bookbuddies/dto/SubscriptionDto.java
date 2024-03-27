package com.ford.bookbuddies.dto;

import com.ford.bookbuddies.entity.SubscriptionPlan;

public class SubscriptionDto {
    private Integer bookId;
    private Integer userId;
    private SubscriptionPlan subscriptionPlan;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }
}