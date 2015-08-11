/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet;

import com.collection.Album;
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
import org.jdom2.JDOMException;

/**
 *
 * @author nguyen
 */
@WebServlet(name = "ViewAlbums", urlPatterns = {"/ViewAlbums"})
public class ViewAlbums extends HttpServlet {

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
            out.println("<title>Servlet ViewAlbums</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewAlbums at " + request.getContextPath() + "</h1>");
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
            String userId = request.getParameter("userId");
            String mode = request.getParameter("mode");
            AlbumManager am = new AlbumManager();
            File f = new File(SaveToAlbum.fileUrl);
            String data = "";
            if (f.exists() && !f.isDirectory()) {
                List<Album> lw = am.selectAlbums(f, userId);

                if ("dropdown".equals(mode)) {
                    for (Album temp : lw) {
                        data = "<option value='" + temp.getName() + "'>" + temp.getName() + "</option>"
                                + data;
                    }
                    data = "<button onclick='showDropdown()' id='avc-add-select'> Add To </button>\n"
                            + "<select id='avc-dropdown'  onclick=\"addToAlbum()\">"
                            + data
                            + "<option value='new-album'>New album..</option>\n"
                            + "</select>";
                } else {
                    for (Album temp : lw) {
                        data = "<div class='avc-album-item'>"
                                + "<div class='avc-word-key' id='" + temp.getName() + "' onclick=\"getListWord('" + temp.getName() + "')\">" + temp.getName() + "</div>"
                                + "</div>" + data;
                    }
                }

            } else {
                data = "<button onclick='showDropdown()' id='avc-add-select'> Add To </button>\n"
                        + "<select id='avc-dropdown'  onclick=\"addToAlbum()\">"
                        + "<option value='new-album'>New album..</option>\n"
                        + "</select>";
            }
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(data);
        } catch (JDOMException ex) {
            Logger.getLogger(ViewAlbums.class.getName()).log(Level.SEVERE, null, ex);
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
