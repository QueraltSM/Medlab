/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.Cases;

import controllers.FrontCommand;
import ejbs.LogFacade;
import ejbs.ClinicalcasesFacade;
import entities.Log;
import entities.Clinicalcases;
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
public class SearchCasesCommand extends FrontCommand {
    private ClinicalcasesFacade casesDB;
    private LogFacade log;

    private void getClinicalcasesMatched() {
        try {
            List<Clinicalcases> cases;
            String title;
            Speciality speciality;
            if (request.getParameter("keyword_search").isEmpty() && request.getParameter("speciality_search").equals("All specialities")) {
                cases = casesDB.findAll();
            } else if (!request.getParameter("keyword_search").isEmpty() && request.getParameter("speciality_search").equals("All specialities")) {
                title = new String(request.getParameter("keyword_search").getBytes("ISO8859_1"), "UTF-8");
                cases = casesDB.findCasesbyTitle(title);
            } else if (request.getParameter("keyword_search").isEmpty() && !request.getParameter("speciality_search").equals("All specialities")) {
                speciality = new Speciality(request.getParameter("speciality_search"));
                cases = casesDB.findCasesbySpeciality(speciality);
            } else {
                title = new String(request.getParameter("keyword_search").getBytes("ISO8859_1"), "UTF-8");
                speciality = new Speciality(request.getParameter("speciality_search"));
                cases = casesDB.findCasesbyTitle_Speciality(title, speciality);
            }
            if (cases.isEmpty()) {
                request.setAttribute("error", "There is no cases that matches");
            }
            request.setAttribute("all_matched_cases", cases);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SearchCasesCommand.class.getName()).log(Level.SEVERE, null, ex);
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
            log1.setEjbs("SearchCasesCommand:process()");
            log.create(log1);
            casesDB = (ClinicalcasesFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/ClinicalcasesFacade!ejbs.ClinicalcasesFacade");
            getClinicalcasesMatched();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("cases.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(SearchCasesCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(SearchCasesCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
