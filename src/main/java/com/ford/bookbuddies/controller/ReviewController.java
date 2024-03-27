package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.entity.Book;
import com.ford.bookbuddies.entity.Review;
import com.ford.bookbuddies.exception.CustomerException;
import com.ford.bookbuddies.exception.ReviewException;
import com.ford.bookbuddies.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/review")
    public Book addReview(@RequestBody Review review) throws ReviewException, CustomerException {
        return reviewService.addReview(review);
    }

    @GetMapping("/review/{id}")
    public Review getBookReviewsByReviewId(@PathVariable("id")Integer reviewId) throws ReviewException {
        return reviewService.getBookReviewById(reviewId);
    }

//    @GetMapping("/reviews/customer/{customerId}")
//    public List<Review> getCustomerReviews(@PathVariable("customerId") Integer customerId){
//        return reviewService.getCustomerReviews(customerId);
//    }

    @PutMapping("/review")
    public Book updateReview(@RequestBody Review review) throws CustomerException, ReviewException {
        return reviewService.updateReview(review);
    }

    @DeleteMapping("/review/{reviewId}")
    public List<Review> deleteReview(@PathVariable("reviewId")Integer reviewId){
        return reviewService.deleteReview(reviewId);
    }

}
