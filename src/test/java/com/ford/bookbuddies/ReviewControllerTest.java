package com.ford.bookbuddies;


import com.ford.bookbuddies.controller.ReviewController;
import com.ford.bookbuddies.entity.Book;
import com.ford.bookbuddies.entity.Review;
import com.ford.bookbuddies.exception.BookException;
import com.ford.bookbuddies.exception.CustomerException;
import com.ford.bookbuddies.exception.ReviewException;
import com.ford.bookbuddies.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @Test
    public void testAddReview_Success() throws ReviewException, CustomerException, BookException {
        Review review = new Review("Great book!", 1, "charu@gmail.com");
        Book expectedBook = new Book();

        when(reviewService.addReview(review)).thenReturn(expectedBook);

        Book result = reviewController.addReview(review);

        assertNotNull(result);
        assertEquals(expectedBook, result);
        verify(reviewService, times(1)).addReview(review);
    }

    @Test
    public void testUpdateReview_NonExistentReview() throws CustomerException, ReviewException {
        Review review = new Review("Review to update", 1, "charu@gmail.com");


        when(reviewService.updateReview(review)).thenThrow(new ReviewException("Review not found"));


        assertThrows(ReviewException.class, () -> reviewController.updateReview(review));


    }



    @Test
    public void testUpdateReview_Success() throws CustomerException, ReviewException {
        Review review = new Review("Updated review", 1, "charu@gmail.com");
        Book expectedBook = new Book();

        when(reviewService.updateReview(review)).thenReturn(expectedBook);

        Book result = reviewController.updateReview(review);

        assertNotNull(result);
        assertEquals(expectedBook, result);
        verify(reviewService, times(1)).updateReview(review);
    }
    @Test
    public void testDeleteReview_Success() throws ReviewException {
        int reviewId = 1;

        assertDoesNotThrow(() -> reviewController.deleteReview(reviewId));
        verify(reviewService, times(1)).deleteReview(reviewId);
    }

    @Test
    public void testUpdateReview_InvalidData() throws CustomerException, ReviewException {
        Review review = new Review("Updated review", 12345, "invalid_email@example.com");


        when(reviewService.updateReview(review)).thenThrow(new ReviewException("Invalid data in the review"));


        assertThrows(ReviewException.class, () -> reviewController.updateReview(review));


    }

    @Test
    public void testUpdateReview_InvalidReviewId() throws CustomerException, ReviewException {
        Review review = new Review("Updated review", -1, "user@example.com");


        when(reviewService.updateReview(review)).thenThrow(new ReviewException("Invalid reviewId"));


        assertThrows(ReviewException.class, () -> reviewController.updateReview(review));

    }




}
