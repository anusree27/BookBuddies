package com.ford.BookBuddies.dto;

public class CustomerCartDto {
    private String bookName;
    private Integer quantity;

    public CustomerCartDto() {
    }

    public CustomerCartDto(String bookName, Integer quantity) {
        this.bookName = bookName;
        this.quantity = quantity;
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
