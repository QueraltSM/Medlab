/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import ejbs.LogFacade;
import ejbs.LoginstatsFacade;
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
public class ShowLoginStatsCommand extends FrontCommand {
    private LoginstatsFacade loginStatsDB;

    private void getLog() {
        request.setAttribute("LoginStats", loginStatsDB.findAll());
    }
    
    @Override
    public void process() {
        try {
            loginStatsDB = (LoginstatsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/LoginstatsFacade!ejbs.LoginstatsFacade");
            getLog();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("loginStats.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(ShowLogCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(ShowLoginStatsCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}