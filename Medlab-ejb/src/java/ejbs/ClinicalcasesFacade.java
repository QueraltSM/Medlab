/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Log;
import entities.Clinicalcases;
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
public class ClinicalcasesFacade extends AbstractFacade<Clinicalcases> {
    @PersistenceContext(unitName = "Medlab-ejbPU")
    private EntityManager em;
    @EJB
    LogFacade log;
    private CriteriaBuilder cb;
    private CriteriaQuery<Clinicalcases> query;
    private Root<Clinicalcases> root;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClinicalcasesFacade() {
        super(Clinicalcases.class);
    }
        
   public List<Clinicalcases> findCasesbyTitle(String title) {
        setLogTrace("NewsFacade::findCasesbyTitle");
        cb = em.getCriteriaBuilder();
        query = cb.createQuery(Clinicalcases.class);
        root = query.from(Clinicalcases.class);
        query.select(root).where(cb.like(root.get("title"), title)); 
        return em.createQuery(query).getResultList();
    }
       
    public List<Clinicalcases> findCasesbySpeciality(Speciality speciality) {
        setLogTrace("NewsFacade::findCasesbySpeciality");
        cb = em.getCriteriaBuilder();
        query = cb.createQuery(Clinicalcases.class);
        root = query.from(Clinicalcases.class);
        query.select(root).where(cb.equal(root.get("speciality"), speciality.getType())); 
        return em.createQuery(query).getResultList();
    }
      
    public List<Clinicalcases> findCasesbyTitle_Speciality(String title, Speciality speciality) {
        setLogTrace("NewsFacade::findCasesbyTitle_Speciality");
        cb = em.getCriteriaBuilder();
        query = cb.createQuery(Clinicalcases.class);
        root = query.from(Clinicalcases.class);
        query.select(root)
                .where(cb.like(root.get("title"), title))
                .where(cb.equal(root.get("speciality"), speciality.getType())); 
        return em.createQuery(query).getResultList();
    }
           
    public List<Clinicalcases> findCasesbyAuthor(String author) {
        setLogTrace("NewsFacade::findCasesbyAuthor");
        cb = em.getCriteriaBuilder();
        query = cb.createQuery(Clinicalcases.class);
        root = query.from(Clinicalcases.class);
        query.select(root).where(cb.like(root.get("author"), author)); 
        return em.createQuery(query).getResultList();
    }
    
    public void updateVisits(long id) {
        setLogTrace("ClinicalcasesFacade::updateVisits");
        Clinicalcases clinical_case = em.find(Clinicalcases.class, id);
        clinical_case.setViews(clinical_case.getViews()+1);
        em.merge(clinical_case);
    }
    
    public List<Clinicalcases> orderbyViews() {
        setLogTrace("ClinicalcasesFacade::orderbyViews");
        cb = em.getCriteriaBuilder();
        query = cb.createQuery(Clinicalcases.class);
        root = query.from(Clinicalcases.class);
        query.select(root)
                .orderBy(cb.desc(root.get("views")));
        return em.createQuery(query).getResultList();
    }
    
    public void insertCase(Clinicalcases clinical_case) {
        setLogTrace("ClinicalcasesFacade::insertCase");
        em.persist(clinical_case);
    }
    
    public void deleteCase(long id) {
        setLogTrace("ClinicalcasesFacade::deleteCase");
        Clinicalcases clinical_case = em.find(Clinicalcases.class, id);
        em.remove(em.merge(clinical_case));
    }
    
    public void updateCase(Clinicalcases clinical_cases) {
        setLogTrace("ClinicalcasesFacade::updateCase");
        em.merge(clinical_cases);
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