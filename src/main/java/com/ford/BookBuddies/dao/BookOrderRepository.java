package com.ford.BookBuddies.dao;

import com.ford.BookBuddies.entity.BookOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookOrderRepository extends JpaRepository<BookOrders,Integer> {
}
