/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.News;

import controllers.FrontCommand;
import ejbs.Log;
import ejbs.NewsFacade;
import entities.News;
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
public class DeleteNewsCommand extends FrontCommand {
    private Log log;
    private NewsFacade newsDB;

    private void deleteNews() {
        long id = Long.parseLong((String) request.getParameter("id"));
        System.out.println("id = " + id);
        News news = newsDB.find(id);
        newsDB.remove(news);
    }

    @Override
    public void process() {
        try {
            log = (Log) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/Log!ejbs.Log");
            log.newCallEJB("DeleteNewsCommand:process()");
            newsDB = (NewsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/NewsFacade!ejbs.NewsFacade");
            deleteNews();
            ShowNewsCommand command = new ShowNewsCommand();
            command.init(context, request, response);
            command.process();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewNews.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(DeleteNewsCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(DeleteNewsCommand.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
