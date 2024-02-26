package com.ford.BookBuddies.dto;

import com.ford.BookBuddies.entity.BookDetail;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;

import java.util.List;
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class ConfirmedBooks {
    private Integer userId;
    private List<BookDetail> orderedbooks;

    public ConfirmedBooks(Integer userId, List<BookDetail> orderedbooks) {
        this.userId = userId;
        this.orderedbooks = orderedbooks;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<BookDetail> getOrderedbooks() {
        return orderedbooks;
    }

    public void setOrderedbooks(List<BookDetail> orderedbooks) {
        this.orderedbooks = orderedbooks;
    }

    public ConfirmedBooks() {
    }
}
