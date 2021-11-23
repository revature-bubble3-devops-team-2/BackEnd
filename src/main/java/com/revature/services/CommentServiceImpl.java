package com.revature.services;

import com.revature.models.Comment;
import com.revature.models.Post;
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
     * @throw NullPointerException if null pointer error occurs
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
     * updateComment utilizes Spring Data's Reflection API to find a comment by commentId. Inside an if statement,
     * checks to see if the comment is null or not. If it's not null, save the new comment to the database.
     *
     * @param comment to be updated
     * @return comment that was updated
     */
    @Override
    public Comment updateComment(Comment comment) {
        Comment target = commentRepo.getCommentByCid(comment.getCid());
        if(comment.getCBody()!=null)
            target.setCBody(comment.getCBody());
        return commentRepo.save(target);
    }

    /**
     * deleteCommentByCid utilizes Spring Data's Reflection API to find a comment by id. Inside an if statement,
     * checks to see if the comment is null or not. If it's not null, delete the comment.
     *
     * @param cid commentId identifier
     * @return boolean to indicate if delete was successful or not.
     */
    @Override
    public boolean deleteCommentByCid(Integer cid) {
        Comment deleteComment = commentRepo.getCommentByCid(cid);
        if(deleteComment!=null) {
            commentRepo.delete(deleteComment);
            return true;
        }
        else
            return false;
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
