/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import controllers.FrontCommand;
import controllers.UnknownCommand;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author QSM
 */
@WebServlet(urlPatterns = {"/FrontController"})
public class FrontController extends HttpServlet {

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
    }

       
    private FrontCommand getCommand(HttpServletRequest req){
        try {
            FrontCommand f=(FrontCommand) getCommandClass(req).newInstance();
            return f;
        }catch (InstantiationException | IllegalAccessException e) {
            System.out.println("Application exception");
        }
        return null;
    }
    
    private Class getCommandClass(HttpServletRequest req){
    Class result;
        String command = "controllers.";
        if (((String)req.getParameter("command")).contains("News")) {
            command += "News.";
        } else if (((String)req.getParameter("command")).contains("Cases")) {
            command += "Cases.";
        } else if (((String)req.getParameter("command")).contains("Discussions")) {
            command += "Discussions.";
        } else if (((String)req.getParameter("command")).contains("Researches")) {
            command += "Researches.";
        } else if (((String)req.getParameter("command")).contains("Comment")) {
            command += "Comments.";
        }
        
        command += (String)req.getParameter("command");
        try {
            if (command.contains("LoginCommand") || command.contains("SignupCommand") || (req.getSession().getAttribute("logged") != null && req.getSession().getAttribute("logged").equals("true"))) {
                result = Class.forName(command);
            } else {
                req.setAttribute("unknown_error", "The requested resource is not available");
                result = UnknownCommand.class;
            }
        }
        catch(ClassNotFoundException e) {
            req.setAttribute("unknown_error", "The requested resource is not available");
            result = UnknownCommand.class;
        }
        return result;
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
        FrontCommand command = getCommand(request);
        command.init(getServletContext(), request, response);
        command.process(); 
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
