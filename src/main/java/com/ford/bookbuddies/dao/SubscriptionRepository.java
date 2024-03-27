package com.ford.bookbuddies.dao;

import com.ford.bookbuddies.entity.Book;
import com.ford.bookbuddies.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Integer> {
    List<Subscription> findByExpireDateBefore(LocalDate date);

    List<Subscription> findAllBy();

    List<Subscription> findByBook(Optional<Book> book);

    List<Subscription> findByCustomerUserName(String username);
    List<Subscription> findByCustomerId(Integer userId);
}

