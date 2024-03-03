package com.ford.bookbuddies.dto;

public class ReviewDto {
    private Integer reviewId;
    private String review;
    private String userEmail;

    public ReviewDto(Integer reviewId, String review, String userEmail) {
        this.reviewId = reviewId;
        this.review = review;
        this.userEmail = userEmail;
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

    public String getUserEmail() {
        return userEmail;
    }

}
