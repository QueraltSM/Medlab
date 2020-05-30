/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.Carts;

import controllers.FrontCommand;
import ejbs.LogFacade;
import ejbs.CartFacade;
import ejbs.CartitemsFacade;
import ejbs.UsersFacade;
import entities.Log;
import entities.Cart;
import entities.Cartitems;
import entities.Users;
import java.io.IOException;
import java.util.ArrayList;
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
public class ShowCartCommand extends FrontCommand {
    private LogFacade log;
    private CartFacade cartsDB;
    private HttpSession session;
    private UsersFacade usersDB;
    private CartitemsFacade cartitemsDB;

    private void getCart() {
        long userID = Long.parseLong(String.valueOf(session.getAttribute("userID")));
        Users user = usersDB.find(userID);
        List <Cart> cart = cartsDB.findCartByUserID(user);
        List<Cartitems> items = new ArrayList();
        if (!cart.isEmpty()) {
            items = cartitemsDB.findCartitemsByCartID(cart.get(0));
        }
        session.setAttribute("Cart", items);
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
            log1.setEjbs("ShowCartCommand:process()");
            log.create(log1);
            session = request.getSession();
            usersDB = (UsersFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/UsersFacade!ejbs.UsersFacade");
            cartitemsDB = (CartitemsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/CartitemsFacade!ejbs.CartitemsFacade");
            cartsDB = (CartFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/CartFacade!ejbs.CartFacade");
            getCart();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewCart.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(ShowCartCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(ShowCartCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
