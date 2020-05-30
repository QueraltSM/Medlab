/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.Comments;

import controllers.FrontCommand;
import ejbs.LogFacade;
import ejbs.CommentFacade;
import entities.Log;
import entities.Comment;
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
public class ModifyCommentCommand extends FrontCommand {
    private LogFacade log;
    private CommentFacade commentsDB;
    
    private void updateComment() {
        try {
            String message = new String(request.getParameter("updated_message").getBytes("ISO8859_1"), "UTF-8");
            long id = Long.parseLong(String.valueOf(request.getParameter("id")));
            Comment comments = commentsDB.findCommentsbyId(id).get(0);
            comments.setMessage(message);
            comments.setDate(new Date());
            commentsDB.updateComment(comments);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ModifyCommentCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void process() {
        try {
            HttpSession session = request.getSession(true);
            log = (LogFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/LogFacade!ejbs.LogFacade");
            Log log1 = new Log();
            long id = 1;
            if (!log.findAll().isEmpty()) {
               id = log.findAll().size()+1;
            }
            log1.setId(id);
            log1.setDate(new Date());
            log1.setEjbs("ModifyCommentCommand:process()");
            log.create(log1);
            commentsDB = (CommentFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/CommentFacade!ejbs.CommentFacade");
            updateComment();
            try {
                response.sendRedirect(request.getContextPath() + "/FrontController?"+session.getAttribute("old_url"));
            } catch (IOException ex) {
                Logger.getLogger(ModifyCommentCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(ModifyCommentCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
