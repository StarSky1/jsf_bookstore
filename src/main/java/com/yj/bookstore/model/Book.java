package com.yj.bookstore.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author 76355
 * @Date 2019/4/27 15:57
 * @Description
 */
@Table(name = "book")
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "book_name",length = 50)
    private String bookName;

    @Column(name = "publish_date")
    @Temporal(TemporalType.DATE)
    private Date publishDate;

    @Column(length = 20)
    private String author;

    @Column(name = "publish_house",length = 50)
    private String publishHouse;

    @Column(length = 30)
    private String category;

    private Double price;

    @Column(length = 200)
    private String brief;

    public Book() {
    }

    public Book(String bookName, Double price) {
        this.bookName = bookName;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", publishDate=" + publishDate +
                ", author='" + author + '\'' +
                ", publishHouse='" + publishHouse + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", brief='" + brief + '\'' +
                '}';
    }

    public String getPublishHouse() {
        return publishHouse;
    }

    public void setPublishHouse(String publishHouse) {
        this.publishHouse = publishHouse;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
}
