package com.mycompany.Persistence;

import com.mycompany.Business.Services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.mycompany.Helper.Order;
import com.mycompany.Helper.Book;
import java.io.IOException;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;


public class OrdersCRUD {
    
    private static Connection getCon() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
//            String dbUrl = System.getenv("DB_URL");
            String dbUrl = "localhost:3306";
            String url = "jdbc:mysql://" + dbUrl + "/orders_DB?serverTimezone=UTC&autoReconnect=true&useSSL=false";
            Connection con = DriverManager.getConnection(url, "root", "student");
            return con;
            
        } catch (Exception e) {
            return null;
        }
    }
    
    public static ArrayList<Order> getOrderHistory(int userID) {
        
        ArrayList<Order> orders = new ArrayList<>();
        
        try {
            Connection con = getCon();
            
            String q = "select * from orders where userID = '" + userID + "'";
            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            
            // loop through the orders and add necessary values
            while (rs.next()) {
                int orderID = Integer.parseInt(rs.getString("orderID"));
                int paymentID = Integer.parseInt(rs.getString("paymentID"));
                String purchaseDate = rs.getString("purchaseDate");
                double orderTotal = Double.parseDouble(rs.getString("orderTotal"));
                
                Order order = new Order(orderID, userID, paymentID, purchaseDate, orderTotal);
                orders.add(order);
            }
            con.close();
            
            for (Order order : orders) {
                int orderID = order.getOrderID();
                ArrayList<Book> books = getOrderItems(orderID);
                order.setBooks(books);
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return orders;
        
    }
    
    public static ArrayList<Book> getOrderItems(int orderID) {
        
        ArrayList<Integer> bookIDs = new ArrayList<>();
        ArrayList<Book> books = new ArrayList<>();
        
        try {
            Connection con = getCon();
            
            String q = "select * from order_items where orderID = '" + orderID + "'";
            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                int bookID = Integer.parseInt(rs.getString("bookID"));
                bookIDs.add(bookID);
            }
            con.close();
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        // loop thru and get books
//        for (int bookID : bookIDs) {
//
//            try {
//                Book book = Services.getBook(bookID);
//                
//                if (book != null) 
//                    books.add(book);
//            } catch (IOException ex) {
//                Logger.getLogger(OrdersCRUD.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            
//        }
        for (int bookID : bookIDs)
            books.add(new Book(bookID, null, null, null));
        
        return books;
        
    }
    
    public static void addOrder(int bookID, int userID, String date) {}
    
}
