package com.ford.bookbuddies.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Review {
    @Id
    @GeneratedValue
    private Integer reviewId;
    private String review;
    private Integer bookId;
    private String userName;

    private Integer userId;

    public Review() {
    }

    public Review( String review, Integer bookId) {

        this.review = review;
        this.bookId = bookId;

    }

    public Review(Integer reviewId, String review, Integer bookId, String userName, Integer userId) {
        this.reviewId = reviewId;
        this.review = review;
        this.bookId = bookId;
        this.userName = userName;
        this.userId = userId;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    //Constructors





}
