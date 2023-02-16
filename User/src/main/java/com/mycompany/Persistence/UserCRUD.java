package com.mycompany.Persistence;

import com.mycompany.Helper.Authenticate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.mycompany.Helper.User;
import java.sql.DriverManager;

public class UserCRUD {
    
    private static Connection getCon() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
//            String dbUrl = System.getenv("DB_URL");
            String dbUrl = "localhost:3306";
            String url = "jdbc:mysql://" + dbUrl + "/user_DB?serverTimezone=UTC&autoReconnect=true&useSSL=false";
            Connection con = DriverManager.getConnection(url, "root", "student");
            return con;
            
        } catch (Exception e) {
            return null;
        }
    }
    
    public static User getUserInfo(String username, String password) {
        User user = null;
        
        try {
            Connection con = getCon();
            
            String q = "select * from user where username = '" + username + "' and password = '" + password + "'";
            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            
            // if there is a valid response in the query
            if (rs.next()) {
                int userID = Integer.parseInt(rs.getString("userID"));
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String phoneNumber = rs.getString("phoneNumber");
                
                user = new User(userID, firstName, lastName, username, phoneNumber);
            }
            con.close();
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return user;
        
    }
    
    public static User getUserInfo(int userID) {
        User user = null;
        
        try {
            Connection con = getCon();
            
            String q = "select * from user where userID = '" + userID + "'";
            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            
            // if there is a valid response in the query
            if (rs.next()) {
                String username = rs.getString("username");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String phoneNumber = rs.getString("phoneNumber");
                
                user = new User(userID, firstName, lastName, username, phoneNumber);
            }
            con.close();
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return user;
        
    }
    
    public static String createUserJWT(String username, String password) {
        User user = getUserInfo(username, password);
        
        if (user == null) return null;
        
        // turn user into jwt
        String jwt = Authenticate.createJWT("customer login page", 
                user.getUserID(), user.getUsername(), 100000);
        
        return jwt;
    }
    
}
