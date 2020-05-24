/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.News;

import controllers.FrontCommand;
import ejbs.Log;
import ejbs.NewsFacade;
import ejbs.NewsVisitorCounter;
import entities.News;
import java.io.IOException;
import java.util.ArrayList;
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
public class ShowNewsCommand extends FrontCommand {

    private HttpSession session;
    private NewsFacade newsDB;
    private NewsVisitorCounter visitor;
    private Log log;

    public void getSortedNews() {
        List<News> news = new ArrayList();
        visitor.sortMap().entrySet().stream().forEach((entry) -> {
            newsDB.findAll().stream().filter((n) -> (n.getId().equals(entry.getKey().getId()))).forEach((n) -> {
                news.add(n);
            });
        });
        if (news.isEmpty()) {
            request.setAttribute("error", "There is no most viewed news yet");
        }
        request.setAttribute("all_sorted_news", news);
    }
        
    private void getAllNews() {
        List<News> news = newsDB.findAll();
        if (news.isEmpty()) {
            request.setAttribute("error", "There is no news yet");
        }
        request.setAttribute("all_news", news);
    }

    @Override
    public void process() {
        try {
            log = (Log) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/Log!ejbs.Log");
            log.newCallEJB("ShowNewsCommand:process()");
            session = request.getSession();
            visitor = (NewsVisitorCounter) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/NewsVisitorCounter!ejbs.NewsVisitorCounter");
            newsDB = (NewsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/NewsFacade!ejbs.NewsFacade");
            getAllNews();
            
            if (request.getParameter("sort") != null && request.getParameter("sort").equals("viewed")) {
                getSortedNews();
            }
            
            session.setAttribute("search_command", "SearchNewsCommand");
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("home.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(ShowNewsCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(ShowNewsCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
