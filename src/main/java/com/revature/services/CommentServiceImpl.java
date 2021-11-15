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
        System.out.println(commentList.toString());
        return commentList;
    }
}
