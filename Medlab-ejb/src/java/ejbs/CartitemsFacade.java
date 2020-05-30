/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Cart;
import entities.Cartitems;
import entities.Log;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author QSM
 */
@Stateless
public class CartitemsFacade extends AbstractFacade<Cartitems> {
    @PersistenceContext(unitName = "Medlab-ejbPU")
    private EntityManager em;
    @EJB
    LogFacade log;
        
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CartitemsFacade() {
        super(Cartitems.class);
    }
    
    public List<Cartitems> findCartitemsByCartID(Cart cart) {
        setLogTrace("CartFacade::findCartitemsByCartID");
        return em.createNamedQuery("Cartitems.findByCartId")
                .setParameter("cartid", cart).getResultList();
    }  
        
    public List<Cart> findCartByCartID(Cart cart) {
        setLogTrace("CartFacade::findCartByCartID");
        return em.createNamedQuery("Cartitems.findByCartId")
                .setParameter("cartid", cart).getResultList();
    }
    
    public void setLogTrace(String ejbs) {
        System.out.println("setLogTrace::ejbs = "+ ejbs);
        Log log1 = new Log();
        long id = 1;
        if (!log.findAll().isEmpty()) {
            id = log.findAll().size()+1;
        }
        log1.setId(id);
        log1.setEjbs(ejbs);
        log1.setDate(new Date());
        log.create(log1);
    } 
}
