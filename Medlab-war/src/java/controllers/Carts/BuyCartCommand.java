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
import entities.Cart;
import entities.Log;
import entities.Users;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author QSM
 */
public class BuyCartCommand extends FrontCommand {
    private LogFacade log;
    private CartFacade cartsDB;
    private HttpSession session;
    private UsersFacade usersDB;
    private CartitemsFacade cartitemsDB;

    private void buyCart() {
        long userID = Long.parseLong(String.valueOf(session.getAttribute("userID")));
        Users user = usersDB.find(userID);
        Cart cart = cartsDB.findCartByUserID(user).get(0);
        cartitemsDB.findCartitemsByCartID(cart).stream().forEach((c) -> {
            cartitemsDB.remove(c);
        });
    }

    @Override
    public void process() {
        try {
            session = request.getSession(true);
            log = (LogFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/LogFacade!ejbs.LogFacade");
            Log log1 = new Log();
            long id = 1;
            if (!log.findAll().isEmpty()) {
                id = log.findAll().size() + 1;
            }
            log1.setId(id);
            log1.setDate(new Date());
            log1.setEjbs("BuyCartCommand:process()");
            log.create(log1);
            cartsDB = (CartFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/CartFacade!ejbs.CartFacade");
            usersDB = (UsersFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/UsersFacade!ejbs.UsersFacade");
            cartitemsDB = (CartitemsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/CartitemsFacade!ejbs.CartitemsFacade");
            buyCart();
            ShowCartCommand command = new ShowCartCommand();
            command.init(context, request, response);
            command.process();
        } catch (NamingException ex) {
            Logger.getLogger(BuyCartCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}