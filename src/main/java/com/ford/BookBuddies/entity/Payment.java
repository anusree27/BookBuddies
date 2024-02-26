package com.ford.BookBuddies.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;

@Entity
//@Getter
//@Setter
//@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue
    private Integer paymentId;
    private Double totalCost;
    private Boolean paymentStatus;
    private String address;
    //address
}
