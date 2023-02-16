package com.mycompany.Persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.mycompany.Helper.Book;
import java.sql.DriverManager;


public class BookCRUD {
    
    private static Connection getCon() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
//            String dbUrl = System.getenv("DB_URL");
//            String dbUrl = "localhost:3306";
            String dbUrl = System.getenv("BOOKSDB_PORT_3306_TCP_ADDR") + ":3306";
            String url = "jdbc:mysql://" + dbUrl + "/books_DB?serverTimezone=UTC&autoReconnect=true&useSSL=false";
            Connection con = DriverManager.getConnection(url, "root", "student");
            return con;
            
        } catch (Exception e) {
            return null;
        }
    }
    
    public static Book getBook(int bookID) {
        
        Book book = null;
        
        try {
            Connection con = getCon();
            
            String q = "select * from book where bookID = '" + bookID + "'";
            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String title = rs.getString("title");
                String isbn = rs.getString("isbn");
                String category = rs.getString("category");
                
                book = new Book(bookID, title, isbn, category);
            }
            con.close();
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return book;
    }
    
}
