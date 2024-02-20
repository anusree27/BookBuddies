package com.ford.BookBuddies.dao;

import com.ford.BookBuddies.entity.Book;
import com.ford.BookBuddies.entity.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByBookTitle(String name);

    Optional<Book> deleteByBookTitle(String name);

    List<Book> findAllByBookCategory(BookCategory category);
}
