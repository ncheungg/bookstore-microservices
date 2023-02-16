package com.mycompany.Helper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "book")
@XmlAccessorType(XmlAccessType.FIELD)
public class Book {
    
    private int bookID;
    private String title;
    private String isbn;
    private String category;
    
    // no-arg default constructor for JAXB
    private Book() {
        this(0, null, null, null);
    }

    public Book(int bookID, String title, String isbn, String category) {
        this.bookID = bookID;
        this.title = title;
        this.isbn = isbn;
        this.category = category;
    }
    
    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
}