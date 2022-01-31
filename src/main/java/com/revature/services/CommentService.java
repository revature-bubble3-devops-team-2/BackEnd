package com.revature.services;

import com.revature.models.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(Comment comment);
    
    
    List<Comment> getCommentsbyPostPsidPaginated(int psid, int pageNumber);
    List<Comment> getCommentsByPostPsid(int psid);
    
    Comment getCommentByCid(int cid);
}
