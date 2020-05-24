/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import ejbs.Log;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author QSM
 */
public class LogoutCommand extends FrontCommand {

    private static HttpSession session;
    private Log log;

    @Override
    public void process() {
        try {
            log = (Log) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/Log!ejbs.Log");
            log.newCallEJB("LogoutCommand:process()");
            session = request.getSession(true);
            session.setAttribute("logged", "false");
            session.setAttribute("fullname", null);
            session.setAttribute("email", null);
            session.setAttribute("usertype", null);
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(LogoutCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(LogoutCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
