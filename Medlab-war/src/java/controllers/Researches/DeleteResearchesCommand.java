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
import java.io.IOException;
import java.util.Date;
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
public class DeleteResearchesCommand extends FrontCommand {
    private LogFacade log;
    private ResearchesFacade researchesDB;

    private void deleteCase() {
        long id = Long.parseLong((String) request.getParameter("id"));
        researchesDB.deleteResearch(id);
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
            log1.setEjbs("DeleteResearchesCommand:process()");
            log.create(log1);
            researchesDB = (ResearchesFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/ResearchesFacade!ejbs.ResearchesFacade");
            deleteCase();
            ShowResearchesCommand command = new ShowResearchesCommand();
            command.init(context, request, response);
            command.process();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewResearches.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(DeleteResearchesCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(DeleteResearchesCommand.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
