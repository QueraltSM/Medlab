/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Discussions;
import entities.Log;
import entities.Speciality;
import entities.Users;
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
public class DiscussionsFacade extends AbstractFacade<Discussions> {
    @PersistenceContext(unitName = "Medlab-ejbPU")
    private EntityManager em;
    @EJB
    LogFacade log;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DiscussionsFacade() {
        super(Discussions.class);
    }
    
    public List<Discussions> findDiscussionsbyID(long id) {
        setLogTrace("DiscussionsFacade::findDiscussionsbyID");
        return em.createNamedQuery("Discussions.findById")
                .setParameter("id", id).getResultList();
    }
   
    public List<Discussions> findDiscussionsbyTitle(String title) {
        setLogTrace("DiscussionsFacade::findDiscussionsbyTitle");
        return em.createNamedQuery("Discussions.findLikeTitle")
                .setParameter("title", "%"+ title+ "%").getResultList();
    }
    
    public List<Discussions> findDiscussionsbyAuthor(Users author) {
        setLogTrace("DiscussionsFacade::findDiscussionsbyID");
        return em.createNamedQuery("Discussions.findByAuthor")
                .setParameter("author", author).getResultList();
    }
        
    public List<Discussions> findDiscussionsbySpeciality(Speciality speciality) {
        setLogTrace("DiscussionsFacade::findDiscussionsbySpeciality");
        return em.createQuery("SELECT n FROM Discussions n WHERE n.speciality.type LIKE :speciality")
                .setParameter("speciality", "%"+speciality.getType()+"%")
                .getResultList();
    }
    
    public List<Discussions> findDiscussionsbyTitle_Speciality(String title, Speciality speciality) {
        setLogTrace("DiscussionsFacade::findDiscussionsbyTitle_Speciality");
        return em.createQuery("SELECT n FROM Discussions n WHERE n.title LIKE :title AND n.speciality.type LIKE :speciality")
                .setParameter("title", "%"+title+"%")
                .setParameter("speciality", "%"+speciality.getType()+"%")
                .getResultList();
    } 
            
    public void updateVisits(long id) {
        setLogTrace("DiscussionsFacade::updateVisits");
        em.createQuery("UPDATE Discussions n set n.views = :views WHERE n.id = :id")
        .setParameter("id", id)
        .setParameter("views", findDiscussionsbyID(id).get(0).getViews()+1)
        .executeUpdate();
    }

    public List<Discussions> orderbyRecent() {
        setLogTrace("DiscussionsFacade::orderbyRecent");
        return em.createQuery("SELECT n FROM Discussions n ORDER BY n.date DESC")
        .getResultList();
    }
        
    public List<Discussions> orderbyViews() {
        setLogTrace("DiscussionsFacade::orderbyViews");
        return em.createQuery("SELECT n FROM Discussions n ORDER BY n.views DESC")
        .getResultList();
    }
    
    public void insertDiscussion(Discussions discussion) {
        setLogTrace("DiscussionsFacade::insertDiscussions");
        em.createNativeQuery("INSERT INTO DISCUSSIONS (id, title, author, description, date, speciality, views) VALUES (?,?,?,?,?,?,?)")
        .setParameter(1, discussion.getId())
        .setParameter(2, discussion.getTitle())
        .setParameter(3, discussion.getAuthor().getId())
        .setParameter(4, discussion.getDescription())
        .setParameter(5, discussion.getDate())
        .setParameter(6, discussion.getSpeciality().getType())
        .setParameter(7, discussion.getViews())
        .executeUpdate();
    }
    
    public void deleteDiscussion(long id) {
        setLogTrace("DiscussionsFacade::deleteDiscussions");
        em.createQuery("DELETE FROM Discussions n WHERE n.id = :id")
        .setParameter("id", id)
        .executeUpdate();   
    }
    
    public void updateDiscussion(Discussions discussion) {
        setLogTrace("DiscussionsFacade::updateDiscussions");
        em.createQuery("UPDATE Discussions n set n.title = :title, n.description = :description, n.date = :date, n.speciality = :speciality WHERE n.id = :id")
        .setParameter("id", discussion.getId())
        .setParameter("date", discussion.getDate())
        .setParameter("description", discussion.getDescription())
        .setParameter("title", discussion.getTitle())
        .setParameter("speciality", discussion.getSpeciality())
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
    
    public List<Discussions> findByPagination(int page_number) {
        setLogTrace("DiscussionsFacade::findByPagination");
        return em.createQuery("SELECT d FROM Discussions d")
                .setFirstResult((page_number-1)*5)
                .setMaxResults(5)
                .getResultList();
    }
}
