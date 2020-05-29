/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Book;
import entities.Comment;
import entities.Log;
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
public class CommentFacade extends AbstractFacade<Comment> {
    @PersistenceContext(unitName = "Medlab-ejbPU")
    private EntityManager em;
    @EJB
    LogFacade log;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CommentFacade() {
        super(Comment.class);
    }

    public void updateVisits(long id) {
        setLogTrace("CommentFacade::updateVisits");
        Book book = em.find(Book.class, id);
        book.setViews(book.getViews()+1);
        em.merge(book);
    }
        
    public List<Comment> findCommentsbyId(long id) {
        setLogTrace("CommentFacade::findCommentsbyId");
        return em.createNamedQuery("Comment.findById")
                .setParameter("id", id).getResultList();
    }
        
    public List<Comment> findCommentsbyIdType(long id) {
        setLogTrace("CommentFacade::findCommentsbyIdType");
        return em.createNamedQuery("Comment.findByIdType")
                .setParameter("idType", id).getResultList();
    }
     
    public void insertComment(Comment comment) {
        setLogTrace("CommentFacade::insertComment");
        em.createNativeQuery("INSERT INTO COMMENT (id, author, message, id_type, date) VALUES (?,?,?,?,?)")
        .setParameter(1, comment.getId())
        .setParameter(2, comment.getAuthor().getId())
        .setParameter(3, comment.getMessage())
        .setParameter(4, comment.getIdType())
        .setParameter(5, comment.getDate())
        .executeUpdate();
    }
    
    public void deleteComment(long id) {
        setLogTrace("CommentFacade::deleteComments");
        em.createQuery("DELETE FROM Comment c WHERE c.id = :id")
        .setParameter("id", id)
        .executeUpdate();   
    }
       
    public void updateComment(Comment comment) {
        setLogTrace("CommentFacade::updateNews");
        em.createQuery("UPDATE Comment c set c.date = :date, c.message = :message WHERE c.id = :id")
        .setParameter("id", comment.getId())
        .setParameter("date", comment.getDate())
        .setParameter("message", comment.getMessage())
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
        log.create(log1);
    }
}
