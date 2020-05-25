/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.News;
import entities.Speciality;
import java.util.List;
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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NewsFacade() {
        super(News.class);
    }
    
    public List<News> findNewsbyID(long id) {
        return em.createNamedQuery("News.findById")
                .setParameter("id", id).getResultList();
    }
    
    public List<News> findNewsbyTitle(String title) {
        return em.createNamedQuery("News.findLikeTitle")
                .setParameter("title", "%"+ title+ "%").getResultList();
    }
    
    public List<News> findNewsbySpeciality(Speciality speciality) {
        return em.createQuery("SELECT n FROM News n WHERE n.speciality.type LIKE :speciality")
                .setParameter("speciality", "%"+speciality.getType()+"%")
                .getResultList();
    }
    
    public List<News> findNewsbyTitle_Speciality(String title, Speciality speciality) {
        return em.createQuery("SELECT n FROM News n WHERE n.title LIKE :title AND n.speciality.type LIKE :speciality")
                .setParameter("title", "%"+title+"%")
                .setParameter("speciality", "%"+speciality.getType()+"%")
                .getResultList();
    }     
    public int insertNewVisit(long id) {
        News news = findNewsbyID(id).get(0);
        return em.createQuery("UPDATE News n set n.views = :views WHERE n.id = :id")
        .setParameter("id", id)
        .setParameter("views", news.getViews()+1)
        .executeUpdate();
    }
    
    public List<News> orderbyViews() {
        return em.createQuery("SELECT n FROM News n ORDER BY n.views DESC")
        .getResultList();
    }
}