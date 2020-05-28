/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.Cases;

import controllers.FrontCommand;
import ejbs.LogFacade;
import ejbs.ClinicalcasesFacade;
import ejbs.UsersFacade;
import entities.Log;
import entities.Users;
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
public class MyCasesCommand extends FrontCommand {
    private HttpSession session;
    private ClinicalcasesFacade casesDB;
    private LogFacade log;
    private UsersFacade usersDB;
     
    private void getUserCases() {
        long userID = Long.parseLong(String.valueOf(session.getAttribute("userID")));
        Users user_logged = usersDB.findUserbyID(userID).get(0);
        request.setAttribute("cases", casesDB.findCasesbyAuthor(user_logged));
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
            log1.setEjbs("MyCasesCommand:process()");
            log.create(log1);
            session = request.getSession(true);
            casesDB = (ClinicalcasesFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/ClinicalcasesFacade!ejbs.ClinicalcasesFacade");
            usersDB = (UsersFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/UsersFacade!ejbs.UsersFacade");
            getUserCases();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("myCases.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(MyCasesCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(MyCasesCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
