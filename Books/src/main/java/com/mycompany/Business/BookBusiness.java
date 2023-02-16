/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Business;

import com.mycompany.Helper.Book;
import com.mycompany.Persistence.BookCRUD;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.event.Event;
import io.kubemq.sdk.tools.Converter;
import javax.net.ssl.SSLException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 *
 * @author student
 */
public class BookBusiness {
    
    public boolean order(int bookID, int userID) throws ClassNotFoundException, 
            SQLException, ServerAddressNotSuppliedException, IOException, InterruptedException {
        
        boolean success = false;
        Book book = BookCRUD.getBook(bookID);
        
        if (book != null) {
            success = true;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate orderDate = LocalDate.now();
            
            Messaging.sendMessage("ORDER:" + Integer.toString(bookID) + ":" + Integer.toString(userID) + ":" + orderDate.format(formatter));
        }
        
        return success;
    }
    
}
