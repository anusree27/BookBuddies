package com.ford.bookbuddies;

import com.ford.bookbuddies.dto.ReviewDto;
import com.ford.bookbuddies.entity.Book;
import com.ford.bookbuddies.entity.Review;
import com.ford.bookbuddies.exception.BookException;
import com.ford.bookbuddies.exception.CustomerException;
import com.ford.bookbuddies.exception.ReviewException;
import com.ford.bookbuddies.service.ReviewService;
import com.ford.bookbuddies.service.ReviewServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ReviewServiceTest {

    private final ReviewService reviewService = new ReviewServiceImpl();

    @Test
    public void testAddReview_Success() {
        ReviewDto reviewDto = new ReviewDto(1, "Great book!", "charu@gmail.com");

        Assertions.assertDoesNotThrow(() -> reviewService.addReview(reviewDto));
    }

    @Test
    public void testUpdateReview_Success() {
        ReviewDto reviewDto = new ReviewDto(1, "Updated review", "charu@gmail.com");

        Assertions.assertDoesNotThrow(() -> reviewService.updateReview(reviewDto));
    }

    @Test
    public void testUpdateReview_InvalidReviewId_ShouldThrowException() {
        ReviewDto reviewDto = new ReviewDto(-1, "Updated review", "charu@gmail.com");

        Assertions.assertThrows(ReviewException.class, () -> reviewService.updateReview(reviewDto));
    }

    @Test
    public void testDeleteReview_InvalidReviewId_ShouldThrowException() {
        int reviewId = -1;

        Assertions.assertThrows(ReviewException.class, () -> reviewService.deleteReview(reviewId));
    }








}
