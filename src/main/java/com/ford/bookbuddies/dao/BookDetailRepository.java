package com.ford.bookbuddies.dao;

import com.ford.bookbuddies.entity.Book;
import com.ford.bookbuddies.entity.BookDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookDetailRepository extends JpaRepository<BookDetail, Integer> {
    Optional<BookDetail> findByBook(Book book);
    void deleteByBook(Book book);
}
