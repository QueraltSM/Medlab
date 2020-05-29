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
import entities.Speciality;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
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
public class SearchBooksCommand extends FrontCommand {
    private BookFacade booksDB;
    private LogFacade log;

    private void getBooksMatched() {
        try {
            List<Book> books;
            String title;
            Speciality speciality;
            if (request.getParameter("keyword_search").isEmpty() && request.getParameter("speciality_search").equals("All specialities")) {
                books = booksDB.findAll();
            } else if (!request.getParameter("keyword_search").isEmpty() && request.getParameter("speciality_search").equals("All specialities")) {
                title = new String(request.getParameter("keyword_search").getBytes("ISO8859_1"), "UTF-8");
                books = booksDB.findBooksbyTitle(title);
            } else if (request.getParameter("keyword_search").isEmpty() && !request.getParameter("speciality_search").equals("All specialities")) {
                speciality = new Speciality(request.getParameter("speciality_search"));
                books = booksDB.findBooksbySpeciality(speciality);
            } else {
                title = new String(request.getParameter("keyword_search").getBytes("ISO8859_1"), "UTF-8");
                speciality = new Speciality(request.getParameter("speciality_search"));
                books = booksDB.findBooksbyTitle_Speciality(title, speciality);
            }
            if (books.isEmpty()) {
                request.setAttribute("error", "There is no books that matches");
            }
            request.setAttribute("all_matched_books", books);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SearchBooksCommand.class.getName()).log(Level.SEVERE, null, ex);
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
            log1.setEjbs("SearchBooksCommand:process()");
            log.create(log1);
            booksDB = (BookFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/BookFacade!ejbs.BookFacade");
            getBooksMatched();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("books.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(SearchBooksCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(SearchBooksCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
