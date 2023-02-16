/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Business;

import com.mycompany.Helper.Book;
import java.io.StringReader;
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
    
}
