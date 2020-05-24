package controllers;

import ejbs.Log;
import ejbs.UsersFacade;
import entities.Users;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
public class SignupCommand extends FrontCommand {
    private Log log;
    private UsersFacade usersDB;

    private void registerUser() {
        try {
            String name = new String(request.getParameter("fullname").getBytes("ISO8859_1"), "UTF-8");
            String email = new String(request.getParameter("email").getBytes("ISO8859_1"), "UTF-8");
            String password = new String(request.getParameter("password").getBytes("ISO8859_1"), "UTF-8");
            String type = "doctor";
            Users user = new Users(Long.parseLong(request.getParameter("license_number")));
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setType(type);
            usersDB.create(user);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SignupCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean userExists() {
        boolean exists = false;
        long license_number = Long.parseLong(request.getParameter("license_number"));
        Users user = usersDB.find(license_number);
        if (user != null) {
            request.setAttribute("signup_error_license_number", "License number is already registered");
            exists = true;
        }
        if (usersDB.findUserbyEmail(request.getParameter("email")).size() > 0) {
            request.setAttribute("signup_error_email", "Email is already registered");
            exists = true;
        }
        return exists;
    }

    @Override
    public void process() {
        try {
            log = (Log) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/Log!ejbs.Log");
            log.newCallEJB("SignupCommand:process()");
            usersDB = (UsersFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/UsersFacade!ejbs.UsersFacade");
            String destination = "login.jsp";
            if (userExists()) {
                destination = "signup.jsp";
            } else {
                registerUser();
            }
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(destination);
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(SignupCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(SignupCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
