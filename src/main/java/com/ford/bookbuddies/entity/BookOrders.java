package com.ford.bookbuddies.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class BookOrders {

    @Id
    @GeneratedValue
    private Integer orderId;
    private Integer totalBookCount;
    private OrderStatus orderStatus;
    @OneToMany
    private List<BookDetail> bookList = new ArrayList<>();
    @OneToOne
    private Payment payment;

    //Constructors


    public BookOrders() {

    }

    public BookOrders(Integer orderId, Integer totalBookCount, OrderStatus orderStatus, List<BookDetail> bookList, Payment payment) {
        this.orderId = orderId;
        this.totalBookCount = totalBookCount;
        this.orderStatus = orderStatus;
        this.bookList = bookList;
        this.payment = payment;
    }


    //getters and setters

    public Integer getTotalBookCount() {
        return totalBookCount;
    }

    public void setTotalBookCount(Integer totalBookCount) {
        this.totalBookCount = totalBookCount;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<BookDetail> getBookList() {
        return bookList;
    }

    public void setBookList(List<BookDetail> bookList) {
        this.bookList = bookList;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }


}
