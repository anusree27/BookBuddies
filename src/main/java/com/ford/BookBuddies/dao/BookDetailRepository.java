package com.ford.BookBuddies.dao;

import com.ford.BookBuddies.entity.BookDetail;
import com.ford.BookBuddies.entity.BookStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookDetailRepository extends JpaRepository<BookDetail, Integer> {

}
