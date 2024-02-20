package com.ford.BookBuddies.dao;

import com.ford.BookBuddies.entity.Book;
import com.ford.BookBuddies.entity.BookStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookStockRepository extends JpaRepository<BookStock, Integer> {
    Optional<BookStock> deleteBookStockByBook(Book book);

    Optional<BookStock> findBookStockByBook(Book book);
}
