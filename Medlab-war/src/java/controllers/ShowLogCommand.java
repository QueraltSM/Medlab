/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import ejbs.LogFacade;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

/**
 *
 * @author QSM
 */
public class ShowLogCommand extends FrontCommand {
    private LogFacade logDB;

    private void getLog() {
        request.setAttribute("Log", logDB.findAll());
    }
    
    @Override
    public void process() {
        try {
            logDB = (LogFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/LogFacade!ejbs.LogFacade");
            getLog();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("log.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(ShowLogCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(ShowLogCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}