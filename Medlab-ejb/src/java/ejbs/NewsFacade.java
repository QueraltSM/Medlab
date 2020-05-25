/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Log;
import entities.News;
import entities.Speciality;
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
public class NewsFacade extends AbstractFacade<News> {
    @PersistenceContext(unitName = "Medlab-ejbPU")
    private EntityManager em;
    private LogFacade log;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NewsFacade() {
        super(News.class);
    }
    
    public List<News> findNewsbyID(long id) {
        setLogTrace("NewsFacade::findNewsbyID");
        return em.createNamedQuery("News.findById")
                .setParameter("id", id).getResultList();
    }
    
    public List<News> findNewsbyTitle(String title) {
        setLogTrace("NewsFacade::findNewsbyTitle");
        return em.createNamedQuery("News.findLikeTitle")
                .setParameter("title", "%"+ title+ "%").getResultList();
    }
    
    public List<News> findNewsbySpeciality(Speciality speciality) {
        setLogTrace("NewsFacade::findNewsbySpeciality");
        return em.createQuery("SELECT n FROM News n WHERE n.speciality.type LIKE :speciality")
                .setParameter("speciality", "%"+speciality.getType()+"%")
                .getResultList();
    }
    
    public List<News> findNewsbyTitle_Speciality(String title, Speciality speciality) {
        setLogTrace("NewsFacade::findNewsbyTitle_Speciality");
        return em.createQuery("SELECT n FROM News n WHERE n.title LIKE :title AND n.speciality.type LIKE :speciality")
                .setParameter("title", "%"+title+"%")
                .setParameter("speciality", "%"+speciality.getType()+"%")
                .getResultList();
    }     
    public int insertNewVisit(long id) {
        setLogTrace("NewsFacade::orderbyViews");
        News news = findNewsbyID(id).get(0);
        return em.createQuery("UPDATE News n set n.views = :views WHERE n.id = :id")
        .setParameter("id", id)
        .setParameter("views", news.getViews()+1)
        .executeUpdate();
    }
    
    public List<News> orderbyViews() {
        setLogTrace("NewsFacade::orderbyViews");
        return em.createQuery("SELECT n FROM News n ORDER BY n.views DESC")
        .getResultList();
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