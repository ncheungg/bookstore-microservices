/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Endpoint;

import com.mycompany.Helper.Authenticate;
import com.mycompany.Helper.OrdersXML;
import com.mycompany.Persistence.OrdersCRUD;
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
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXB;

/**
 * REST Web Service
 *
 * @author student
 */
@Path("history")
public class HistoryResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of HistoryResource
     */
    public HistoryResource() {
    }

    /**
     * Retrieves representation of an instance of com.mycompany.Endpoint.HistoryResource
     * @param jwt
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml(@CookieParam("jwt_token") String jwt) {
                    
        try {
            Map<String, String> userInfo = Authenticate.decodeJWT(jwt);
            
            if (userInfo.get("isLoggedIn").equals("false"))
                throw new Exception();
            
            int userID = Integer.parseInt(userInfo.get("userID"));
            OrdersXML orders = new OrdersXML(OrdersCRUD.getOrderHistory(userID));
            
            StringWriter sw = new StringWriter();
            JAXB.marshal(orders, sw);
            return sw.toString();
            
        } catch (Exception ex) {
            Logger.getLogger(HistoryResource.class.getName()).log(Level.SEVERE, null, ex);
            return "Error getting OrdersXML!";
        }
    }

    /**
     * PUT method for updating or creating an instance of HistoryResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
