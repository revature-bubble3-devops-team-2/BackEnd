package com.revature.services;

import java.util.List;
import com.revature.models.Comment;

public interface CommentService {
    Comment addComment(Comment comment);
    
    
    List<Comment> getCommentsByPostPsid(int psid);
    List<Comment> getCommentsByPostPsidPaginated(int psid, int page);
    
    List<Comment> getCommentsByPostPsidAndPrevious(int psid, int cid);
    List<Comment> getCommentsByPostPsidAndPreviousPaginated(int psid, int cid, int page);
    
    

    Comment getCommentByCid(int cid);
}
