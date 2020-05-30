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
import entities.Discussions;
import entities.Speciality;
import entities.Users;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
public class AddDiscussionsCommand extends FrontCommand {
    private LogFacade log;
    private DiscussionsFacade discussionsDB;
    private UsersFacade usersDB;
    private HttpSession session;
    
    private void createDiscussion() {
        try {
            String title = new String(request.getParameter("title").getBytes("ISO8859_1"), "UTF-8");
            String description = new String(request.getParameter("description").getBytes("ISO8859_1"), "UTF-8");
            Discussions discussions = new Discussions();
            long userID = Long.parseLong(String.valueOf(session.getAttribute("userID")));
            Users user_logged = usersDB.findUserbyID(userID).get(0);
            long id = 0;
            if (!discussionsDB.findAll().isEmpty()) id = discussionsDB.findAll().get(discussionsDB.count()-1).getId()+1;
            discussions.setId(id);
            discussions.setViews(0);
            discussions.setAuthor(user_logged);
            discussions.setTitle(title);
            discussions.setDescription(description);
            discussions.setSpeciality(new Speciality(request.getParameter("speciality")));
            discussions.setDate(new Date());
            discussionsDB.insertDiscussion(discussions);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AddDiscussionsCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void process() {
        try {
            session = request.getSession(true);
            log = (LogFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/LogFacade!ejbs.LogFacade");
            Log log1 = new Log();
            long id = 1;
            if (!log.findAll().isEmpty()) {
               id = log.findAll().size()+1;
            }
            log1.setId(id);
            log1.setDate(new Date());
            log1.setEjbs("AddDiscussionsCommand:process()");
            log.create(log1);
            discussionsDB = (DiscussionsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/DiscussionsFacade!ejbs.DiscussionsFacade");
            usersDB = (UsersFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/UsersFacade!ejbs.UsersFacade");
            createDiscussion();
            ShowDiscussionsCommand command = new ShowDiscussionsCommand();
            command.init(context, request, response);
            command.process();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewDiscussions.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(AddDiscussionsCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(AddDiscussionsCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
