package com.example.java.web.heroku.template.web;

import com.example.java.web.heroku.template.domain.SalesforceAccessTokenResponse;
import com.example.java.web.heroku.template.util.SalesforceApiHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Andres Canavesi
 */
@WebServlet(name = "SalesforceOauthServlet", urlPatterns = {"/SalesforceOauthServlet"})
public class SalesforceOauthServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(SalesforceOauthServlet.class.getName());

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

        try {
            LOG.info("Processing oauth request...");
            String error = request.getParameter("error");
            if (error != null) {
                //An error ocurred, among others user maybe denied access (allow / deny dialog)
                String description = request.getParameter("error_description") != null ? request.getParameter("error_description") : "";
                throw new Exception(error + " " + description);
            }

            String code = request.getParameter("code");
            if (code == null) {
                throw new Exception("'Code' parameter is missing");
            }

            /**
             * After getting the code we have to get the access token
             *
             * IMPORTANT: we do not store the access token, we only keep It in
             * memory / session
             */
            SalesforceApiHelper salesforceApiHelper = new SalesforceApiHelper();
            //TODO for now we do not support sandbox orgs
            boolean isSandbox = false;
            salesforceApiHelper.requestAccessToken(null, code, isSandbox);
            LOG.info("We are Salesforce authenticated!");

            //we store salesforceApiHelper instance to use along the web application
            HttpSession httpSession = request.getSession(true);
            httpSession.setMaxInactiveInterval(60 * 60);//one hour
            httpSession.setAttribute("salesforceApiHelper", salesforceApiHelper);
            LOG.info("Oauth request processed successfully");

            response.sendRedirect("salesforce.xhtml");

        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new ServletException("Oauth flow error. " + e.getMessage(), e);
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
