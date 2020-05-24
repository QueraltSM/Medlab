/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.News;

import controllers.FrontCommand;
import ejbs.Log;
import ejbs.NewsFacade;
import entities.News;
import entities.Speciality;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

/**
 *
 * @author QSM
 */
public class SearchNewsCommand extends FrontCommand {
    private NewsFacade newsDB;
    private Log log;

    private void getNewsMatched() {
        try {
            List<News> news;
            String title;
            Speciality speciality;
            if (request.getParameter("keyword_search").isEmpty() && request.getParameter("speciality_search").equals("All specialities")) {
                news = newsDB.findAll();
            } else if (!request.getParameter("keyword_search").isEmpty() && request.getParameter("speciality_search").equals("All specialities")) {
                title = new String(request.getParameter("keyword_search").getBytes("ISO8859_1"), "UTF-8");
                news = newsDB.findNewsbyTitle(title);
            } else if (request.getParameter("keyword_search").isEmpty() && !request.getParameter("speciality_search").equals("All specialities")) {
                speciality = new Speciality(request.getParameter("speciality_search"));
                news = newsDB.findNewsbySpeciality(speciality);
            } else {
                title = new String(request.getParameter("keyword_search").getBytes("ISO8859_1"), "UTF-8");
                speciality = new Speciality(request.getParameter("speciality_search"));
                news = newsDB.findNewsbyTitle_Speciality(title, speciality);
            }
            if (news.isEmpty()) {
                request.setAttribute("error", "There is no news that matches");
            }
            request.setAttribute("all_matched_news", news);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SearchNewsCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void process() {
        try {
            log = (Log) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/Log!ejbs.Log");
            log.newCallEJB("SearchNewsCommand:process()");
            newsDB = (NewsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/NewsFacade!ejbs.NewsFacade");
            getNewsMatched();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("home.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(SearchNewsCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(SearchNewsCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
