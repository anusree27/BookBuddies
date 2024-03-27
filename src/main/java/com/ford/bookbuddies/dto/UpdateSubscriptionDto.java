package com.ford.bookbuddies.dto;

import com.ford.bookbuddies.entity.SubscriptionPlan;

public class UpdateSubscriptionDto {
    private Integer subscriptionId;
    private SubscriptionPlan subscriptionPlan;

    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;

    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }
}