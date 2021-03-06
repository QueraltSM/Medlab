/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.Cases;

import controllers.FrontCommand;
import ejbs.LogFacade;
import ejbs.ClinicalcasesFacade;
import ejbs.UsersFacade;
import entities.Log;
import entities.Clinicalcases;
import entities.Speciality;
import entities.Users;
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
public class AddCasesCommand extends FrontCommand {
    private LogFacade log;
    private ClinicalcasesFacade casesDB;
    private HttpSession session;
    private UsersFacade usersDB;
    
    private void createCase() {
        try {
            String title = new String(request.getParameter("title").getBytes("ISO8859_1"), "UTF-8");
            String description = new String(request.getParameter("description").getBytes("ISO8859_1"), "UTF-8");
            String examination = new String(request.getParameter("examination").getBytes("ISO8859_1"), "UTF-8");
            String questions = new String(request.getParameter("questions").getBytes("ISO8859_1"), "UTF-8");
            String history = new String(request.getParameter("history").getBytes("ISO8859_1"), "UTF-8");
            Clinicalcases cases = new Clinicalcases();
            long id = 0;
            if (!casesDB.findAll().isEmpty()) id = casesDB.findAll().get(casesDB.count()-1).getId()+1;
            cases.setId(id);
            cases.setViews(0);
            cases.setTitle(title);
            cases.setDescription(description);
            cases.setSpeciality(new Speciality(request.getParameter("speciality")));
            cases.setDate(new Date());
            long userID = Long.parseLong(String.valueOf(session.getAttribute("userID")));
            Users user_logged = usersDB.findUserbyID(userID).get(0);
            cases.setAuthor(user_logged);
            cases.setExamination(examination);
            cases.setQuestions(questions);
            cases.setHistory(history);
            casesDB.insertCase(cases);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AddCasesCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void process() {
        try {
            session = request.getSession(true);
            log = (LogFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/LogFacade!ejbs.LogFacade");
            Log log1 = new Log();
            long id = 1;
            if (!log.findAll().isEmpty()) {
               id = log.findAll().size()+1;
            }
            log1.setId(id);
            log1.setDate(new Date());
            log1.setEjbs("AddCasesCommand:process()");
            log.create(log1);
            casesDB = (ClinicalcasesFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/ClinicalcasesFacade!ejbs.ClinicalcasesFacade");
            usersDB = (UsersFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/UsersFacade!ejbs.UsersFacade");
            createCase();
            ShowCasesCommand command = new ShowCasesCommand();
            command.init(context, request, response);
            command.process();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewCase.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(AddCasesCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(AddCasesCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
