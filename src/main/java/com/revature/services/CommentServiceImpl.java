package com.revature.services;

import com.revature.models.Comment;
import com.revature.repositories.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepo commentRepo;

    /**
     * addComment will receive a comment to be added from the user and return a comment. Within a try block,
     * it will catch any exception and return null. It also makes sure the comment has a profile and date, otherwise
     * it will throw an exception. Spring Data's abstracted save method is called to add the comment to the database,
     * and returns it.
     *
     * @param comment Comment to be added to the database
     * @throws NullPointerException if null pointer error occurs
     * @return Comment that was added or null if an exception is thrown.
     */
    @Override
    public Comment addComment(Comment comment) {
        try {
            if (comment.getDateCreated()==null|| comment.getWriter()==null) {
                throw new NullPointerException();
            }
           commentRepo.save(comment);
            return comment;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * getCommentsByPost utilizes Spring Data's Reflection API to find a list of all comments by PostId.
     *
     * @param psid PostId identifier
     * @return list of comments organized by PostId
     *
     */
    @Override
    public List<Comment> getCommentByPostPsid(Integer psid) {
        return commentRepo.getCommentByPostPsid(psid);
    }

    /**
     * getCommentByCid utilizes Spring Data's Reflection API to return a comment based on its id.
     *
     * @param cid
     * @return
     */
    @Override
    public Comment getCommentByCid(Integer cid){
        return commentRepo.getCommentByCid(cid);
    }

}
