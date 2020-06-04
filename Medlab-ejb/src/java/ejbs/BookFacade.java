/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Book;
import entities.Log;
import entities.Speciality;
import java.util.Date;
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
public class BookFacade extends AbstractFacade<Book> {
    @PersistenceContext(unitName = "Medlab-ejbPU")
    private EntityManager em;
    @EJB
    LogFacade log;
    private CriteriaBuilder cb;
    private CriteriaQuery<Book> query;
    private Root<Book> root;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookFacade() {
        super(Book.class);
    }
    
    public List<Book> findBooksbyTitle(String title) {
        setLogTrace("BookFacade::findBooksbyTitle");
        cb = em.getCriteriaBuilder();
        query = cb.createQuery(Book.class);
        root = query.from(Book.class);
        query.select(root).where(cb.like(root.get("title"), "%"+title+"%")); 
        return em.createQuery(query).getResultList();
    }
       
    public List<Book> findBooksbySpeciality(Speciality speciality) {
        setLogTrace("booksFacade::findBooksbySpeciality");
        cb = em.getCriteriaBuilder();
        query = cb.createQuery(Book.class);
        root = query.from(Book.class);
        query.select(root).where(cb.equal(root.get("speciality"), speciality)); 
        return em.createQuery(query).getResultList();
    }
      
    public List<Book> findBooksbyTitle_Speciality(String title, Speciality speciality) {
        setLogTrace("BookFacade::findBooksbyTitle_Speciality");
        cb = em.getCriteriaBuilder();
        query = cb.createQuery(Book.class);
        root = query.from(Book.class);
        query.select(root)
                .where(cb.like(root.get("title"), "%"+title+"%"))
                .where(cb.equal(root.get("speciality"), speciality)); 
        return em.createQuery(query).getResultList();
    }
    
    public List<Book> orderbyRecent() {
        setLogTrace("BookFacade::orderbyRecent");
        cb = em.getCriteriaBuilder();
        query = cb.createQuery(Book.class);
        root = query.from(Book.class);
        query.select(root)
                .orderBy(cb.desc(root.get("date")));
        return em.createQuery(query).getResultList();
    }
            
    public List<Book> orderbyViews() {
        setLogTrace("BookFacade::orderbyViews");
        cb = em.getCriteriaBuilder();
        query = cb.createQuery(Book.class);
        root = query.from(Book.class);
        query.select(root)
                .orderBy(cb.desc(root.get("views")));
        return em.createQuery(query).getResultList();
    }
    
    public void updateVisits(long id) {
        setLogTrace("BookFacade::updateVisits");
        Book book = em.find(Book.class, id);
        book.setViews(book.getViews()+1);
        em.merge(book);
    }
        
    public void insertBook(Book book) {
        setLogTrace("BookFacade::insertBook");
        em.persist(book);
    }
    
    public void deleteBook(long id) {
        setLogTrace("BookFacade::deleteBook");
        Book book = em.find(Book.class, id);
        em.remove(em.merge(book));
    }
    
    public void updateBook(Book books) {
        setLogTrace("BookFacade::updateBook");
        em.merge(books);
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
    
    public List<Book> findByPagination(int page_number) {
        setLogTrace("BookFacade::findByPagination");
        return em.createQuery("SELECT b FROM Book b")
                .setFirstResult((page_number-1)*5)
                .setMaxResults(5)
                .getResultList();
    }
}
