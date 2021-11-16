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

    @Override
    public List<Comment> getCommentByPostPsid(Integer psid) {
        List<Comment> commentList = commentRepo.getCommentByPostPsid(psid);
        return commentList;
    }

    @Override
    public Comment updateComment(Comment comment) {
        Comment target = commentRepo.getCommentByCid(comment.getCid());
        if(comment.getCBody()!=null)
            target.setCBody(comment.getCBody());
        return commentRepo.save(target);
    }

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

    @Override
    public Comment getCommentByCid(Integer cid){
        return commentRepo.getCommentByCid(cid);
    }

}
