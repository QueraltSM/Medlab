/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.News;

import controllers.FrontCommand;
import ejbs.CommentFacade;
import ejbs.LogFacade;
import ejbs.NewsFacade;
import entities.Comment;
import entities.Log;
import entities.News;
import java.io.IOException;
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
public class NewsDetailsCommand extends FrontCommand {
    private HttpSession session;
    private NewsFacade newsDB;
    private CommentFacade commentsDB;
    private LogFacade log;

    private void getAllComments() {
        long id = Long.parseLong((String) request.getParameter("id"));
        List<Comment> comments = commentsDB.findCommentsbyIdType(id);
        request.setAttribute("comments", comments);
    }

    private void setNewsVisit(News news) {
        newsDB.insertNewVisit(news.getId());
    }
    
    private void getNewsDetails() {
        long id = Long.parseLong((String) request.getParameter("id"));
        News news = newsDB.find(id);
        request.setAttribute("news", news);
        if (request.getParameter("action") == null) {
            setNewsVisit(news);
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
            log1.setEjbs("NewsDetailsCommand:process()");
            log.create(log1);
            session = request.getSession();
            newsDB = (NewsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/NewsFacade!ejbs.NewsFacade");
            commentsDB = (CommentFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/CommentFacade!ejbs.CommentFacade");
            getNewsDetails();
            getAllComments();
            session.setAttribute("details_command", "NewsDetailsCommand");
            try {
                String dst = "viewNews.jsp";
                if (request.getParameter("action") != null && request.getParameter("action").equals("edit")) {
                   dst = "editNews.jsp";
                }
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(dst);
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(NewsDetailsCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(NewsDetailsCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
