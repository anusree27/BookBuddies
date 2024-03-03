package com.ford.bookbuddies.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Subscription {
    @Id
    @GeneratedValue
    private Integer subscriptionId;
    private LocalDate subscriptionDate;
    private LocalDate expireDate;
    private Double subscriptionCost;
    private String subscriptionStatus;
    @Enumerated
    private SubscriptionPlan paymentPlan;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Book book;

    public Subscription() {
    }

    public Subscription(LocalDate subscriptionDate, LocalDate expireDate, Double subscriptionCost, String subscriptionStatus, SubscriptionPlan paymentPlan, Customer customer, Book book) {
        this.subscriptionDate = subscriptionDate;
        this.expireDate = expireDate;
        this.subscriptionCost = subscriptionCost;
        this.subscriptionStatus = subscriptionStatus;
        this.paymentPlan = paymentPlan;
        this.customer = customer;
        this.book = book;
    }

    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public LocalDate getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(LocalDate subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public Double getSubscriptionCost() {
        return subscriptionCost;
    }

    public void setSubscriptionCost(Double subscriptionCost) {
        this.subscriptionCost = subscriptionCost;
    }

    public SubscriptionPlan getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(SubscriptionPlan paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }


}
