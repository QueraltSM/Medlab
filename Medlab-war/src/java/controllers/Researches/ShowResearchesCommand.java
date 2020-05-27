/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.Researches;

import controllers.FrontCommand;
import ejbs.LogFacade;
import ejbs.ResearchesFacade;
import entities.Log;
import entities.Researches;
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
public class ShowResearchesCommand extends FrontCommand {
    private HttpSession session;
    private ResearchesFacade researchesDB;
    private LogFacade log;

    public void getSortedResearches() {
        List<Researches> researches = researchesDB.orderbyViews();
        if (researches.isEmpty()) {
            request.setAttribute("error", "There is no most viewed researches yet");
        }
        request.setAttribute("all_sorted_researches", researches);
    }
        
    private void getAllResearches() {
        List<Researches> researches = researchesDB.findAll();
        if (researches.isEmpty()) {
            request.setAttribute("error", "There is no researches yet");
        }
        request.setAttribute("all_researches", researches);
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
            log1.setEjbs("ShowResearchesCommand:process()");
            log.create(log1);
            session = request.getSession();
            researchesDB = (ResearchesFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/ResearchesFacade!ejbs.ResearchesFacade");
            getAllResearches();
            
            if (request.getParameter("sort") != null && request.getParameter("sort").equals("viewed")) {
                getSortedResearches();
            }
            
            session.setAttribute("search_command", "SearchResearchesCommand");
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("researches.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(ShowResearchesCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(ShowResearchesCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
