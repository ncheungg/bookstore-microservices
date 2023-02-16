package com.mycompany.Helper;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {
    
    private int orderID;
    private int userID;
    private int paymentID;
    private String purchaseDate;
    private double orderTotal;
    
    @XmlElement(name = "book")
    private ArrayList<Book> books;
    
    // no-arg default constructor for JAXB
    private Order() {
        this(0, 0, 0, null, 0);
    }

    public Order(int orderID, int userID, int paymentID, String purchaseDate, double orderTotal) {
        this.orderID = orderID;
        this.userID = userID;
        this.paymentID = paymentID;
        this.purchaseDate = purchaseDate;
        this.orderTotal = orderTotal;
    }

    public Order(int orderID, int userID, int paymentID, String purchaseDate, double orderTotal, ArrayList<Book> books) {
        this.orderID = orderID;
        this.userID = userID;
        this.paymentID = paymentID;
        this.purchaseDate = purchaseDate;
        this.orderTotal = orderTotal;
        this.books = books;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public ArrayList<Book> getBooks() {
        return new ArrayList<>(books);
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }
    
    public int getBookCount() {
        return books.size();
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    
}