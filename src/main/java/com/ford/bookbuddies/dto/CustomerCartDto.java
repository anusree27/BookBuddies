package com.ford.bookbuddies.dto;

public class CustomerCartDto {
    private Integer userId;
    private String bookName;
    private Integer quantity;

    public CustomerCartDto() {
    }

    public CustomerCartDto(Integer userId,String bookName, Integer quantity) {
        this.bookName = bookName;
        this.quantity = quantity;
        this.userId=userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
