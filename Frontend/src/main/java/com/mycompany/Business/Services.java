/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Business;

import com.mycompany.Helper.Book;
import com.mycompany.Helper.Order;
import com.mycompany.Helper.User;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author student
 */
public class Services {
    
    public static ArrayList<Order> getOrderHistory(String jwt) throws IOException {
        
        Client client = ClientBuilder.newClient();
//        String serviceUrl = System.getenv("historyService");
        String serviceUrl = "localhost:8080";
        WebTarget webTarget = client.
                target("http://" + serviceUrl + "/History/webresources/history");
        InputStream is = webTarget.request(MediaType.APPLICATION_XML).
                cookie("jwt_token", jwt).get(InputStream.class);
        
        String xml = IOUtils.toString(is, "utf-8");
        ArrayList<Order> orders = ParseXML.toOrderHistory(xml);
        
        return orders;
    }
    
    public static Book getBook(int bookID) throws IOException {
        
        Client client = ClientBuilder.newClient();
//        String serviceUrl = System.getenv("bookService");
        String serviceUrl = "localhost:8080";
        WebTarget webTarget = client.
                target("http://" + serviceUrl + "/Books/webresources/book");
        InputStream is = webTarget.path(Integer.toString(bookID)).
                request(MediaType.APPLICATION_XML).get(InputStream.class);
        
        String xml = IOUtils.toString(is, "utf-8");
        Book book = ParseXML.toBook(xml);
        
        return book;
    }
    
    public static User getUserInfo(String jwt) throws IOException {
        
        Client client = ClientBuilder.newClient();
//        String serviceUrl = System.getenv("userService");
        String serviceUrl = "localhost:8080";
        WebTarget webTarget = client.
                target("http://" + serviceUrl + "/User/webresources/user");
        InputStream is = webTarget.request(MediaType.APPLICATION_XML).
                cookie("jwt_token", jwt).get(InputStream.class);
        
        String xml = IOUtils.toString(is, "utf-8");
        User user = ParseXML.toUser(xml);
        
        return user;
    }
    
    public static User login(String username, String password) 
            throws IOException {
        
        Client client = ClientBuilder.newClient();
//        String serviceUrl = System.getenv("loginService");
        String serviceUrl = "localhost:8080";
        WebTarget webTarget = client.
                target("http://" + serviceUrl + "/Login/webresources/login");
        InputStream is = webTarget.request(MediaType.APPLICATION_XML).
                cookie("username", username).cookie("password", password)
                .get(InputStream.class);
        
        String xml = IOUtils.toString(is, "utf-8");
        User user = ParseXML.toUser(xml);
        
        return user;
    }
    
}
