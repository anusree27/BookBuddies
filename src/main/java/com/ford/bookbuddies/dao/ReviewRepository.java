package com.ford.bookbuddies.dao;

import com.ford.bookbuddies.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

//    Optional<Review> findByBookId(Integer bookId);

//    Optional<Review> findByCustomerId(Integer customerId);
}


