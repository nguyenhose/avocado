/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet;

import com.collection.Word;
import com.util.AlbumManager;
import com.util.ParseHelper;
import java.io.File;
import java.io.IOException;
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
@WebServlet(name = "SaveToAlbum", urlPatterns = {"/SaveToAlbum"})
public class SaveToAlbum extends HttpServlet {

    public static String fileUrl = "/home/nguyen/Desktop/file.xml";

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

        String type = request.getParameter("type");
        String definition = request.getParameter("definition");
        String name = request.getParameter("name");
        String album = request.getParameter("album");
        String status = request.getParameter("status");

        Word myWord = new Word();
        myWord.setName(name);
        myWord.setDefinition(definition);
        myWord.setType(type);

        File f = new File(fileUrl);
        if (f.exists() && !f.isDirectory()) {
            //update word to album
            AlbumManager am = new AlbumManager();
            try {
                String data="";
                if ("newAlbum".equals(status)) {
                    if (am.checkExist(f, name, status, null)) {
                        am.addAlbum(myWord, f, "2", album);
                        data = "<div>Add album successful</div>";
                    } else {
                        data = "<div>This album was existed</div>";
                    }
                } else {
                    if (am.checkExist(f, album, status, name)) {
                        am.addWord(myWord, f, "2", album);
                        data = "<div>Add word successful</div>";
                    } else {
                         data = "<div>This word was existed in album</div>";
                    }
                }
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(data);
            } catch (JDOMException ex) {
                Logger.getLogger(SaveToAlbum.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //create new albums 
            ParseHelper parser = new ParseHelper();
            parser.initalFile(myWord, f);
        }

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
