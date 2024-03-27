package com.ford.bookbuddies.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {


    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String userName;

    private Long phoneNumber;
    private String email;
    private String password;

    @OneToMany
    private List<BookOrders> orderList =new ArrayList<>();

    @OneToOne
    private Cart cart;


    //Constructors

    public Customer(String name, String email, String password, String userName, Cart cart) {
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.password = password;
       this.cart = cart;
    }

    public Customer(Integer id, String name, String userName, String email, String password) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public Customer(Integer id, String name, String email, String password, String userName, Cart cart) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.cart = cart;
    }

    public Customer() {
    }

    public Customer( String name, String email, String userName, String password) {
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Customer(String name, String userName, Long phoneNumber, String email, String password, List<BookOrders> orderList, Cart cart) {
        this.name = name;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.orderList = orderList;
        this.cart = cart;
    }


    //getters and setters

    public Cart getCart() {
        return cart;
    }

    public List<BookOrders> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<BookOrders> list) {
        this.orderList = list;
    }

    public Customer(List<BookOrders> orderList) {
        this.orderList = orderList;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    //userorders?
//    @OneToMany
//    private List<BookOrder> BookOrders = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

