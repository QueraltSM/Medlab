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
import entities.Book;
import java.io.IOException;
import java.util.Date;
import java.util.List;
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
public class ShowBooksCommand extends FrontCommand {
    private HttpSession session;
    private BookFacade booksDB;
    private LogFacade log;

    public void getSortedBooks() {
        List<Book> books = booksDB.orderbyViews();
        if (books.isEmpty()) {
            request.setAttribute("error", "There is no most viewed books yet");
        }
        request.setAttribute("all_sorted_books", books);
    }
        
    private void getAllBooks() {
        List<Book> books = booksDB.orderbyRecent();
        if (books.isEmpty()) {
            request.setAttribute("error", "There is no books yet");
        }
        request.setAttribute("all_books", books);
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
            log1.setEjbs("ShowBooksCommand:process()");
            log.create(log1);
            session = request.getSession();
            booksDB = (BookFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/BookFacade!ejbs.BookFacade");
            getAllBooks();
            
            if (request.getParameter("sort") != null && request.getParameter("sort").equals("viewed")) {
                getSortedBooks();
            }
            
            session.setAttribute("search_command", "SearchBooksCommand");
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("books.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(ShowBooksCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(ShowBooksCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
