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
public class ModifyNewsCommand extends FrontCommand {

    private Log log;
    private NewsFacade newsDB;
    
    private void modifyNews() {
        try {
            String title = new String(request.getParameter("title").getBytes("ISO8859_1"), "UTF-8");
            String description = new String(request.getParameter("description").getBytes("ISO8859_1"), "UTF-8");
            long id = Long.parseLong((String) request.getParameter("id"));
            News news = new News();
            news.setId(id);
            news.setTitle(title);
            news.setDescription(description);
            news.setSpeciality(new Speciality(request.getParameter("speciality")));
            news.setDate(new Date());
            newsDB.edit(news);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AddNewsCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void process() {
        try {
            log = (Log) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/Log!ejbs.Log");
            log.newCallEJB("ModifyNewsCommand:process()");
            newsDB = (NewsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/NewsFacade!ejbs.NewsFacade");
            modifyNews();
            NewsDetailsCommand command = new NewsDetailsCommand();
            command.init(context, request, response);
            command.process();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewNews.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(ModifyNewsCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(ModifyNewsCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}