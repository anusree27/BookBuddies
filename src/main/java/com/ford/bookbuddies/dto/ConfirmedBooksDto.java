package com.ford.bookbuddies.dto;

import com.ford.bookbuddies.entity.BookDetail;

import java.util.ArrayList;
import java.util.List;

public class ConfirmedBooksDto {
    private Integer userId;
    List<BookDetail> orderedBooks = new ArrayList<>();

    public ConfirmedBooksDto() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<BookDetail> getOrderedBooks() {
        return orderedBooks;
    }

    public void setOrderedBooks(List<BookDetail> orderedBooks) {
        this.orderedBooks = orderedBooks;
    }

    public ConfirmedBooksDto(Integer userId, List<BookDetail> orderedBooks) {
        this.userId = userId;
        this.orderedBooks = orderedBooks;
    }
}
