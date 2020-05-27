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
import entities.Discussions;
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
public class ModifyDiscussionsCommand extends FrontCommand {
    private LogFacade log;
    private DiscussionsFacade discussionsDB;
    private HttpSession session;
    
    private void modifyDiscussions() {
        try {
            String title = new String(request.getParameter("title").getBytes("ISO8859_1"), "UTF-8");
            String description = new String(request.getParameter("description").getBytes("ISO8859_1"), "UTF-8");
            long id = Long.parseLong((String) request.getParameter("id"));
            Discussions discussions = new Discussions();
            discussions.setId(id);
            discussions.setAuthor((String) session.getAttribute("email"));
            discussions.setTitle(title);
            discussions.setDescription(description);
            discussions.setViews(discussionsDB.find(id).getViews());
            discussions.setSpeciality(new Speciality(request.getParameter("speciality")));
            discussions.setDate(new Date());
            discussionsDB.updateDiscussions(discussions);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ModifyDiscussionsCommand.class.getName()).log(Level.SEVERE, null, ex);
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
            log1.setEjbs("ModifyDiscussionsCommand:process()");
            log.create(log1);
            discussionsDB = (DiscussionsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/DiscussionsFacade!ejbs.DiscussionsFacade");
            modifyDiscussions();
            DiscussionsDetailsCommand command = new DiscussionsDetailsCommand();
            command.init(context, request, response);
            command.process();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewDiscussions.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(ModifyDiscussionsCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(ModifyDiscussionsCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
