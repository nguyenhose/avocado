/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet;

import com.collection.Album;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.util.AlbumManager;
import com.util.MongoConnection;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jdom2.JDOMException;

/**
 *
 * @author nguyen
 */
@WebServlet(name = "ViewLibrary", urlPatterns = {"/ViewLibrary"})
public class ViewLibrary extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewLibrary</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewLibrary at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        try {
            HttpSession session = request.getSession(false);
            if (!session.isNew() && session != null) {
                String userId = session.getAttribute("_id").toString();
                String keyword = request.getParameter("keyword");
                AlbumManager am = new AlbumManager();
                List<Album> publicAlbumList = new ArrayList<>();
                File f = new File(SaveToAlbum.fileUrl);
                if (f.exists() && !f.isDirectory()) {
                    if (keyword != null) {
                        publicAlbumList = am.searchLibrary(f, userId, keyword);
                    } else {
                        publicAlbumList = am.selectLibrary(f, userId);
                    }
                    String data = "";
                    String name = "";
                    if (publicAlbumList.size() > 0) {
                        MongoClient mongo = MongoConnection.getConnection();
                        DB db = mongo.getDB("avocado");
                        DBCollection col = db.getCollection("users");

                        for (Album al : publicAlbumList) {
                            BasicDBObject allQuery = new BasicDBObject("_id", al.getUserId());
                            DBCursor cursor = col.find(allQuery);
                            if (cursor.hasNext()) {
                                BasicDBObject obj = (BasicDBObject) cursor.next();
                                name = obj.getString("name");
                            }
                            data = "<div class='library-item'>"
                                    + "<div class='public-user'>" + name + "</div>"
                                    + "<div class='public-name'>" + al.getName() + "</div>"
                                    + "<div class='public-follow'> Follow this album.</div>"
                                    + "</div>" + data;
                        }
                    } else {
                        data = "<div class='no-error'>No library, try again ?</div>";
                    }

                    response.setContentType("text/plain");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(data);
                }
            } else {
                response.sendRedirect("/avocado/login.jsp");
            }
        } catch (JDOMException ex) {
            Logger.getLogger(ViewLibrary.class.getName()).log(Level.SEVERE, null, ex);
        }
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
