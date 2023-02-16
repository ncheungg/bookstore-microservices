/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Endpoint;

import com.mycompany.Helper.Authenticate;
import com.mycompany.Helper.Book;
import com.mycompany.Persistence.BookCRUD;
import java.io.StringWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 * REST Web Service
 *
 * @author student
 */
@Path("book/{bookID}")
public class BookResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BookResource
     */
    public BookResource() {
    }

    /**
     * Retrieves representation of an instance of com.mycompany.Endpoint.BookResource
     * @param bookID
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml(@PathParam("bookID") int bookID) {
        
        try {
            Book book = BookCRUD.getBook(bookID);
            
            StringWriter sw = new StringWriter();
            JAXB.marshal(book, sw);
            return sw.toString();
            
        } catch (Exception ex) {
            Logger.getLogger(BookResource.class.getName()).log(Level.SEVERE, null, ex);
            return "Error getting book!";
        }
    }

    /**
     * PUT method for updating or creating an instance of BookResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
