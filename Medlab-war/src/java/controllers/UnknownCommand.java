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

/**
 *
 * @author QSM
 */
public class UnknownCommand extends FrontCommand {
    private Log log;
    
    @Override
    public void process() {
        try {
            log = (Log) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/Log!ejbs.Log");
            log.newCallEJB("UnknownCommand:process()");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("unknown.jsp");
            requestDispatcher.forward(request, response);
        } catch (ServletException | IOException | NamingException ex) {
            Logger.getLogger(UnknownCommand.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
