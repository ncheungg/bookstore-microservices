/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Business;

import com.mycompany.Helper.Book;
import com.mycompany.Helper.Order;
import com.mycompany.Helper.OrdersXML;
import com.mycompany.Helper.User;
import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author student
 */
public class ParseXML {
    
    public static Book toBook(String xml) {
        if (xml.isEmpty()) return null;
        
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Book book = (Book) unmarshaller.unmarshal(new StringReader(xml));
            
            return book;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static User toUser(String xml) {
        if (xml.isEmpty()) return null;
        
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            User user = (User) unmarshaller.unmarshal(new StringReader(xml));
            
            return user;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static ArrayList<Order> toOrderHistory(String xml) {
        if (xml.isEmpty()) return null;
        
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(OrdersXML.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            
            OrdersXML ordersXML = (OrdersXML) unmarshaller.unmarshal(new StringReader(xml));
            ArrayList<Order> orders = ordersXML.getOrders();
            
            return orders;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    
}
