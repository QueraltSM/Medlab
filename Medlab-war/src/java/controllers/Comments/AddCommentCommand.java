/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.Comments;

import controllers.FrontCommand;
import ejbs.LogFacade;
import ejbs.CommentFacade;
import ejbs.UsersFacade;
import entities.Log;
import entities.Comment;
import entities.Users;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author QSM
 */
public class AddCommentCommand extends FrontCommand {
    private LogFacade log;
    private CommentFacade commentsDB;
    private UsersFacade usersDB;
    private HttpSession session;
    
    private void createComment() {
        try {
            String message = new String(request.getParameter("message").getBytes("ISO8859_1"), "UTF-8");
            Comment comments = new Comment();
            long id = 0;
            if (!commentsDB.findAll().isEmpty()) id = commentsDB.findAll().get(commentsDB.count()-1).getId()+1;
            comments.setId(id);
            long userID = Long.parseLong(String.valueOf(session.getAttribute("userID")));
            Users user_logged = usersDB.findUserbyID(userID).get(0);
            comments.setAuthor(user_logged);
            comments.setIdType(Long.parseLong((String) request.getParameter("id_type")));
            comments.setMessage(message);
            comments.setDate(new Date());
            commentsDB.insertComment(comments);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AddCommentCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void process() {
        try {
            session = request.getSession(true);
            log = (LogFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/LogFacade!ejbs.LogFacade");
            Log log1 = new Log();
            long id = 1;
            if (!log.findAll().isEmpty()) {
               id = log.findAll().size()+1;
            }
            log1.setId(id);
            log1.setDate(new Date());
            log1.setEjbs("AddCommentCommand:process()");
            log.create(log1);
            commentsDB = (CommentFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/CommentFacade!ejbs.CommentFacade");
            usersDB = (UsersFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/UsersFacade!ejbs.UsersFacade");
            createComment();
            try {
                response.sendRedirect(request.getContextPath() + "/FrontController?"+session.getAttribute("old_url"));
            } catch (IOException ex) {
                Logger.getLogger(AddCommentCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(AddCommentCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
