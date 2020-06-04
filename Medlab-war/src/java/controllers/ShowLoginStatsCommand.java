/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import ejbs.LogFacade;
import ejbs.LoginstatsFacade;
import entities.Log;
import java.io.IOException;
import java.util.Date;
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
    private LogFacade log;

    private void getLoginstats() {
        request.setAttribute("LoginStats", loginStatsDB.findAll());
    }
    
    @Override
    public void process() {
        try {
            log = (LogFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/LogFacade!ejbs.LogFacade");
            Log log1 = new Log();
            long id = 1;
            if (!log.findAll().isEmpty()) {
               id = log.findAll().size()+1;
            }
            log1.setId(id);
            log1.setDate(new Date());
            log1.setEjbs("LoginstatsFacade:process()");
            log.create(log1);
            loginStatsDB = (LoginstatsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/LoginstatsFacade!ejbs.LoginstatsFacade");
            getLoginstats();
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