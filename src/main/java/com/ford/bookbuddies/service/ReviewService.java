package com.ford.bookbuddies.service;

import com.ford.bookbuddies.entity.Book;
import com.ford.bookbuddies.entity.Review;
import com.ford.bookbuddies.exception.BookException;
import com.ford.bookbuddies.exception.CustomerException;
import com.ford.bookbuddies.exception.ReviewException;

import java.util.List;

public interface ReviewService {
    Book addReview(Review review) throws ReviewException, CustomerException;
   List<Review> getBookReviews(Integer bookId) throws BookException;
//   List<Review> getCustomerReviews(Integer customerId);

    Book updateReview(Review review) throws ReviewException, CustomerException;
   void deleteReview(Integer reviewId);

}
