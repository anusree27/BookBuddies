package com.ford.bookbuddies.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class BookStock {
    @Id
    @GeneratedValue
    private Integer bookStockId;
    @OneToOne
    private Book book;
    private Integer stockQuantity;


    //Constructors


    public BookStock(Book book, Integer stockQuantity) {
        this.book = book;
        this.stockQuantity = stockQuantity;
    }

    public BookStock() {
    }


    //getters and setters

    public Integer getBookStockId() {
        return bookStockId;
    }

    public void setBookStockId(Integer bookStockId) {
        this.bookStockId = bookStockId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

}
