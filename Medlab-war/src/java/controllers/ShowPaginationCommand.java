/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import ejbs.BookFacade;
import ejbs.CartFacade;
import ejbs.CartitemsFacade;
import ejbs.ClinicalcasesFacade;
import ejbs.CommentFacade;
import ejbs.DiscussionsFacade;
import ejbs.LogFacade;
import ejbs.NewsFacade;
import ejbs.ResearchesFacade;
import ejbs.SpecialityFacade;
import ejbs.UsersFacade;
import entities.Book;
import entities.Cart;
import entities.Cartitems;
import entities.Clinicalcases;
import entities.Comment;
import entities.Discussions;
import entities.Log;
import entities.News;
import entities.Researches;
import entities.Speciality;
import entities.Users;
import java.io.IOException;
import java.util.Date;
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
public class ShowPaginationCommand extends FrontCommand {
    private LogFacade log;
    private NewsFacade newsDB;
    private ClinicalcasesFacade casesDB;
    private DiscussionsFacade discussionsDB;
    private ResearchesFacade researchesDB;
    private CommentFacade commentsDB;
    private BookFacade booksDB;
    private CartFacade cartsDB;
    private CartitemsFacade cartitemsDB;
    private UsersFacade usersDB;
    private SpecialityFacade specialityDB;

    private void showSpecialityFacade(int page_number) {
        int max_page_number = 0;
        List<Speciality> specialities = specialityDB.findByPagination(page_number);
        for (int i = 0; i<specialityDB.findAll().size(); i++) {
            if (i%5==0) {
                max_page_number++;
            }
        }
        request.setAttribute("specialities", specialities);
        request.setAttribute("max_page_number", max_page_number); 
    }
    
    private void showUsersFacade(int page_number) {
        int max_page_number = 0;
        List<Users> users = usersDB.findByPagination(page_number);
        for (int i = 0; i<usersDB.findAll().size(); i++) {
            if (i%5==0) {
                max_page_number++;
            }
        }
        request.setAttribute("users", users);
        request.setAttribute("max_page_number", max_page_number); 
    }

    private void showCartitemsFacade(int page_number) {
        int max_page_number = 0;
        List<Cartitems> cartitems = cartitemsDB.findByPagination(page_number);
        for (int i = 0; i<cartitemsDB.findAll().size(); i++) {
            if (i%5==0) {
                max_page_number++;
            }
        }
        request.setAttribute("cartitems", cartitems);
        request.setAttribute("max_page_number", max_page_number); 
    }
        
    private void showCartFacade(int page_number) {
        int max_page_number = 0;
        List<Cart> carts = cartsDB.findByPagination(page_number);
        for (int i = 0; i<cartsDB.findAll().size(); i++) {
            if (i%5==0) {
                max_page_number++;
            }
        }
        request.setAttribute("carts", carts);
        request.setAttribute("max_page_number", max_page_number); 
    }
        
    private void showBookFacade(int page_number) {
        int max_page_number = 0;
        List<Book> books = booksDB.findByPagination(page_number);
        for (int i = 0; i<booksDB.findAll().size(); i++) {
            if (i%5==0) {
                max_page_number++;
            }
        }
        request.setAttribute("books", books);
        request.setAttribute("max_page_number", max_page_number); 
    }
        
    private void showCommentFacade(int page_number) {
        int max_page_number = 0;
        List<Comment> comments = commentsDB.findByPagination(page_number);
        for (int i = 0; i<commentsDB.findAll().size(); i++) {
            if (i%5==0) {
                max_page_number++;
            }
        }
        request.setAttribute("comments", comments);
        request.setAttribute("max_page_number", max_page_number); 
    }
    
    private void showResearchesFacade(int page_number) {
        int max_page_number = 0;
        List<Researches> researches = researchesDB.findByPagination(page_number);
        for (int i = 0; i<researchesDB.findAll().size(); i++) {
            if (i%5==0) {
                max_page_number++;
            }
        }
        request.setAttribute("researches", researches);
        request.setAttribute("max_page_number", max_page_number); 
    }
    
    private void showDiscussionsFacade(int page_number) {
        int max_page_number = 0;
        List<Discussions> discussions = discussionsDB.findByPagination(page_number);
        for (int i = 0; i<discussionsDB.findAll().size(); i++) {
            if (i%5==0) {
                max_page_number++;
            }
        }
        request.setAttribute("discussions", discussions);
        request.setAttribute("max_page_number", max_page_number);
    }
        
    private void showCasesFacade(int page_number) {
        int max_page_number = 0;
        List<Clinicalcases> cases = casesDB.findByPagination(page_number);
        for (int i = 0; i<casesDB.findAll().size(); i++) {
            if (i%5==0) {
                max_page_number++;
            }
        }
        request.setAttribute("cases", cases);
        request.setAttribute("max_page_number", max_page_number);
    }
    
    private void showNewsFacade(int page_number) {
        int max_page_number = 0;
        List<News> news = newsDB.findByPagination(page_number);
        for (int i = 0; i<newsDB.findAll().size(); i++) {
            if (i%5==0) {
                max_page_number++;
            }
        }
        request.setAttribute("news", news);
        request.setAttribute("max_page_number", max_page_number);
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
            log1.setEjbs("ShowPaginationCommand:process()");
            log.create(log1);
            newsDB = (NewsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/NewsFacade!ejbs.NewsFacade");
            casesDB = (ClinicalcasesFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/ClinicalcasesFacade!ejbs.ClinicalcasesFacade");
            discussionsDB = (DiscussionsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/DiscussionsFacade!ejbs.DiscussionsFacade");
            researchesDB = (ResearchesFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/ResearchesFacade!ejbs.ResearchesFacade");
            commentsDB = (CommentFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/CommentFacade!ejbs.CommentFacade");
            booksDB = (BookFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/BookFacade!ejbs.BookFacade");
            cartsDB = (CartFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/CartFacade!ejbs.CartFacade");
            cartitemsDB = (CartitemsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/CartitemsFacade!ejbs.CartitemsFacade");
            usersDB = (UsersFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/UsersFacade!ejbs.UsersFacade");
            specialityDB = (SpecialityFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/SpecialityFacade!ejbs.SpecialityFacade");
            
            int page_number = 1;
            if (request.getParameter("page_number") != null) {
                page_number = Integer.parseInt(request.getParameter("page_number"));
            }
            
            if (request.getParameter("entity") == null) {
                showNewsFacade(page_number);
            } else {
                switch(request.getParameter("entity")) {
                    case "cases":
                        showCasesFacade(page_number);
                        break;
                    case "discussions":
                        showDiscussionsFacade(page_number);
                        break;
                    case "researches":
                        showResearchesFacade(page_number);
                        break;
                    case "comments":
                        showCommentFacade(page_number);
                        break;
                    case "books":
                        showBookFacade(page_number);
                        break;
                    case "carts":
                        showCartFacade(page_number);
                        break;
                    case "cartitems":
                        showCartitemsFacade(page_number);
                        break;
                    case "users":
                        showUsersFacade(page_number);
                        break;
                    case "specialities":
                        showSpecialityFacade(page_number);
                        break;
                    default:
                        showNewsFacade(page_number);
                        break;
                }
            }
            
            request.setAttribute("page_number", page_number);
        
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(request.getParameter("entity") + "_pagination.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(ShowPaginationCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(ShowPaginationCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}