/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.Carts;

import controllers.Books.AddBooksCommand;
import controllers.Books.ShowBooksCommand;
import controllers.FrontCommand;
import ejbs.LogFacade;
import ejbs.CartFacade;
import ejbs.CartitemsFacade;
import ejbs.UsersFacade;
import entities.Cart;
import entities.Cartitems;
import entities.Log;
import entities.Users;
import java.io.IOException;
import java.util.ArrayList;
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
public class AddCartCommand extends FrontCommand {
    private LogFacade log;
    private CartFacade cartsDB;
    private HttpSession session;
    private UsersFacade usersDB;
    private CartitemsFacade cartitemsDB;
    
    private void storeBook() {
        long userID = Long.parseLong(String.valueOf(session.getAttribute("userID")));
        Users user = usersDB.find(userID);
        Cart cart = cartsDB.findCartByUserID(user).get(0);
        long bookID = Long.parseLong(String.valueOf(request.getParameter("id")));
        long itemsID = cartitemsDB.findAll().size()+1;
        
        Cartitems items = new Cartitems();
        items.setBookid(bookID);
        items.setCartid(cart);
        
        boolean entered = false;
        
        for (Cartitems c : cartitemsDB.findAll()) {
            if (c.getBookid() == bookID && c.getCartid().getId().equals(cart.getId())) {
                items.setId(c.getId());
                items.setQuantity(c.getQuantity()+1);
                cartitemsDB.edit(items);
                entered = true;
            }
        }
        
        if (!entered) {
            items.setId(itemsID);
            items.setQuantity(1);
            cartitemsDB.create(items);
        }
        
    }
    
    private void createCart() {
        long userID = Long.parseLong(String.valueOf(session.getAttribute("userID")));
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
            session = request.getSession(true);
            log = (LogFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/LogFacade!ejbs.LogFacade");
            Log log1 = new Log();
            long id = 1;
            if (!log.findAll().isEmpty()) {
               id = log.findAll().size()+1;
            }
            log1.setId(id);
            log1.setEjbs("AddCartsCommand:process()");
            log.create(log1);
            cartsDB = (CartFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/CartFacade!ejbs.CartFacade");
            usersDB = (UsersFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/UsersFacade!ejbs.UsersFacade");
            cartitemsDB = (CartitemsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/CartitemsFacade!ejbs.CartitemsFacade");
            createCart();
            storeBook();
            ShowBooksCommand command = new ShowBooksCommand();
            command.init(context, request, response);
            command.process();
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewBooks.jsp");
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(AddBooksCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(AddCartCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
