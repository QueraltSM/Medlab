/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.News.ShowNewsCommand;
import ejbs.LogFacade;
import ejbs.LoginstatsFacade;
import ejbs.UsersFacade;
import entities.Log;
import entities.Loginstats;
import entities.Users;
import java.io.IOException;
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
public class LoginCommand extends FrontCommand {

    private static HttpSession session;
    private UsersFacade usersDB;
    private LogFacade log;
    private LoginstatsFacade login_stats;

    @SuppressWarnings("unchecked")
    private boolean userExists() {
        boolean exists = true;
        if (usersDB.findUserbyEmail((String) request.getParameter("email")).isEmpty()) {
            request.setAttribute("login_error_email", "Email is incorrect");
            request.setAttribute("login_error_password", "Password is incorrect");
            return false;
        }
        Users user = usersDB.findUserbyEmail((String) request.getParameter("email")).get(0);
        if (!user.getEmail().equals(request.getParameter("email"))) {
            exists = false;
            request.setAttribute("login_error_email", "Email is incorrect");
        }
        if (!user.getPassword().equals(request.getParameter("password"))) {
            exists = false;
            request.setAttribute("login_error_password", "Password is incorrect");
        }
        if (exists) {
            session.setAttribute("usertype", user.getType());
            session.setAttribute("fullname", user.getName());
        }
        return exists;
    }

    @Override
    public void process() {
        try {
            log = (LogFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/LogFacade!ejbs.LogFacade");
            login_stats = (LoginstatsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/LoginstatsFacade!ejbs.LoginstatsFacade");
            Log log1 = new Log();
            long id = 1;
            if (!log.findAll().isEmpty()) {
               id = log.findAll().size()+1;
            }
            log1.setId(id);
            log1.setEjbs("LoginCommand:process()");
            log.create(log1);
            session = request.getSession(true);
            usersDB = (UsersFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/UsersFacade!ejbs.UsersFacade");
            if (userExists()) {
                Loginstats loginstats1 = new Loginstats();
                long id1 = 0;
                if (!login_stats.findAll().isEmpty()) {
                    id1 = login_stats.findAll().size()+1;
                }
                loginstats1.setId(id1);
                loginstats1.setEmail(request.getParameter("email"));
                loginstats1.setDate(new Date());
                login_stats.create(loginstats1);
                session.setAttribute("logged", "true");
                session.setAttribute("email", request.getParameter("email"));
                ShowNewsCommand command = new ShowNewsCommand();
                command.init(context, request, response);
                command.process();
            } else {
                try {
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
                    requestDispatcher.forward(request, response);
                } catch (ServletException | IOException ex) {
                    Logger.getLogger(LoginCommand.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (NamingException ex) {
            Logger.getLogger(LoginCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
