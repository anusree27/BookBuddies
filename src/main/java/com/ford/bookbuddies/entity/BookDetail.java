package com.ford.bookbuddies.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class BookDetail {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer quantity;

    @OneToOne
    private Book book;

    public BookDetail() {
    }

    public BookDetail(Integer id, Integer quantity, Book book) {
        this.id = id;
        this.quantity = quantity;
        this.book = book;
    }
    public BookDetail(Integer quantity, Book book) {
        this.quantity = quantity;
        this.book = book;
    }

    public BookDetail(Book book, Integer quantity) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "BookDetail{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", book=" + book +
                '}';
    }
}
