/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet;

import com.util.ParseHelper;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.System.out;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

/**
 *
 * @author nguyen
 */
@WebServlet(name = "PDFExport", urlPatterns = {"/PDFExport"})
public class PDFExport extends HttpServlet {

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
       
        try {
            String path = getServletContext().getRealPath("/");
            String xslPath = path + "common/avcXSL.xsl";
            String xmlPath = path + "common/file.xml";
            String pdfPath = "/home/nguyen/Desktop/mycard.pdf";

//            ParseHelper ph = new ParseHelper();
//            ph.XSLConverter(xslPath, xmlPath, foPath, path);
//            File file = new File(foPath);
            File fo = new File(xslPath);
            StreamSource source = new StreamSource(xmlPath);
            StreamSource transformSource = new StreamSource(fo);
//            
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();

            FopFactory ff = FopFactory.newInstance();
            FOUserAgent fua = ff.newFOUserAgent();

            Fop fop = ff.newFop(MimeConstants.MIME_PDF, fua, outStream);
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer trans = tff.newTransformer(transformSource);

            Result result = new SAXResult(fop.getDefaultHandler());
            trans.transform(source, result);

            File pdffile = new File(pdfPath);
            OutputStream out = new FileOutputStream(pdffile);
            out = new BufferedOutputStream(out);
            FileOutputStream str = new FileOutputStream(pdffile);
            str.write(outStream.toByteArray());
            str.close();
            out.close();

            byte[] content = outStream.toByteArray();

            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition",
                    "attachment;filename=mycard.pdf");
            response.setContentLength(content.length);
            response.getOutputStream().write(content);
            response.getOutputStream().flush();
        } catch (FOPException ex) {
            Logger.getLogger(PDFExport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(PDFExport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(PDFExport.class.getName()).log(Level.SEVERE, null, ex);
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
