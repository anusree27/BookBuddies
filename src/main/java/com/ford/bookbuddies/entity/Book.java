package com.ford.bookbuddies.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {
    @Id
    @GeneratedValue
    private Integer bookId;
    private String bookTitle;
    private String bookAuthor;
    private Double price;

    private String image;
    private String pdfLink;
    @Enumerated(EnumType.STRING)
    private BookCategory bookCategory;
    @OneToMany
    private List<Review> reviewList=new ArrayList<>();

    //Constructors

    public Book() {
    }

    public Book( String bookTitle, String bookAuthor, Double price, BookCategory bookCategory) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.price = price;
        this.bookCategory = bookCategory;
    }

    public Book(String bookTitle, String bookAuthor, Double price, String image, BookCategory bookCategory, List<Review> reviewList) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.price = price;
        this.image = image;
        this.bookCategory = bookCategory;
        this.reviewList = reviewList;
    }


    //getters and setters

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public BookCategory getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPdfLink() {
        return pdfLink;
    }

    public void setPdfLink(String pdfLink) {
        this.pdfLink = pdfLink;
    }
}
