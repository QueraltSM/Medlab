/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.News;

import controllers.FrontCommand;
import ejbs.LogFacade;
import ejbs.NewsFacade;
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
public class ShowNewsCommand extends FrontCommand {
    private HttpSession session;
    private NewsFacade newsDB;
    private LogFacade log;

    public void getSortedNews() {
        List<News> news = newsDB.orderbyViews();
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
            log = (LogFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/LogFacade!ejbs.LogFacade");
            Log log1 = new Log();
            long id = 1;
            if (!log.findAll().isEmpty()) {
               id = log.findAll().size()+1;
            }
            log1.setId(id);
            log1.setEjbs("ShowNewsCommand:process()");
            log.create(log1);
            session = request.getSession();
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
