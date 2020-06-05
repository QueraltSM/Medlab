package controllers;

import ejbs.CartFacade;
import ejbs.LogFacade;
import ejbs.UsersFacade;
import entities.Cart;
import entities.Fullname;
import entities.Log;
import entities.Users;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
public class SignupCommand extends FrontCommand {
    private LogFacade log;
    private UsersFacade usersDB;
    private CartFacade cartsDB;

    private void registerUser() {
        try {
            String first_name = new String(request.getParameter("first_name").getBytes("ISO8859_1"), "UTF-8");
            String last_name = new String(request.getParameter("last_name").getBytes("ISO8859_1"), "UTF-8");
            String email = new String(request.getParameter("email").getBytes("ISO8859_1"), "UTF-8");
            String password = new String(request.getParameter("password").getBytes("ISO8859_1"), "UTF-8");
            String type = "doctor";
            Fullname fullname = new Fullname();
            fullname.setFirstName(first_name);
            fullname.setLastName(last_name);
            Users user = new Users();
            user.setId(Long.parseLong(String.valueOf(request.getParameter("license_number"))));
            user.setFullname(fullname);
            user.setEmail(email);
            user.setPassword(password);
            user.setType(type);
            usersDB.insertUser(user);
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

    private void createCart() {
        long userID = Long.parseLong(String.valueOf(request.getParameter("license_number")));
        Users user = usersDB.find(userID);
        List<Cart> carts = cartsDB.findCartByUserID(user);
        if (carts.isEmpty()) {
            Cart cart = new Cart();
            long cartID = cartsDB.findAll().size()+1;
            cart.setId(cartID);
            cart.setUserid(user);
            cartsDB.create(cart);
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
            log1.setEjbs("SignupCommand:process()");
            log.create(log1);
            usersDB = (UsersFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/UsersFacade!ejbs.UsersFacade");
            cartsDB = (CartFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/CartFacade!ejbs.CartFacade");
            
            String destination = "login.jsp";
            if (userExists()) {
                destination = "signup.jsp";
            } else {
                registerUser();
                createCart();
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
