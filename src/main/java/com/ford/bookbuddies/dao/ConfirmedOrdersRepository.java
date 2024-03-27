package com.ford.bookbuddies.dao;

import com.ford.bookbuddies.entity.ConfirmedOrders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConfirmedOrdersRepository extends JpaRepository<ConfirmedOrders, Integer> {
    Optional<List<ConfirmedOrders>> findAllByUserId(Integer id);
}
