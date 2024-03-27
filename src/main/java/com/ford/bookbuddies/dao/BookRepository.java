package com.ford.bookbuddies.dao;

import com.ford.bookbuddies.entity.Book;
import com.ford.bookbuddies.entity.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByBookTitleIgnoreCase(String name);

    Optional<Book> findByBookId(Integer id);
    Optional<Book> deleteByBookTitle(String name);

    List<Book> findAllByBookCategory(BookCategory category);
}
