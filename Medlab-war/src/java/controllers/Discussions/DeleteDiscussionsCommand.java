/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.Discussions;

import controllers.FrontCommand;
import ejbs.LogFacade;
import ejbs.DiscussionsFacade;
import entities.Log;
import java.io.IOException;
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
public class DeleteDiscussionsCommand extends FrontCommand {
    private LogFacade log;
    private DiscussionsFacade discussionsDB;

    private void deleteDiscussions() {
        long id = Long.parseLong((String) request.getParameter("id"));
        discussionsDB.deleteDiscussions(id);
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
            log1.setEjbs("DeleteDiscussionsCommand:process()");
            log.create(log1);
            discussionsDB = (DiscussionsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/DiscussionsFacade!ejbs.DiscussionsFacade");
            deleteDiscussions();
            ShowDiscussionsCommand command = new ShowDiscussionsCommand();
            command.init(context, request, response);
            command.process();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewDiscussions.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(DeleteDiscussionsCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(DeleteDiscussionsCommand.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
