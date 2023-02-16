/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Business;

import com.mycompany.Helper.Book;
import java.io.IOException;
import java.io.InputStream;
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
    
    public static Book getBook(int bookID) throws IOException {
        
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.
                target("http://localhost:8080/Books/webresources/book");
        InputStream is = webTarget.path(Integer.toString(bookID)).
                request(MediaType.APPLICATION_XML).get(InputStream.class);
        
        String xml = IOUtils.toString(is, "utf-8");
        Book book = ParseXML.toBook(xml);
        
        return book;
    }
    
}
