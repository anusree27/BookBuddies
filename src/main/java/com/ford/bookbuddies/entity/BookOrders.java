package com.ford.bookbuddies.entity;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class BookOrders {
    @Id
    @GeneratedValue
    private Integer orderId;
    private Integer totalBookCount;

    private OrderStatus orderStatus;
    private LocalDate orderDate;
//    @OneToMany
//    private List<BookDetail> bookList = new ArrayList<>();
    @OneToOne
    private ConfirmedOrders confirmedOrders;

    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;

    //Constructors
    public BookOrders() {

    }

    public BookOrders(Integer orderId, Integer totalBookCount, OrderStatus orderStatus, LocalDate orderDate, ConfirmedOrders confirmedOrders, Payment payment) {
        this.orderId = orderId;
        this.totalBookCount = totalBookCount;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.confirmedOrders = confirmedOrders;
        this.payment = payment;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }


    public void setTotalBookCount(Integer totalBookCount) {
        this.totalBookCount = totalBookCount;
    }


    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Integer getTotalBookCount() {
        return totalBookCount;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }


    public Payment getPayment() {
        return payment;
    }

    public ConfirmedOrders getConfirmedOrders() {
        return confirmedOrders;
    }

    public void setConfirmedOrders(ConfirmedOrders confirmedOrders) {
        this.confirmedOrders = confirmedOrders;
    }
}
