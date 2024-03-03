package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.dto.ReviewDto;
import com.ford.bookbuddies.entity.Book;
import com.ford.bookbuddies.entity.Review;
import com.ford.bookbuddies.exception.BookException;
import com.ford.bookbuddies.exception.CustomerException;
import com.ford.bookbuddies.exception.ReviewException;
import com.ford.bookbuddies.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/review")
    public Book addReview(@RequestBody Review review) throws ReviewException, CustomerException {
        return reviewService.addReview(review);
    }

    @GetMapping("/reviews/book/{bookId}")
    public List<Review> getBookReviews(@PathVariable("bookId")Integer bookId) throws BookException {
        return reviewService.getBookReviews(bookId);
    }

//    @GetMapping("/reviews/customer/{customerId}")
//    public List<Review> getCustomerReviews(@PathVariable("customerId") Integer customerId){
//        return reviewService.getCustomerReviews(customerId);
//    }

    @PostMapping("/review")
    public Book updateReview(@RequestBody Review review) throws CustomerException, ReviewException {
        return reviewService.updateReview(review);
    }

    @DeleteMapping("/review/{reviewId}")
    public void deleteReview(@PathVariable("reviewId")Integer reviewId){
        reviewService.deleteReview(reviewId);
    }

}
