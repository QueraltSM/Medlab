/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.Researches;

import controllers.FrontCommand;
import ejbs.CommentFacade;
import ejbs.LogFacade;
import ejbs.ResearchesFacade;
import entities.Comment;
import entities.Log;
import entities.Researches;
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
public class ResearchesDetailsCommand extends FrontCommand {
    private HttpSession session;
    private ResearchesFacade researchesDB;
    private CommentFacade commentsDB;
    private LogFacade log;

    private void getAllComments() {
        long id = Long.parseLong((String) request.getParameter("id"));
        String type = request.getParameter("type");
        List<Comment> comments = commentsDB.orderbyRecent(id, type);
        request.setAttribute("comments", comments);
    }

    private void setResearchesVisit(Researches researches) {
        researchesDB.updateVisits(researches.getId());
    }
    
    private void getResearchesDetails() {
        long id = Long.parseLong((String) request.getParameter("id"));
        Researches researches = researchesDB.find(id);
        request.setAttribute("researches", researches);
        if (request.getParameter("action") == null) {
            setResearchesVisit(researches);
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
            log1.setEjbs("ResearchesDetailsCommand:process()");
            log.create(log1);
            session = request.getSession(true);
            researchesDB = (ResearchesFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/ResearchesFacade!ejbs.ResearchesFacade");
            commentsDB = (CommentFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/CommentFacade!ejbs.CommentFacade");
            getResearchesDetails();
            getAllComments();
            session.setAttribute("details_command", "ResearchesDetailsCommand");
            try {
                String dst = "viewResearch.jsp";
                if (request.getParameter("action") != null && request.getParameter("action").equals("edit")) {
                   dst = "editResearch.jsp";
                }
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(dst);
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(ResearchesDetailsCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(ResearchesDetailsCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
