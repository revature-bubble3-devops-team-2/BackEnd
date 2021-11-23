package com.revature.services;

import com.revature.models.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(Comment comment);
    List<Comment> getCommentByPostPsid(Integer psid);
    Comment getCommentByCid(Integer cid);
}
