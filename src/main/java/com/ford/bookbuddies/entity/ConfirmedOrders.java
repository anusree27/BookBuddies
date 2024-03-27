package com.ford.bookbuddies.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ConfirmedOrders {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer userId;

    private Boolean isPaid = false;
    @ManyToMany
    List<BookDetail> orderedBooks = new ArrayList<>();

    public ConfirmedOrders() {
    }

    public ConfirmedOrders(Integer userId, List<BookDetail> orderedBooks) {
        this.userId = userId;
        this.orderedBooks = orderedBooks;
    }

    public ConfirmedOrders( Integer userId, Boolean isPaid, List<BookDetail> orderedBooks) {
        this.userId = userId;
        this.isPaid = isPaid;
        this.orderedBooks = orderedBooks;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public List<BookDetail> getOrderedBooks() {
        return orderedBooks;
    }

    public void setOrderedBooks(List<BookDetail> orderedBooks) {
        this.orderedBooks = orderedBooks;
    }


}
