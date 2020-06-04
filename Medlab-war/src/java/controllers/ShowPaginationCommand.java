/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import ejbs.ClinicalcasesFacade;
import ejbs.DiscussionsFacade;
import ejbs.LogFacade;
import ejbs.NewsFacade;
import entities.Clinicalcases;
import entities.Discussions;
import entities.Log;
import entities.News;
import java.io.IOException;
import java.util.Date;
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
public class ShowPaginationCommand extends FrontCommand {
    private LogFacade log;
    private NewsFacade newsDB;
    private ClinicalcasesFacade casesDB;
    private DiscussionsFacade discussionsDB;

    private void showDiscussionsFacade(int page_number) {
        int max_page_number = 0;
        List<Discussions> discussions = discussionsDB.findByPagination(page_number);
        for (int i = 0; i<discussionsDB.findAll().size(); i++) {
            if (i%5==0) {
                max_page_number++;
            }
        }
        request.setAttribute("discussions", discussions);
        request.setAttribute("max_page_number", max_page_number);
    }
        
    private void showCasesFacade(int page_number) {
        int max_page_number = 0;
        List<Clinicalcases> cases = casesDB.findByPagination(page_number);
        for (int i = 0; i<casesDB.findAll().size(); i++) {
            if (i%5==0) {
                max_page_number++;
            }
        }
        request.setAttribute("cases", cases);
        request.setAttribute("max_page_number", max_page_number);
    }
    
    private void showNewsFacade(int page_number) {
        int max_page_number = 0;
        List<News> news = newsDB.findByPagination(page_number);
        for (int i = 0; i<newsDB.findAll().size(); i++) {
            if (i%5==0) {
                max_page_number++;
            }
        }
        request.setAttribute("news", news);
        request.setAttribute("max_page_number", max_page_number);
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
            log1.setEjbs("ShowPaginationCommand:process()");
            log.create(log1);
            newsDB = (NewsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/NewsFacade!ejbs.NewsFacade");
            casesDB = (ClinicalcasesFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/ClinicalcasesFacade!ejbs.ClinicalcasesFacade");
            discussionsDB = (DiscussionsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/DiscussionsFacade!ejbs.DiscussionsFacade");
            int page_number = 1;
            if (request.getParameter("page_number") != null) {
                page_number = Integer.parseInt(request.getParameter("page_number"));
            }
            
            if (request.getParameter("entity") == null) {
                showNewsFacade(page_number);
            } else {
                switch(request.getParameter("entity")) {
                    case "cases":
                        showCasesFacade(page_number);
                        break;
                    case "discussions":
                        showDiscussionsFacade(page_number);
                        break;
                    default:
                        showNewsFacade(page_number);
                        break;
                }
            }
            
            request.setAttribute("page_number", page_number);
        
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(request.getParameter("entity") + "_pagination.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(ShowPaginationCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(ShowPaginationCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}