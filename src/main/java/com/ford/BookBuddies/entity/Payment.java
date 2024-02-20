package com.ford.BookBuddies.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Payment {
    @Id
    @GeneratedValue
    private Integer paymentId;
    private Boolean paymentStatus;
    private Double totalCost;
    //address
}
