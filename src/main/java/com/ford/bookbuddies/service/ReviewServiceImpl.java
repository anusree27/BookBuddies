package com.ford.bookbuddies.service;

import com.ford.bookbuddies.dao.BookOrderRepository;
import com.ford.bookbuddies.dao.BookRepository;
import com.ford.bookbuddies.dao.CustomerRepository;
import com.ford.bookbuddies.dao.ReviewRepository;
import com.ford.bookbuddies.entity.*;
import com.ford.bookbuddies.exception.CustomerException;
import com.ford.bookbuddies.exception.ReviewException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookOrderRepository bookOrderRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book addReview(Review review) throws ReviewException, CustomerException {
        if (review == null) {
            throw new ReviewException("Review cannot be null");
        }

        Optional<Customer> customerOptional = this.customerRepository.findById(review.getUserId());
        if (customerOptional.isEmpty()) {
            throw  new CustomerException("User not registered to add review");
        }
        Boolean result = false;
        Optional<Book> book = this.bookRepository.findById(review.getBookId());
        Customer customer = customerOptional.get();
        for (BookOrders bl : customer.getOrderList()) {
            for (BookDetail bookDetail : bl.getConfirmedOrders().getOrderedBooks()) {
                if(bookDetail.getBook().getBookId() == review.getBookId()) {
                    result = true;
                    book.get().getReviewList().add(review);
                    reviewRepository.save(review);
                    bookRepository.save(book.get());
                    break;
                }

            }
            if(result == true) {
                break;
            }
        }
        if (result == false) {
            throw new ReviewException("User did not purchase the book to review");
        }
        return book.get();
    }

    @Override
    public Review getBookReviewById(Integer reviewId) throws ReviewException {
        Optional<Review> reviewOptional = this.reviewRepository.findById(reviewId);
       if (reviewOptional.isEmpty()) {
           throw new ReviewException("Review not present");
       }

        return reviewOptional.get();
    }

    @Override
    public Book updateReview(Review review) throws ReviewException {
        if (review == null) {
            throw new ReviewException("Review cannot be null");
        }
        Optional<Review> reviewOptional = this.reviewRepository.findById(review.getReviewId());
        if (reviewOptional.isEmpty()) {
            throw new ReviewException("Review is not present to update");
        }
        Optional<Book> book = this.bookRepository.findById(review.getBookId());
        reviewRepository.save(review);
        bookRepository.save(book.get());
        return book.get();
    }

//    @Override
//    public List<Review> getCustomerReviews(Integer customerId) {
//        return this.reviewRepository.
//    }

    @Override
    public List<Review> deleteReview(Integer reviewId) {
        Optional<Review> reviewOptional = this.reviewRepository.findById(reviewId);
        Optional<Book> bookOptional=this.bookRepository.findById(reviewOptional.get().getBookId());
        List<Review> reviewsList=bookOptional.get().getReviewList().stream().filter((r)->!r.getReviewId().equals(reviewId)).collect(Collectors.toList());
        bookOptional.get().setReviewList(reviewsList);
        this.bookRepository.save(bookOptional.get());
        this.reviewRepository.deleteById(reviewId);
        return reviewsList;
    }

}

