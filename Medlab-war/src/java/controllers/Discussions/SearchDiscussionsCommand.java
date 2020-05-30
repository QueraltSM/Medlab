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
import entities.Speciality;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
public class SearchDiscussionsCommand extends FrontCommand {
    private DiscussionsFacade discussionsDB;
    private LogFacade log;

    private void getDiscussionsMatched() {
        try {
            List<Discussions> discussions;
            String title;
            Speciality speciality;
            if (request.getParameter("keyword_search").isEmpty() && request.getParameter("speciality_search").equals("All specialities")) {
                discussions = discussionsDB.findAll();
            } else if (!request.getParameter("keyword_search").isEmpty() && request.getParameter("speciality_search").equals("All specialities")) {
                title = new String(request.getParameter("keyword_search").getBytes("ISO8859_1"), "UTF-8");
                discussions = discussionsDB.findDiscussionsbyTitle(title);
            } else if (request.getParameter("keyword_search").isEmpty() && !request.getParameter("speciality_search").equals("All specialities")) {
                speciality = new Speciality(request.getParameter("speciality_search"));
                discussions = discussionsDB.findDiscussionsbySpeciality(speciality);
            } else {
                title = new String(request.getParameter("keyword_search").getBytes("ISO8859_1"), "UTF-8");
                speciality = new Speciality(request.getParameter("speciality_search"));
                discussions = discussionsDB.findDiscussionsbyTitle_Speciality(title, speciality);
            }
            if (discussions.isEmpty()) {
                request.setAttribute("error", "There is no discussions that matches");
            }
            request.setAttribute("all_matched_discussions", discussions);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SearchDiscussionsCommand.class.getName()).log(Level.SEVERE, null, ex);
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
            log1.setEjbs("SearchDiscussionsCommand:process()");
            log.create(log1);
            discussionsDB = (DiscussionsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/DiscussionsFacade!ejbs.DiscussionsFacade");
            getDiscussionsMatched();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("discussions.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(SearchDiscussionsCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(SearchDiscussionsCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
