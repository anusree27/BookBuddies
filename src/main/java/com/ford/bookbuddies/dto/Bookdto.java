package com.ford.bookbuddies.dto;

//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;

//@AllArgsConstructor
//@NoArgsConstructor
//@Setter
//@Getter
public class Bookdto {
    private Integer userId;
    private Integer bookId;

    public Bookdto(Integer userId, Integer bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    public Bookdto() {
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
}
