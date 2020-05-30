/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.Discussions;

import controllers.FrontCommand;
import ejbs.LogFacade;
import ejbs.DiscussionsFacade;
import ejbs.UsersFacade;
import entities.Log;
import entities.Users;
import java.io.IOException;
import java.util.Date;
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
public class MyDiscussionsCommand extends FrontCommand {
    private HttpSession session;
    private DiscussionsFacade discussionsDB;
    private LogFacade log;
    private UsersFacade usersDB;
    
    private void getUserDiscussions() {
        long userID = Long.parseLong(String.valueOf(session.getAttribute("userID")));
        Users user_logged = usersDB.findUserbyID(userID).get(0);
        request.setAttribute("discussions", discussionsDB.findDiscussionsbyAuthor(user_logged));
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
            log1.setEjbs("MyDiscussionsCommand:process()");
            log.create(log1);
            session = request.getSession(true);
            discussionsDB = (DiscussionsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/DiscussionsFacade!ejbs.DiscussionsFacade");
            usersDB = (UsersFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/UsersFacade!ejbs.UsersFacade");
            getUserDiscussions();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("myDiscussions.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(MyDiscussionsCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(MyDiscussionsCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
