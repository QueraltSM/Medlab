/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.News;

import controllers.FrontCommand;
import ejbs.CommentFacade;
import ejbs.Log;
import ejbs.NewsFacade;
import ejbs.NewsVisitorCounter;
import entities.Comment;
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
    private NewsVisitorCounter visitor;
    private Log log;

    private void getAllComments() {
        long id = Long.parseLong((String) request.getParameter("id"));
        List<Comment> comments = commentsDB.findCommentsbyIdType(id);
        request.setAttribute("comments", comments);
    }

    private void setNewVisit(News news) {
        try {
            visitor = (NewsVisitorCounter)InitialContext.doLookup("java:global/Medlab/Medlab-ejb/NewsVisitorCounter!ejbs.NewsVisitorCounter");
            visitor.newVisit(news);
        } catch (NamingException ex) {
            Logger.getLogger(NewsDetailsCommand.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    private void getNewsDetails() {
        long id = Long.parseLong((String) request.getParameter("id"));
        News news = newsDB.find(id);
        request.setAttribute("news", news);
        setNewVisit(news);
    }

    @Override
    public void process() {
        try {
            log = (Log) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/Log!ejbs.Log");
            log.newCallEJB("NewsDetailsCommand:process()");
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
