/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Log;
import entities.Users;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author QSM
 */
@Stateless
public class UsersFacade extends AbstractFacade<Users> {
    @PersistenceContext(unitName = "Medlab-ejbPU")
    private EntityManager em;
    private LogFacade log;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }
    
    public List<Users> findUserbyEmail(String email) {
        setLogTrace("UsersFacade::findUserbyEmail");
        return em.createNamedQuery("Users.findByEmail")
                .setParameter("email", email).getResultList();
    }
    
    public void setLogTrace(String ejbs) {
        try {
            log = (LogFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/LogFacade!ejbs.LogFacade");
            Log log1 = new Log();
            long id = 1;
            if (!log.findAll().isEmpty()) {
                id = id+1;
            }
            log1.setId(id);
            log1.setEjbs(ejbs);
        } catch (NamingException ex) {
            Logger.getLogger(UsersFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
