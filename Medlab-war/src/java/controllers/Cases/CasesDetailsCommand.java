/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.Cases;

import controllers.FrontCommand;
import ejbs.CommentFacade;
import ejbs.LogFacade;
import ejbs.ClinicalcasesFacade;
import entities.Comment;
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
public class CasesDetailsCommand extends FrontCommand {
    private HttpSession session;
    private ClinicalcasesFacade casesDB;
    private CommentFacade commentsDB;
    private LogFacade log;

    private void getAllComments() {
        long id = Long.parseLong((String) request.getParameter("id"));
        List<Comment> comments = commentsDB.findCommentsbyIdType(id);
        request.setAttribute("comments", comments);
    }

    private void setCasesVisit(Clinicalcases cases) {
        casesDB.updateVisits(cases.getId());
    }
    
    private void getCasesDetails() {
        long id = Long.parseLong((String) request.getParameter("id"));
        Clinicalcases cases = casesDB.find(id);
        request.setAttribute("cases", cases);
        if (request.getParameter("action") == null) {
            setCasesVisit(cases);
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
            log1.setEjbs("CasesDetailsCommand:process()");
            log.create(log1);
            session = request.getSession(true);
            casesDB = (ClinicalcasesFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/ClinicalcasesFacade!ejbs.ClinicalcasesFacade");
            commentsDB = (CommentFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/CommentFacade!ejbs.CommentFacade");
            getCasesDetails();
            getAllComments();
            session.setAttribute("details_command", "CasesDetailsCommand");
            try {
                String dst = "viewCases.jsp";
                if (request.getParameter("action") != null && request.getParameter("action").equals("edit")) {
                   dst = "editCase.jsp";
                }
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(dst);
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(CasesDetailsCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(CasesDetailsCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
