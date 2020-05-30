/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.Discussions;

import controllers.FrontCommand;
import ejbs.LogFacade;
import ejbs.DiscussionsFacade;
import entities.Log;
import entities.Discussions;
import java.io.IOException;
import java.util.Date;
import java.util.List;
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
public class ShowDiscussionsCommand extends FrontCommand {
    private HttpSession session;
    private DiscussionsFacade discussionsDB;
    private LogFacade log;

    public void getSortedDiscussions() {
        List<Discussions> discussions = discussionsDB.orderbyViews();
        if (discussions.isEmpty()) {
            request.setAttribute("error", "There is no most viewed discussions yet");
        }
        request.setAttribute("all_sorted_discussions", discussions);
    }
        
    private void getAllDiscussions() {
        List<Discussions> discussions = discussionsDB.orderbyRecent();
        if (discussions.isEmpty()) {
            request.setAttribute("error", "There is no discussions yet");
        }
        request.setAttribute("all_discussions", discussions);
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
            log1.setEjbs("ShowDiscussionsCommand:process()");
            log.create(log1);
            session = request.getSession();
            discussionsDB = (DiscussionsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/DiscussionsFacade!ejbs.DiscussionsFacade");
            getAllDiscussions();
            
            if (request.getParameter("sort") != null && request.getParameter("sort").equals("viewed")) {
                getSortedDiscussions();
            }
            
            session.setAttribute("search_command", "SearchDiscussionsCommand");
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("discussions.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(ShowDiscussionsCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(ShowDiscussionsCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
