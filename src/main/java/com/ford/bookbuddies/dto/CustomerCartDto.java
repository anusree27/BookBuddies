package com.ford.bookbuddies.dto;

public class CustomerCartDto {
    private Integer userId;
    private Integer bookId;
    private Integer quantity;

    public CustomerCartDto() {
    }

    public CustomerCartDto(Integer userId,Integer bookId, Integer quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.userId=userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
