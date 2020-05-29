/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.Books;

import controllers.FrontCommand;
import ejbs.LogFacade;
import ejbs.BookFacade;
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
public class DeleteBooksCommand extends FrontCommand {
    private LogFacade log;
    private BookFacade booksDB;

    private void deleteBook() {
        long id = Long.parseLong((String) request.getParameter("id"));
        booksDB.deleteBook(id);
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
            log1.setEjbs("DeleteBooksCommand:process()");
            log.create(log1);
            booksDB = (BookFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/BookFacade!ejbs.BookFacade");
            deleteBook();
            ShowBooksCommand command = new ShowBooksCommand();
            command.init(context, request, response);
            command.process();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewBooks.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(DeleteBooksCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(DeleteBooksCommand.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
