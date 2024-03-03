package com.ford.bookbuddies.entity;

import jakarta.persistence.*;

@Entity
public class Payment {
    @Id
    @GeneratedValue
    private Integer paymentId;
    private Boolean paymentStatus;
    private Double totalCost;

    private String address;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

    public Payment() {
    }

    public Payment(Boolean paymentStatus, Double totalCost, String address, PaymentMode paymentMode) {
        this.paymentStatus = paymentStatus;
        this.totalCost = totalCost;
        this.address = address;
        this.paymentMode = paymentMode;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Boolean getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }
    //address
}
