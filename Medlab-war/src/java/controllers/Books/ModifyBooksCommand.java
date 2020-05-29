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
public class ModifyBooksCommand extends FrontCommand {
    private LogFacade log;
    private BookFacade booksDB;
    private HttpSession session;
    
    private void modifyBook() {
        try {
            String title = new String(request.getParameter("title").getBytes("ISO8859_1"), "UTF-8");
            String description = new String(request.getParameter("description").getBytes("ISO8859_1"), "UTF-8");
            String author = new String(request.getParameter("author").getBytes("ISO8859_1"), "UTF-8");
            double price = Double.parseDouble((String)request.getParameter("price"));
            int stock = Integer.parseInt(((String)request.getParameter("stock")));
            long id = Long.parseLong((String) request.getParameter("id"));
            Book books = booksDB.find(id);
            books.setTitle(title);
            books.setDescription(description);
            books.setSpeciality(new Speciality(request.getParameter("speciality")));
            books.setDate(new Date());
            books.setStock(stock);
            books.setPrice(price);
            booksDB.updateBook(books);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ModifyBooksCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void process() {
        try {
            session = request.getSession();
            log = (LogFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/LogFacade!ejbs.LogFacade");
            Log log1 = new Log();
            long id = 1;
            if (!log.findAll().isEmpty()) {
               id = log.findAll().size()+1;
            }
            log1.setId(id);
            log1.setEjbs("ModifyBooksCommand:process()");
            log.create(log1);
            booksDB = (BookFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/BookFacade!ejbs.BookFacade");
            modifyBook();
            BooksDetailsCommand command = new BooksDetailsCommand();
            command.init(context, request, response);
            command.process();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewBooks.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(ModifyBooksCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(ModifyBooksCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
