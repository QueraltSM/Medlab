/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Log;
import entities.Researches;
import entities.Speciality;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author QSM
 */
@Stateless
public class ResearchesFacade extends AbstractFacade<Researches> {
    @PersistenceContext(unitName = "Medlab-ejbPU")
    private EntityManager em;
    @EJB
    LogFacade log;
    private CriteriaBuilder cb;
    private CriteriaQuery<Researches> query;
    private Root<Researches> root;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ResearchesFacade() {
        super(Researches.class);
    }
        
   public List<Researches> findResearchesbyTitle(String title) {
        setLogTrace("ResearchesFacade::findResearchesbyTitle");
        cb = em.getCriteriaBuilder();
        query = cb.createQuery(Researches.class);
        root = query.from(Researches.class);
        query.select(root).where(cb.like(root.get("title"), title)); 
        return em.createQuery(query).getResultList();
    }
       
    public List<Researches> findResearchesbySpeciality(Speciality speciality) {
        setLogTrace("ResearchesFacade::findResearchesbySpeciality");
        cb = em.getCriteriaBuilder();
        query = cb.createQuery(Researches.class);
        root = query.from(Researches.class);
        query.select(root).where(cb.equal(root.get("speciality"), speciality.getType())); 
        return em.createQuery(query).getResultList();
    }
      
    public List<Researches> findResearchesbyTitle_Speciality(String title, Speciality speciality) {
        setLogTrace("ResearchesFacade::findResearchesbyTitle_Speciality");
        cb = em.getCriteriaBuilder();
        query = cb.createQuery(Researches.class);
        root = query.from(Researches.class);
        query.select(root)
                .where(cb.like(root.get("title"), title))
                .where(cb.equal(root.get("speciality"), speciality.getType())); 
        return em.createQuery(query).getResultList();
    }
           
    public List<Researches> findResearchesbyAuthor(String author) {
        setLogTrace("ResearchesFacade::findResearchesbyAuthor");
        cb = em.getCriteriaBuilder();
        query = cb.createQuery(Researches.class);
        root = query.from(Researches.class);
        query.select(root).where(cb.like(root.get("author"), author)); 
        return em.createQuery(query).getResultList();
    }
    
    public void updateVisits(long id) {
        setLogTrace("ResearchesFacade::updateVisits");
        Researches research = em.find(Researches.class, id);
        research.setViews(research.getViews()+1);
        em.merge(research);
    }
    
    public List<Researches> orderbyViews() {
        setLogTrace("ResearchesFacade::orderbyViews");
        cb = em.getCriteriaBuilder();
        query = cb.createQuery(Researches.class);
        root = query.from(Researches.class);
        query.select(root)
                .orderBy(cb.desc(root.get("views")));
        return em.createQuery(query).getResultList();
    }
    
    public void insertResearch(Researches research) {
        setLogTrace("ResearchesFacade::insertResearch");
        em.persist(research);
    }
    
    public void deleteResearch(long id) {
        setLogTrace("ResearchesFacade::deleteResearch");
        Researches research = em.find(Researches.class, id);
        em.remove(em.merge(research));
    }
    
    public void updateResearch(Researches research) {
        setLogTrace("ResearchesFacade::updateResearch");
        em.merge(research);
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
        log.create(log1);
    }
}