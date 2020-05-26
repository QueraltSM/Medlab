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
import java.util.Date;
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
public class ModifyCasesCommand extends FrontCommand {
    private LogFacade log;
    private ClinicalcasesFacade casesDB;
    private HttpSession session;
    
    private void modifyClinicalcases() {
        try {
            String title = new String(request.getParameter("title").getBytes("ISO8859_1"), "UTF-8");
            String description = new String(request.getParameter("description").getBytes("ISO8859_1"), "UTF-8");
            String examination = new String(request.getParameter("examination").getBytes("ISO8859_1"), "UTF-8");
            String questions = new String(request.getParameter("questions").getBytes("ISO8859_1"), "UTF-8");
            String history = new String(request.getParameter("history").getBytes("ISO8859_1"), "UTF-8");
            long id = Long.parseLong((String) request.getParameter("id"));
            Clinicalcases cases = casesDB.find(id);
            cases.setTitle(title);
            cases.setDescription(description);
            cases.setSpeciality(new Speciality(request.getParameter("speciality")));
            cases.setDate(new Date());
            cases.setAuthor((String)session.getAttribute("email"));
            cases.setExamination(examination);
            cases.setQuestions(questions);
            cases.setHistory(history);
            casesDB.updateCase(cases);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ModifyCasesCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void process() {
        try {
            session = request.getSession();
            log = (LogFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/LogFacade!ejbs.LogFacade");
            Log log1 = new Log();
            long id = 1;
            if (!log.findAll().isEmpty()) {
               id = log.findAll().size()+1;
            }
            log1.setId(id);
            log1.setEjbs("ModifyCasesCommand:process()");
            log.create(log1);
            casesDB = (ClinicalcasesFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/ClinicalcasesFacade!ejbs.ClinicalcasesFacade");
            modifyClinicalcases();
            CasesDetailsCommand command = new CasesDetailsCommand();
            command.init(context, request, response);
            command.process();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewCases.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(ModifyCasesCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(ModifyCasesCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
