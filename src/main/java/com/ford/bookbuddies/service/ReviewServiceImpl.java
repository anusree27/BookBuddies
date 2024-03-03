package com.ford.bookbuddies.service;

import com.ford.bookbuddies.dao.BookOrderRepository;
import com.ford.bookbuddies.dao.BookRepository;
import com.ford.bookbuddies.dao.CustomerRepository;
import com.ford.bookbuddies.dao.ReviewRepository;
import com.ford.bookbuddies.entity.*;
import com.ford.bookbuddies.exception.BookException;
import com.ford.bookbuddies.exception.CustomerException;
import com.ford.bookbuddies.exception.ReviewException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        Optional<Customer> customerOptional = this.customerRepository.findByEmail(review.getUserEmail());
        if (customerOptional.isEmpty()) {
            throw  new CustomerException("User not registered to add review");
        }
        Boolean result = false;
        Optional<Book> book = this.bookRepository.findById(review.getBookId());
        Customer customer = customerOptional.get();
        for (BookOrders bl : customer.getOrderList()) {
            for (BookDetail bookDetail : bl.getBookList()) {
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
    public List<Review> getBookReviews(Integer bookId) throws BookException {
        Optional<Book> bookOptional = this.bookRepository.findById(bookId);
        if (bookOptional.isEmpty()) {
            throw new BookException("Book not found");
        }
        Book book = bookOptional.get();
        return book.getReviewList();
    }

    @Override
    public Book updateReview(Review review) throws ReviewException, CustomerException {
        if (review == null) {
            throw new ReviewException("Review cannot be null");
        }

        Optional<Customer> customerOptional = this.customerRepository.findByEmail(review.getUserEmail());
        if (customerOptional.isEmpty()) {
            throw  new CustomerException("User not registered to add review");
        }
        Boolean result = false;
        Optional<Book> book = this.bookRepository.findById(review.getBookId());
        Customer customer = customerOptional.get();
        for (BookOrders bl : customer.getOrderList()) {
            for (BookDetail bookDetail : bl.getBookList()) {
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

//    @Override
//    public List<Review> getCustomerReviews(Integer customerId) {
//        return ReviewRepository.findByCustomerId(customerId);
//    }

    @Override
    public void deleteReview(Integer reviewId) {
        this.reviewRepository.deleteById(reviewId);
    }
}

