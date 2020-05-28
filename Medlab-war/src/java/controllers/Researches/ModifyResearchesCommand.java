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
public class ModifyResearchesCommand extends FrontCommand {
    private LogFacade log;
    private ResearchesFacade researchesDB;
    private HttpSession session;
    
    private void modifyCase() {
        try {
            String title = new String(request.getParameter("title").getBytes("ISO8859_1"), "UTF-8");
            String description = new String(request.getParameter("description").getBytes("ISO8859_1"), "UTF-8");
            String conclusions = new String(request.getParameter("conclusions").getBytes("ISO8859_1"), "UTF-8");
            long id = Long.parseLong((String) request.getParameter("id"));
            Researches researches = researchesDB.find(id);
            researches.setTitle(title);
            researches.setDescription(description);
            researches.setSpeciality(new Speciality(request.getParameter("speciality")));
            researches.setDate(new Date());
            researches.setConclusions(conclusions);
            researchesDB.updateResearch(researches);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ModifyResearchesCommand.class.getName()).log(Level.SEVERE, null, ex);
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
            log1.setEjbs("ModifyResearchesCommand:process()");
            log.create(log1);
            researchesDB = (ResearchesFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/ResearchesFacade!ejbs.ResearchesFacade");
            modifyCase();
            ResearchesDetailsCommand command = new ResearchesDetailsCommand();
            command.init(context, request, response);
            command.process();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewResearches.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(ModifyResearchesCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(ModifyResearchesCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
