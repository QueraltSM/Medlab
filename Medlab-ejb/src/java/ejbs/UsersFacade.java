/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Log;
import entities.News;
import entities.Users;
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
public class UsersFacade extends AbstractFacade<Users> {
    @PersistenceContext(unitName = "Medlab-ejbPU")
    private EntityManager em;
    @EJB
    LogFacade log;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }
    
    public List<Users> findUserbyID(long id) {
        setLogTrace("UsersFacade::findUserbyID");
        return em.createNamedQuery("Users.findById")
                .setParameter("id", id).getResultList();
    }
    public List<Users> findUserbyEmail(String email) {
        setLogTrace("UsersFacade::findUserbyEmail");
        return em.createNamedQuery("Users.findByEmail")
                .setParameter("email", email).getResultList();
    }
    
    public void insertUser(Users user) {
        setLogTrace("UsersFacade::insertUser");
        em.persist(user);
    }
    
    public void setLogTrace(String ejbs) {
        Log log1 = new Log();
        long id = 1;
        if (!log.findAll().isEmpty()) {
            id = log.findAll().size()+1;
        }
        log1.setId(id);
        log1.setEjbs(ejbs);
        log.create(log1);
    }
}
