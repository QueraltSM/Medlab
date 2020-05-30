/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.Researches;

import controllers.FrontCommand;
import ejbs.LogFacade;
import ejbs.ResearchesFacade;
import ejbs.UsersFacade;
import entities.Log;
import entities.Researches;
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
public class AddResearchesCommand extends FrontCommand {
    private LogFacade log;
    private ResearchesFacade researchesDB;
    private UsersFacade usersDB;
    private HttpSession session;
    
    private void createResearch() {
        try {
            String title = new String(request.getParameter("title").getBytes("ISO8859_1"), "UTF-8");
            String description = new String(request.getParameter("description").getBytes("ISO8859_1"), "UTF-8");
            String conclusions = new String(request.getParameter("conclusions").getBytes("ISO8859_1"), "UTF-8");
            Researches researches = new Researches();
            long userID = Long.parseLong(String.valueOf(session.getAttribute("userID")));
            Users user_logged = usersDB.findUserbyID(userID).get(0);
            long id = 0;
            if (!researchesDB.findAll().isEmpty()) id = researchesDB.findAll().get(researchesDB.count()-1).getId()+1;
            researches.setId(id);
            researches.setViews(0);
            researches.setTitle(title);
            researches.setDescription(description);
            researches.setSpeciality(new Speciality(request.getParameter("speciality")));
            researches.setDate(new Date());
            researches.setAuthor(user_logged);
            researches.setConclusions(conclusions);
            researchesDB.insertResearch(researches);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AddResearchesCommand.class.getName()).log(Level.SEVERE, null, ex);
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
            log1.setEjbs("AddResearchesCommand:process()");
            log.create(log1);
            usersDB = (UsersFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/UsersFacade!ejbs.UsersFacade");
            researchesDB = (ResearchesFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/ResearchesFacade!ejbs.ResearchesFacade");
            createResearch();
            ShowResearchesCommand command = new ShowResearchesCommand();
            command.init(context, request, response);
            command.process();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewResearch.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(AddResearchesCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(AddResearchesCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
