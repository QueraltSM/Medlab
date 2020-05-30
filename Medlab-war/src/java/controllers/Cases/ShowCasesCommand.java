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
import java.io.IOException;
import java.util.Date;
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
public class ShowCasesCommand extends FrontCommand {
    private HttpSession session;
    private ClinicalcasesFacade casesDB;
    private LogFacade log;

    public void getSortedCases() {
        List<Clinicalcases> cases = casesDB.orderbyViews();
        if (cases.isEmpty()) {
            request.setAttribute("error", "There is no most viewed cases yet");
        }
        request.setAttribute("all_sorted_cases", cases);
    }
        
    private void getAllCases() {
        List<Clinicalcases> cases = casesDB.orderbyRecent();
        if (cases.isEmpty()) {
            request.setAttribute("error", "There is no cases yet");
        }
        request.setAttribute("all_cases", cases);
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
            log1.setEjbs("ShowCasesCommand:process()");
            log.create(log1);
            session = request.getSession();
            casesDB = (ClinicalcasesFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/ClinicalcasesFacade!ejbs.ClinicalcasesFacade");
            getAllCases();
            
            if (request.getParameter("sort") != null && request.getParameter("sort").equals("viewed")) {
                getSortedCases();
            }
            
            session.setAttribute("search_command", "SearchCasesCommand");
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("cases.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(ShowCasesCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(ShowCasesCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
