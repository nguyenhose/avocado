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
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.XMLConstants;
import javax.xml.bind.Validator;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

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
        String path = getServletContext().getRealPath("/");
        String xsdPath = path + "common/avcSchema.xsd";

        String type = request.getParameter("type");
        String definition = request.getParameter("definition");
        String name = request.getParameter("name");
        String album = request.getParameter("album");
        String pronun = request.getParameter("pronun");
        String origin = request.getParameter("origin");
        String example = request.getParameter("exam");
        String status = request.getParameter("status");
        HttpSession session = request.getSession();
        String userId = session.getAttribute("_id").toString();

        Word myWord = new Word();
        myWord.setName(name);
        myWord.setDefinition(definition);
        myWord.setType(type);
        myWord.setPronun(pronun);
        myWord.setExamples(example);
        myWord.setOrigin(origin);

        String data = "";
        File f = new File(fileUrl);
        if (f.exists() && !f.isDirectory()) {
            //update word to album
            AlbumManager am = new AlbumManager();
            try {
                if ("newAlbum".equals(status)) {
                    if (am.checkExist(f, album, status, null, userId)) {
                        if (am.addAlbum(myWord, f, userId, album)) {
                            data = "<div class='success'>Add album successful</div>";
                        }
                    } else {
                        data = "<div class='exist'>This album was existed</div>";
                    }
                } else {
                    if (am.checkExist(f, album, status, name, userId)) {
                        if (am.addWord(myWord, f, userId, album)) {
                            data = "<div class='success'>Add word successful</div>";
                        }
                    } else {
                        data = "<div class='exist'>This word was existed in album</div>";
                    }
                }
            } catch (JDOMException ex) {
                Logger.getLogger(SaveToAlbum.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                //create new albums
                ParseHelper parser = new ParseHelper();
                boolean result = parser.initalFile(myWord, album, userId, f);
                //validate  by schema
                Source schemaFile = new StreamSource(xsdPath);
                Source xmlFile = new StreamSource(new File(f.getPath()));
                SchemaFactory schemaFactory = SchemaFactory
                        .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Schema schema = schemaFactory.newSchema(schemaFile);
                javax.xml.validation.Validator validator = schema.newValidator();
                try {
                    validator.validate(xmlFile);
                    System.out.println(xmlFile.getSystemId() + " is valid");
                } catch (SAXException e) {
                    System.out.println(xmlFile.getSystemId() + " ");
                    System.out.println("Reason: " + e.getLocalizedMessage());
                }
                if (result) {
                    data = "<div class='success'>Add album and first word successful</div>";
                } else {
                    data = "<div class='success'>Something went wrong..try later.</div>";
                }
            } catch (SAXException ex) {
                Logger.getLogger(SaveToAlbum.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(data);

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
