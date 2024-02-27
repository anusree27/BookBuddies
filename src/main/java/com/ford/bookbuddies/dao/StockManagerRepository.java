package com.ford.bookbuddies.dao;

import com.ford.bookbuddies.entity.StockManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockManagerRepository extends JpaRepository<StockManager, Integer> {
    Optional<StockManager> findByName(String string);
}
