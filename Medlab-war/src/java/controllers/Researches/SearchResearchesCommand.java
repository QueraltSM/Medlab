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
public class SearchResearchesCommand extends FrontCommand {
    private ResearchesFacade researchesDB;
    private LogFacade log;

    private void getResearchesMatched() {
        try {
            List<Researches> researches;
            String title;
            Speciality speciality;
            if (request.getParameter("keyword_search").isEmpty() && request.getParameter("speciality_search").equals("All specialities")) {
                researches = researchesDB.findAll();
            } else if (!request.getParameter("keyword_search").isEmpty() && request.getParameter("speciality_search").equals("All specialities")) {
                title = new String(request.getParameter("keyword_search").getBytes("ISO8859_1"), "UTF-8");
                researches = researchesDB.findResearchesbyTitle(title);
            } else if (request.getParameter("keyword_search").isEmpty() && !request.getParameter("speciality_search").equals("All specialities")) {
                speciality = new Speciality(request.getParameter("speciality_search"));
                researches = researchesDB.findResearchesbySpeciality(speciality);
            } else {
                title = new String(request.getParameter("keyword_search").getBytes("ISO8859_1"), "UTF-8");
                speciality = new Speciality(request.getParameter("speciality_search"));
                researches = researchesDB.findResearchesbyTitle_Speciality(title, speciality);
            }
            if (researches.isEmpty()) {
                request.setAttribute("error", "There is no researches that matches");
            }
            request.setAttribute("all_matched_researches", researches);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SearchResearchesCommand.class.getName()).log(Level.SEVERE, null, ex);
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
            log1.setEjbs("SearchResearchesCommand:process()");
            log.create(log1);
            researchesDB = (ResearchesFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/ResearchesFacade!ejbs.ResearchesFacade");
            getResearchesMatched();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("researches.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(SearchResearchesCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(SearchResearchesCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
