   /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet;

import com.collection.Word;
import com.util.ParseHelper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jsoup.nodes.Document;

/**
 *
 * @author nguyen
 */
@WebServlet(name = "CreateWord", urlPatterns = {"/CreateWord"})
public class CreateWord extends HttpServlet {

    public String url = "http://www.oxforddictionaries.com/definition/english/";
    public String createUrl = "new_album.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        HttpSession session = request.getSession();
        if ( session.getAttribute("_id")!=null) {
             
            String word = request.getParameter("word");
            if (word.trim() != "") {
                //Parse website url
                ParseHelper parser = new ParseHelper();
                String data;
                Document result = parser.getHtmlFromUrl(url + word);
                if (result != null) {
                    Word newWord = parser.getMeaning(result, word);
                    if (newWord != null) {
                        data = "<div id='my-name'>" + newWord.getName() + "</div>"
                                + "<div id='my-type'>" + newWord.getType() + "</div>"
                                + "<div id='my-pronun'>" + newWord.getPronun() + "</div>"
                                + "<hr/><div class='info'>Meaning</div>"
                                + "<div id='my-definition'>" + newWord.getDefinition() + "</div>"
                                + "<hr/><div class='info'>Example</div>"
                                + "<div id='my-example'>" + newWord.getExamples() + "</div>"
                                + "<hr/><div class='info'>Origin</div>"
                                + "<div id='my-origin'>" + newWord.getOrigin() + "</div>";
                    } else {
                        data = "Not Found.";
                    }
                } else {
                    data = "Not Found.";
                }
                //get meaning from result
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(data);

            }
        } else {
            response.sendRedirect("/avocado/login.jsp");
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
