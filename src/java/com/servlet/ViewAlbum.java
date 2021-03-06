/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet;

import com.collection.Word;
import com.util.AlbumManager;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "ViewAlbum", urlPatterns = {"/ViewAlbum"})
public class ViewAlbum extends HttpServlet {

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
            out.println("<title>Servlet ViewAlbum</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewAlbum at " + request.getContextPath() + "</h1>");
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
            //processRequest(request, response);
            String album = request.getParameter("album");
            HttpSession session = request.getSession();
            String userId = session.getAttribute("_id").toString();
            AlbumManager am = new AlbumManager();
            File f = new File(SaveToAlbum.fileUrl);
            String data = "";
            if (userId != "") {
                List<Word> lw = am.selectAlbum(f, userId, album);
                if (lw!=null) {
                    for (Word temp : lw) {
                        data = "<div class='card effect-click'"
                                + "style='display:none'>"
                                + "<div class='card-front'>"
                                + "<div class='card-text'>"
                                + temp.getName()
                                + "</div>"
                                + "</div>"
                                + "<div id='" + temp.getName() + "' class='card-back'>"
                                + "<div class='card-info'>"
                                + "<div id='my-type'>" + temp.getType() + "</div>"
                                + "<div class='my-definition'>" + temp.getDefinition() + "</div>"
                                + "</div>"
                                + "</div>"
                                + "</div>" + data;
                    }
                    data = "<h4>" + album + "</h4>" + data;
                    response.setContentType("text/plain");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(data);
                }
            } else {
                response.sendRedirect("Login");
            }

        } catch (JDOMException ex) {
            Logger.getLogger(ViewAlbum.class.getName()).log(Level.SEVERE, null, ex);
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
