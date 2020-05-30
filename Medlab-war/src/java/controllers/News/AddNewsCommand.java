/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.News;

import controllers.FrontCommand;
import ejbs.LogFacade;
import ejbs.NewsFacade;
import entities.Log;
import entities.News;
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

/**
 *
 * @author QSM
 */
public class AddNewsCommand extends FrontCommand {
    private LogFacade log;
    private NewsFacade newsDB;
    
    private void createNews() {
        try {
            String title = new String(request.getParameter("title").getBytes("ISO8859_1"), "UTF-8");
            String description = new String(request.getParameter("description").getBytes("ISO8859_1"), "UTF-8");
            News news = new News();
            long id = 0;
            if (!newsDB.findAll().isEmpty()) id = newsDB.findAll().get(newsDB.count()-1).getId()+1;
            news.setId(id);
            news.setViews(0);
            news.setTitle(title);
            news.setDescription(description);
            news.setSpeciality(new Speciality(request.getParameter("speciality")));
            news.setDate(new Date());
            newsDB.insertNews(news);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AddNewsCommand.class.getName()).log(Level.SEVERE, null, ex);
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
            log1.setEjbs("AddNewsCommand:process()");
            log.create(log1);
            newsDB = (NewsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/NewsFacade!ejbs.NewsFacade");
            createNews();
            ShowNewsCommand command = new ShowNewsCommand();
            command.init(context, request, response);
            command.process();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewNews.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(AddNewsCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(AddNewsCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
