package com.ford.bookbuddies.dao;

import com.ford.bookbuddies.entity.BookOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookOrderRepository extends JpaRepository<BookOrders,Integer> {
}
