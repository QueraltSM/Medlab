/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Log;
import entities.News;
import entities.Speciality;
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
public class NewsFacade extends AbstractFacade<News> {
    @PersistenceContext(unitName = "Medlab-ejbPU")
    private EntityManager em;
    @EJB
    LogFacade log;

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
            
    public void updateVisits(long id) {
        setLogTrace("NewsFacade::updateVisits");
        em.createQuery("UPDATE News n set n.views = :views WHERE n.id = :id")
        .setParameter("id", id)
        .setParameter("views", findNewsbyID(id).get(0).getViews()+1)
        .executeUpdate();
    }

    public List<News> orderbyRecent() {
        setLogTrace("NewsFacade::orderbyRecent");
        return em.createQuery("SELECT n FROM News n ORDER BY n.date DESC")
        .getResultList();
    }
        
    public List<News> orderbyViews() {
        setLogTrace("NewsFacade::orderbyViews");
        return em.createQuery("SELECT n FROM News n ORDER BY n.views DESC")
        .getResultList();
    }
    
    public void insertNews(News news) {
        setLogTrace("NewsFacade::insertNews");
        em.createNativeQuery("INSERT INTO NEWS (id, title, description, date, speciality, views) VALUES (?,?,?,?,?,?)")
        .setParameter(1, news.getId())
        .setParameter(2, news.getTitle())
        .setParameter(3, news.getDescription())
        .setParameter(4, news.getDate())
        .setParameter(5, news.getSpeciality().getType())
        .setParameter(6, news.getViews())
        .executeUpdate();
    }
    
    public void deleteNews(long id) {
        setLogTrace("NewsFacade::deleteNews");
        em.createQuery("DELETE FROM News n WHERE n.id = :id")
        .setParameter("id", id)
        .executeUpdate();   
    }
    
    public void updateNews(News news) {
        setLogTrace("NewsFacade::updateNews");
        em.createQuery("UPDATE News n set n.title = :title, n.description = :description, n.date = :date, n.speciality = :speciality, n.views = :views WHERE n.id = :id")
        .setParameter("id", news.getId())
        .setParameter("date", news.getDate())
        .setParameter("description", news.getDescription())
        .setParameter("title", news.getTitle())
        .setParameter("speciality", news.getSpeciality())
        .setParameter("views", news.getViews())
        .executeUpdate();
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