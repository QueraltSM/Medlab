/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.Books;

import controllers.FrontCommand;
import ejbs.CommentFacade;
import ejbs.LogFacade;
import ejbs.BookFacade;
import entities.Comment;
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
public class BooksDetailsCommand extends FrontCommand {
    private HttpSession session;
    private BookFacade booksDB;
    private CommentFacade commentsDB;
    private LogFacade log;

    private void getAllComments() {
        long id = Long.parseLong((String) request.getParameter("id"));
        String type = request.getParameter("type");
        List<Comment> comments = commentsDB.orderbyRecent(id, type);
        request.setAttribute("comments", comments);
    }

    private void setBooksVisit(Book books) {
        booksDB.updateVisits(books.getId());
    }
    
    private void getBooksDetails() {
        long id = Long.parseLong((String) request.getParameter("id"));
        Book books = booksDB.find(id);
        request.setAttribute("books", books);
        if (request.getParameter("action") == null) {
            setBooksVisit(books);
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
            log1.setEjbs("BooksDetailsCommand:process()");
            log.create(log1);
            session = request.getSession(true);
            booksDB = (BookFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/BookFacade!ejbs.BookFacade");
            commentsDB = (CommentFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/CommentFacade!ejbs.CommentFacade");
            getBooksDetails();
            getAllComments();
            session.setAttribute("details_command", "BooksDetailsCommand");
            try {
                String dst = "viewBooks.jsp";
                if (request.getParameter("action") != null && request.getParameter("action").equals("edit")) {
                   dst = "editBook.jsp";
                }
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(dst);
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(BooksDetailsCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(BooksDetailsCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
