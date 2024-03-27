package com.ford.bookbuddies.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Payment {
    @Id
    @GeneratedValue
    private Integer paymentId;

    private Integer userId;
    private Boolean paymentStatus;
    private Double totalCost;

    private Long mobileNo;
    private String address;



    public Payment() {
    }

    public Payment( Double totalCost, String address) {

        this.totalCost = totalCost;
        this.address = address;

    }

    public Payment(Integer userId, Boolean paymentStatus, Double totalCost, Long mobileNo, String address) {
        this.userId = userId;
        this.paymentStatus = paymentStatus;
        this.totalCost = totalCost;
        this.mobileNo = mobileNo;
        this.address = address;

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



    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Long mobileNo) {
        this.mobileNo = mobileNo;
    }
    //address
}
