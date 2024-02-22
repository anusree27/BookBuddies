package com.ford.BookBuddies.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class BookOrders {
    @Id
    @GeneratedValue
    private Integer orderId;
    private Integer totalBookCount;

    private String address;

    private OrderStatus orderStatus;

    @OneToMany
    private List<BookDetail> bookList = new ArrayList<>();

    @OneToOne
    private Payment payment;

    public BookOrders() {

    }

    public Integer getTotalBookCount() {
        return totalBookCount;
    }

    public void setTotalBookCount(Integer totalBookCount) {
        this.totalBookCount = totalBookCount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public BookOrders(Integer totalBookCount, String address, OrderStatus orderStatus, List<BookDetail> bookList, Payment payment) {
        this.totalBookCount = totalBookCount;
        this.address = address;
        this.orderStatus = orderStatus;
        this.bookList = bookList;
        this.payment = payment;
    }

    public BookOrders(List<BookDetail> bookList) {
        this.bookList = bookList;
    }
}
