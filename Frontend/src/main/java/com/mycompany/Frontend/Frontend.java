/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Frontend;

import com.mycompany.Business.Services;
import com.mycompany.Helper.Authenticate;
import com.mycompany.Helper.Order;
import com.mycompany.Helper.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;

/**
 *
 * @author student
 */
@WebServlet(name = "Frontend", urlPatterns = {"/Frontend"})
public class Frontend extends HttpServlet {
    
    private Map<String, String> getUserAuthentication(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = "";
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("isLoggedIn", "false");
        
        if (cookies == null) return userInfo;
        
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("jwt_token")) 
                token = cookie.getValue();
        }
        
        if (token.isEmpty()) return userInfo;
        
        try {
            return Authenticate.decodeJWT(token);
        } catch (Exception e) {
            return userInfo;
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Map<String, String> userInfo = getUserAuthentication(request);
        boolean isAuthenticated = userInfo.get("isLoggedIn").equals("true");
        String requestedPage = request.getParameter("request-page");
        
        String jwt = "";
        Cookie[] cookies = request.getCookies();
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt_token"))
                    jwt = cookie.getValue();
            }
        }
        
        RequestDispatcher rd;
        
        switch (requestedPage) {
            case "customer login page":
                // if we're authenticated already, skip to home page
                if (isAuthenticated) {
                    rd = request.getRequestDispatcher("customerHome.jsp");
                    rd.forward(request, response);
                    break;
                }
                
                rd = request.getRequestDispatcher("customerLogin.jsp");
                rd.forward(request, response);
                break;
            
            case "admin login page":
                rd = request.getRequestDispatcher("adminLogin.jsp");
                rd.forward(request, response);
                break;
                
            case "customer profile page":
                if (!isAuthenticated) break;
                
                User user = Services.getUserInfo(jwt);
                request.setAttribute("user", user);
                
                rd = request.getRequestDispatcher("customerProfile.jsp");
                rd.forward(request, response);
                break;
                
            case "customer history page":
                if (!isAuthenticated) break;
                
                ArrayList<Order> history = Services.getOrderHistory(jwt);
                request.setAttribute("history", history);
                
                rd = request.getRequestDispatcher("customerHistory.jsp");
                rd.forward(request, response);
                break;
                
            case "customer search page":
                if (!isAuthenticated) break;
                
                rd = request.getRequestDispatcher("search.jsp");
                rd.forward(request, response);
                break;
                
            case "customer home page":
                // if we're here, user doesn't have a JWT
                // so we must authenticate
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                User user2 = Services.login(username, password);
                
                if (user2 == null) break;
                
                jwt = Authenticate.createJWT("login page", 
                        user2.getUserID(), username, 100000);
                Cookie cookie = new Cookie("jwt_token", jwt);
                
                response.addCookie(cookie);
                request.setAttribute("username", user2.getUsername());
                request.setAttribute("userID", user2.getUserID());
                
                rd = request.getRequestDispatcher("customerHome.jsp");
                rd.forward(request, response);
                break;
                
            default:
                rd = request.getRequestDispatcher("error.jsp");
                rd.forward(request, response);
                break;
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
