/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.Discussions;

import controllers.FrontCommand;
import ejbs.CommentFacade;
import ejbs.LogFacade;
import ejbs.DiscussionsFacade;
import entities.Comment;
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
public class DiscussionsDetailsCommand extends FrontCommand {
    private HttpSession session;
    private DiscussionsFacade discussionsDB;
    private CommentFacade commentsDB;
    private LogFacade log;

    private void getAllComments() {
        long id = Long.parseLong((String) request.getParameter("id"));
        List<Comment> comments = commentsDB.orderbyRecent(id);
        request.setAttribute("comments", comments);
    }

    private void setDiscussionsVisit(Discussions discussions) {
        discussionsDB.updateVisits(discussions.getId());
    }
    
    private void getDiscussionsDetails() {
        long id = Long.parseLong((String) request.getParameter("id"));
        Discussions discussions = discussionsDB.find(id);
        request.setAttribute("discussions", discussions);
        if (request.getParameter("action") == null) {
            setDiscussionsVisit(discussions);
        }
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
            log1.setEjbs("DiscussionsDetailsCommand:process()");
            log.create(log1);
            session = request.getSession();
            discussionsDB = (DiscussionsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/DiscussionsFacade!ejbs.DiscussionsFacade");
            commentsDB = (CommentFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/CommentFacade!ejbs.CommentFacade");
            getDiscussionsDetails();
            getAllComments();
            session.setAttribute("details_command", "DiscussionsDetailsCommand");
            try {
                String dst = "viewDiscussions.jsp";
                if (request.getParameter("action") != null && request.getParameter("action").equals("edit")) {
                   dst = "editDiscussion.jsp";
                }
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(dst);
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(DiscussionsDetailsCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(DiscussionsDetailsCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
